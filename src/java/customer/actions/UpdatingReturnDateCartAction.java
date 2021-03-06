/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.actions;

import com.opensymphony.xwork2.ActionContext;
import daos.OrderDetailsDAO;
import dtos.OrderDTO;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class UpdatingReturnDateCartAction {
    
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String INVALID = "invalid";

    private String carID, rentalDate, returnDate, message;
    
    public UpdatingReturnDateCartAction() {
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String execute() throws Exception {
        String url = ERROR;
        if (returnDate == null || returnDate.isEmpty() || !returnDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            message = "Please adjust the return date!";
            url = INVALID;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = sdf.parse(sdf.format(new Date()));
            Timestamp returnTime = Timestamp.valueOf(returnDate + " 23:59:59.998");
            if ((returnTime.getTime() - currentDate.getTime()) < 0) {
                message = "Return date must be greater or equal " + currentDate.getDate() + "/" + (currentDate.getMonth() + 1) + "/" + (currentDate.getYear() + 1900) + "!";
                url = INVALID;
            } else {
                OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                Map session = ActionContext.getContext().getSession();
                HttpServletRequest request = ServletActionContext.getRequest();
                OrderDTO orderDTO = (OrderDTO) session.get("ORDER");
                Timestamp tempTime = Timestamp.valueOf(returnDate + " 00:00:00.000");
                if (rentalDate == null || rentalDate.isEmpty()) {
                    if (orderDetailsDAO.updateDateInCart(orderDTO.getOrderID(), Integer.parseInt(carID), tempTime, returnTime)) {
                        url = SUCCESS;
                    } else {
                        request.setAttribute("ERROR", "Updating date is failed!");
                    }
                } else {
                    String[] temp = rentalDate.split("/");
                    rentalDate = temp[2] + "-" + temp[1] + "-" + temp[0] + " 00:00:00.000";
                    Timestamp rentalTime = Timestamp.valueOf(rentalDate);
                    if ((returnTime.getTime() - rentalTime.getTime()) < 0) {
                        if (orderDetailsDAO.updateDateInCart(orderDTO.getOrderID(), Integer.parseInt(carID), tempTime, returnTime)) {
                            url = SUCCESS;
                        } else {
                            request.setAttribute("ERROR", "Updating date is failed!");
                        }
                    } else {
                        if (orderDetailsDAO.updateDateInCart(orderDTO.getOrderID(), Integer.parseInt(carID), rentalTime, returnTime)) {
                            url = SUCCESS;
                        } else {
                            request.setAttribute("ERROR", "Updating date is failed!");
                        }
                    }
                }
            }
        }
        return url;
    }
    
}
