/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

import com.opensymphony.xwork2.ActionContext;
import daos.CarDAO;
import daos.CategoryDAO;
import daos.OrderDetailsDAO;
import dtos.CarDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class SearchingCarAction {

    private static final String SUCCESS = "success";

    private String txtSearch, cbCategory, fromDate, toDate, priceMin, priceMax , message;

    public SearchingCarAction() {
    }

    public String getCbCategory() {
        return cbCategory;
    }

    public void setCbCategory(String cbCategory) {
        this.cbCategory = cbCategory;
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

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
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
        Hashtable<Integer, String> htCategory = (Hashtable<Integer, String>) session.get("HASHTABLE_CATEGORY");
        if (htCategory == null) {
            htCategory = new CategoryDAO().getList();
            session.put("HASHTABLE_CATEGORY", htCategory);
        }
        Hashtable<Integer, Integer> usedCar = null;
        List<CarDTO> listCar = null;
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

        CarDAO carDAO = new CarDAO();
        if ((txtSearch == null && cbCategory == null && fromDate == null && toDate == null && priceMin == null && priceMax == null)
                || (txtSearch.isEmpty() && cbCategory.equals("0") && fromDate.isEmpty() && toDate.isEmpty() && priceMin.isEmpty() && priceMax.isEmpty())) {
            usedCar = orderDetailsDAO.getTotalCarRentalingWithDate(null, null, Timestamp.valueOf(LocalDateTime.now()));
            listCar = carDAO.findCarsWithoutConditions(usedCar, "active");
            request.setAttribute("LIST_CAR", listCar);
        } else {
            if (fromDate.isEmpty() && toDate.isEmpty()) {
                usedCar = orderDetailsDAO.getTotalCarRentalingWithDate(null, null, Timestamp.valueOf(LocalDateTime.now()));
            } else if (!fromDate.isEmpty() && toDate.isEmpty()) {
                String[] temp = fromDate.split("/");
                String fromDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 00:00:00.000";
                usedCar = orderDetailsDAO.getTotalCarRentalingWithDate(Timestamp.valueOf(fromDateStr), null, Timestamp.valueOf(LocalDateTime.now()));
            } else if (fromDate.isEmpty() && !toDate.isEmpty()) {
                String[] temp = toDate.split("/");
                String toDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 23:59:59.999";
                usedCar = orderDetailsDAO.getTotalCarRentalingWithDate(null, Timestamp.valueOf(toDateStr), Timestamp.valueOf(LocalDateTime.now()));
            } else {
                String[] temp = fromDate.split("/");
                String fromDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 00:00:00.000";
                temp = toDate.split("/");
                String toDateStr = temp[2] + "-" + temp[1] + "-" + temp[0] + " 23:59:59.999";
                usedCar = orderDetailsDAO.getTotalCarRentalingWithDate(Timestamp.valueOf(fromDateStr), Timestamp.valueOf(toDateStr), Timestamp.valueOf(LocalDateTime.now()));
            }
            
            listCar = carDAO.findCarsWithConditions(usedCar, "active", txtSearch, cbCategory, priceMin, priceMax);
            request.setAttribute("LIST_CAR", listCar);
        }
        return SUCCESS;
    }

}
