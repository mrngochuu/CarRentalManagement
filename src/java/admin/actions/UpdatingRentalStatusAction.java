/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.actions;

import daos.OrderDetailsDAO;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class UpdatingRentalStatusAction {
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    
    private int carID, orderID;
    private String statusAction, carName, email, status, rentalFromDate, rentalToDate, returnFromDate, returnToDate, message; 
    
    public UpdatingRentalStatusAction() {
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getStatusAction() {
        return statusAction;
    }

    public void setStatusAction(String statusAction) {
        this.statusAction = statusAction;
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
        String url = ERROR;
        HttpServletRequest request = ServletActionContext.getRequest();
        
        if(new OrderDetailsDAO().updateStatusRental(orderID, carID, statusAction)) {
            message = "Updating rental status is successful!";
            url = SUCCESS;
        } else {
            request.setAttribute("ERROR", "Updating rental status is failed!");
        }
        return url;
    }
    
}
