/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.actions;

import daos.CarDAO;
import daos.OrderDAO;
import daos.OrderDetailsDAO;
import daos.PromotionDAO;
import dtos.CarDTO;
import dtos.OrderDTO;
import dtos.OrderDetailsDTO;
import dtos.PromotionDTO;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class ManagingTransactionAction {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private String carName, email, status, rentalFromDate, rentalToDate, returnFromDate, returnToDate, message;

    public ManagingTransactionAction() {
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRentalFromDate() {
        return rentalFromDate;
    }

    public void setRentalFromDate(String rentalFromDate) {
        this.rentalFromDate = rentalFromDate;
    }

    public String getRentalToDate() {
        return rentalToDate;
    }

    public void setRentalToDate(String rentalToDate) {
        this.rentalToDate = rentalToDate;
    }

    public String getReturnFromDate() {
        return returnFromDate;
    }

    public void setReturnFromDate(String returnFromDate) {
        this.returnFromDate = returnFromDate;
    }

    public String getReturnToDate() {
        return returnToDate;
    }

    public void setReturnToDate(String returnToDate) {
        this.returnToDate = returnToDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        OrderDAO orderDAO = new OrderDAO();

        if (carName == null) {
            carName = "";
        } else {
            carName = carName.trim();
        }

        if (email == null) {
            email = "";
        } else {
            email = email.trim();
        }

        if (rentalFromDate == null) {
            rentalFromDate = "";
        } else {
            rentalFromDate = rentalFromDate.trim();
        }

        if (rentalToDate == null) {
            rentalToDate = "";
        } else {
            rentalToDate = rentalToDate.trim();
        }

        if (returnFromDate == null) {
            returnFromDate = "";
        } else {
            returnFromDate = returnFromDate.trim();
        }

        if (returnToDate == null) {
            returnToDate = "";
        } else {
            returnToDate = returnToDate.trim();
        }

        if (status == null) {
            status = "";
        } else {
            status = status.trim();
        }

        Timestamp rentalFromTime = null;
        Timestamp rentalToTime = null;
        Timestamp returnFromTime = null;
        Timestamp returnToTime = null;

        if (!rentalFromDate.isEmpty()) {
            String[] temp = rentalFromDate.split("/");
            String fromDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 00:00:00.000";
            rentalFromTime = Timestamp.valueOf(fromDateStr);
        }

        if (!rentalToDate.isEmpty()) {
            String[] temp = rentalToDate.split("/");
            String toDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 23:59:59.998";
            rentalToTime = Timestamp.valueOf(toDateStr);
        }

        if (!returnFromDate.isEmpty()) {
            String[] temp = returnFromDate.split("/");
            String fromDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 00:00:00.000";
            returnFromTime = Timestamp.valueOf(fromDateStr);
        }

        if (!returnToDate.isEmpty()) {
            String[] temp = returnToDate.split("/");
            String toDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 23:59:59.998";
            returnToTime = Timestamp.valueOf(toDateStr);
        }

        List<OrderDetailsDTO> listOrderDetailsDTO = new OrderDetailsDAO().getAllCarWithDateAndStatus(rentalFromTime, rentalToTime, returnFromTime, returnToTime, status);
        if (listOrderDetailsDTO != null) {
            Hashtable<Integer, OrderDTO> htOrder = new Hashtable<>();
            Hashtable<Integer, CarDTO> htCar = new Hashtable<>();
            Hashtable<Integer, PromotionDTO> htPromotion = new Hashtable<>();
            Hashtable<OrderDetailsDTO, Integer> htDate = new Hashtable<>();

            CarDAO carDAO = new CarDAO();
            PromotionDAO promotionDAO = new PromotionDAO();

            Iterator<OrderDetailsDTO> i = listOrderDetailsDTO.iterator();
            while (i.hasNext()) {
                OrderDetailsDTO entry = i.next();
                //search by car name, email
                CarDTO carDTO = carDAO.getObjectByCarID(entry.getCarID());
                OrderDTO orderDTO = orderDAO.getObjectByID(entry.getOrderID());
                boolean condition = true;
                if (carDTO == null || orderDTO == null) {
                    condition = false;
                } else {
                    if (!carDTO.getCarName().toLowerCase().contains(carName.toLowerCase())) {
                        condition = false;
                    }

                    if (!orderDTO.getEmail().toLowerCase().contains(email.toLowerCase())) {
                        condition = false;
                    }
                }

                if (condition) {
                    htOrder.put(orderDTO.getOrderID(), orderDTO);
                    htCar.put(carDTO.getCarID(), carDTO);
                    //get promotion
                    if (orderDTO.getPromotionID() != 0) {
                        PromotionDTO promotionDTO = promotionDAO.getObjectByID(orderDTO.getPromotionID());
                        if (promotionDTO != null) {
                            htPromotion.put(promotionDTO.getPromotionID(), promotionDTO);
                        }
                    }

                    //compute rental day
                    long diff = entry.getReturnDate().getTime() - entry.getRentalDate().getTime();
                    int diffHours = (int) (diff / (60 * 60 * 1000) % 24);
                    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

                    if (diffHours >= 1) {
                        diffDays++;
                    }
                    
                    htDate.put(entry, diffDays);
                } else {
                    i.remove();
                }
                
                request.setAttribute("HT_ORDER", htOrder);
                request.setAttribute("HT_CAR", htCar);
                request.setAttribute("HT_PROMOTION", htPromotion);
                request.setAttribute("HT_DATE", htDate);
            }
        }
        request.setAttribute("RESULT", listOrderDetailsDTO);
        return SUCCESS;
    }

}
