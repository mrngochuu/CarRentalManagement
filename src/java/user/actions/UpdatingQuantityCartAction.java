/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

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
public class UpdatingQuantityCartAction {

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private static final String INVALID = "invalid";

    private String carID, quantity, rentalDate, returnDate, message;

    public UpdatingQuantityCartAction() {
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

        if (Integer.parseInt(quantity) <= 0) {
            message = "The quantity must be greater than 0!";
            url = INVALID;
        } else {
            if (rentalDate == null || returnDate == null || rentalDate.isEmpty() || returnDate.isEmpty()) {
                message = "Please adjust the rental and return date the car before changing quantity!";
                url = INVALID;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = sdf.parse(sdf.format(new Date()));
                String[] temp = rentalDate.split("/");
                rentalDate = temp[2] + "-" + temp[1] + "-" + temp[0] + " 00:00:00.000";
                temp = returnDate.split("/");
                returnDate = temp[2] + "-" + temp[1] + "-" + temp[0] + " 23:59:59.998";

                Timestamp rentalTime = Timestamp.valueOf(rentalDate);
                Timestamp returnTime = Timestamp.valueOf(returnDate);
                boolean flagTime = true;
                if ((rentalTime.getTime() - currentDate.getTime()) < 0) {
                    flagTime = false;
                }

                if ((returnTime.getTime() - rentalTime.getTime()) < 0) {
                    flagTime = false;
                }

                if (!flagTime) {
                    message = "Please adjust the rental and return date the car before changing quantity!";
                    url = INVALID;
                } else {
                    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                    Map session = ActionContext.getContext().getSession();
                    OrderDTO orderDTO = (OrderDTO) session.get("ORDER");
                    if (orderDetailsDAO.updateQuantityInCart(orderDTO.getOrderID(), Integer.parseInt(carID), Integer.parseInt(quantity))) {
                        url = SUCCESS;
                    } else {
                        HttpServletRequest request = ServletActionContext.getRequest();
                        request.setAttribute("ERROR", "Updating quantity is failed!");
                    }
                }
            }
        }
        return url;
    }
}
