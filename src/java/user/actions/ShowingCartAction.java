/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

import com.opensymphony.xwork2.ActionContext;
import daos.CarDAO;
import daos.OrderDAO;
import daos.OrderDetailsDAO;
import daos.PromotionDAO;
import daos.PromotionDetailsDAO;
import dtos.CarDTO;
import dtos.OrderDTO;
import dtos.OrderDetailsDTO;
import dtos.PromotionDTO;
import dtos.PromotionDetailsDTO;
import dtos.UserDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class ShowingCartAction {

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    private String message;

    public ShowingCartAction() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String execute() throws Exception {
        String url = ERROR;
        HttpServletRequest request = ServletActionContext.getRequest();
        Map session = ActionContext.getContext().getSession();
        OrderDTO orderDTO = (OrderDTO) session.get("ORDER");
        UserDTO userDTO = (UserDTO) session.get("USER");
        if (orderDTO != null) {
            OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
            List<OrderDetailsDTO> listOrderDetails = orderDetailsDAO.getCart(orderDTO.getOrderID());
            if (listOrderDetails != null) {
                CarDAO carDAO = new CarDAO();
                Hashtable<Integer, CarDTO> hashtableCar = new Hashtable<>();
                Hashtable<Integer, Integer> hashtableDay = new Hashtable<>();
                Hashtable<Integer, String> hashtableStatus = new Hashtable<>();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = sdf.parse(sdf.format(new Date()));

                for (OrderDetailsDTO dto : listOrderDetails) {
                    CarDTO carDTO = carDAO.getObjectByCarID(dto.getCarID());
                    if (carDTO != null) {
                        hashtableCar.put(dto.getCarID(), carDTO);
                        //get status message
                        if (carDTO.getStatus().equals("active")) {
                            if (dto.getRentalDate() == null || dto.getReturnDate() == null) {
                                hashtableStatus.put(dto.getCarID(), "Date mismatch");
                            } else {
                                if ((dto.getRentalDate().getTime() - currentDate.getTime()) < 0 || (dto.getReturnDate().getTime() - dto.getRentalDate().getTime()) < 0) {
                                    hashtableStatus.put(dto.getCarID(), "Date mismatch");
                                } else {
                                    int avaiableCar = carDTO.getQuantity() - orderDetailsDAO.getTotalTheUsedCar(dto.getCarID(), dto.getRentalDate(), dto.getReturnDate());
                                    if (dto.getQuantity() <= avaiableCar) {
                                        hashtableStatus.put(dto.getCarID(), "Available");
                                    } else {
                                        hashtableStatus.put(dto.getCarID(), "Only " + avaiableCar + " Car remain");
                                    }
                                }

                                //get total rental date
                                long diff = dto.getReturnDate().getTime() - dto.getRentalDate().getTime();
                                int diffHours = (int) (diff / (60 * 60 * 1000) % 24);
                                int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

                                if (diffHours >= 1) {
                                    diffDays++;
                                }
                                hashtableDay.put(dto.getCarID(), diffDays);
                            }
                        } else {
                            hashtableStatus.put(dto.getCarID(), "Unavailable");
                        }
                    }
                }
                request.setAttribute("HASHTABLE_STATUS", hashtableStatus);
                request.setAttribute("HASHTABLE_DAY", hashtableDay);
                request.setAttribute("HASHTABLE_CAR", hashtableCar);

                //get promotion
                if (orderDTO.getPromotionID() != 0) {
                    OrderDAO orderDAO = new OrderDAO();
                    PromotionDTO promotionDTO = new PromotionDAO().getObjectByID(orderDTO.getPromotionID());
                    PromotionDetailsDTO promotionDetailsDTO = new PromotionDetailsDAO().getPromotionOfUser(orderDTO.getPromotionID(), userDTO.getEmail(), "available");
                    if (promotionDTO == null || promotionDetailsDTO == null || promotionDetailsDTO.getExpiriedDate().getTime() <= currentDate.getTime()) {
                        if (orderDAO.updateNULLPromotion(orderDTO.getOrderID())) {
                            orderDTO = orderDAO.getObjectByEmail(userDTO.getEmail(), false);
                            session.put("ORDER", orderDTO);
                        }
                    } else {
                        request.setAttribute("PROMOTION", promotionDTO);
                    }
                }
            }
            request.setAttribute("LIST_ORDER_DETAILS", listOrderDetails);
            url = SUCCESS;
        } else {
            request.setAttribute("ERROR", "The cart does not exist!");
        }
        return url;
    }

}
