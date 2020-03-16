/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.actions;

import com.opensymphony.xwork2.ActionContext;
import daos.CarDAO;
import daos.OrderDAO;
import daos.OrderDetailsDAO;
import daos.PromotionDAO;
import dtos.CarDTO;
import dtos.OrderDTO;
import dtos.OrderDetailsDTO;
import dtos.PromotionDTO;
import dtos.UserDTO;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class SearchingHistoryAction {

    private static final String SUCCESS = "success";

    private String txtSearch, fromDate, toDate, message;

    public SearchingHistoryAction() {
    }

    public String getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(String txtSearch) {
        this.txtSearch = txtSearch;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map session = ActionContext.getContext().getSession();
        UserDTO userDTO = (UserDTO) session.get("USER");
        OrderDAO orderDAO = new OrderDAO();
        Timestamp fromTime = null;
        Timestamp toTime = null;
        if(txtSearch == null) {
            txtSearch = "";
        } else {
            txtSearch = txtSearch.trim();
        }
        
        if(fromDate == null) {
            fromDate = "";
        } else {
            fromDate = fromDate.trim();
        }
        
        if(toDate == null) {
            toDate = "";
        } else {
            toDate = toDate.trim();
        }
        
        if (!fromDate.isEmpty()) {
            String[] temp = fromDate.split("/");
            String fromDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 00:00:00.000";
            fromTime = Timestamp.valueOf(fromDateStr);
        }

        if (!toDate.isEmpty()) {
            String[] temp = toDate.split("/");
            String toDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 23:59:59.999";
            toTime = Timestamp.valueOf(toDateStr);
        }
        List<OrderDTO> listPayment = orderDAO.getPaymentOrder(userDTO.getEmail(), fromTime, toTime);
        if (listPayment != null) {
            Hashtable<OrderDTO, List<OrderDetailsDTO>> htOrderDetails = new Hashtable<>();
            Hashtable<Integer, CarDTO> htCar = new Hashtable<>();
            Hashtable<Integer, PromotionDTO> htPromotion = new Hashtable<>();
            Hashtable<OrderDetailsDTO, Integer> htDate = new Hashtable<>();

            CarDAO carDAO = new CarDAO();
            OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
            PromotionDAO promotionDAO = new PromotionDAO();

            for (OrderDTO orderDTO : listPayment) {
                List<OrderDetailsDTO> listOrderDetailsDTO = orderDetailsDAO.getCart(orderDTO.getOrderID());
                //check car that matchs txtSearch
                //if order has the car that matchs txtSearch, add all cars of order
                boolean flag = false;
                if (listOrderDetailsDTO != null) {
                    for (OrderDetailsDTO orderDetailsDTO : listOrderDetailsDTO) {
                        CarDTO carDTO = carDAO.getObjectByCarID(orderDetailsDTO.getCarID());
                        if (carDTO != null) {
                            htCar.put(carDTO.getCarID(), carDTO);
                            if (carDTO.getCarName().toLowerCase().contains(txtSearch.toLowerCase())) {
                                flag = true;
                            }
                        }

                        //get total amount of each orders
                        long diff = orderDetailsDTO.getReturnDate().getTime() - orderDetailsDTO.getRentalDate().getTime();
                        int diffHours = (int) (diff / (60 * 60 * 1000) % 24);
                        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

                        if (diffHours >= 1) {
                            diffDays++;
                        }
                        htDate.put(orderDetailsDTO, diffDays);
                    }
                    if (flag) {
                        htOrderDetails.put(orderDTO, listOrderDetailsDTO);
                    }
                }
                //get Promotion
                if (orderDTO.getPromotionID() != 0) {
                    PromotionDTO promotionDTO = promotionDAO.getObjectByID(orderDTO.getPromotionID());
                    if (promotionDTO != null) {
                        htPromotion.put(promotionDTO.getPromotionID(), promotionDTO);
                    }
                }
            }
            request.setAttribute("HASHTABLE_PROMOTION", htPromotion);
            request.setAttribute("HASHTABLE_CAR", htCar);
            request.setAttribute("HASHTABLE_LIST_ORDER_DETAILS", htOrderDetails);
            request.setAttribute("HASHTABLE_DATE", htDate);
        }
        request.setAttribute("LIST_PAYMENT", listPayment);
        return SUCCESS;
    }

}
