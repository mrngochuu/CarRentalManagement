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
import dtos.CategoryDTO;
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

    private String txtSearch, cbCategory, fromDate, toDate, priceMin, priceMax;

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

    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map session = ActionContext.getContext().getSession();
        Hashtable<Integer, String> htCategory = (Hashtable<Integer, String>) session.get("HASHTABLE_CATEGORY");
        if (htCategory == null) {
            htCategory = new CategoryDAO().getList();
            session.put("HASHTABLE_CATEGORY", htCategory);
        }

        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
        CarDAO carDAO = new CarDAO();

        Hashtable<Integer, Integer> usedCar = orderDetailsDAO.getTotalCarRentaling(Timestamp.valueOf(LocalDateTime.now()));
        List<CarDTO> listCar = carDAO.findCarsWithoutConditions(usedCar, "active");
        request.setAttribute("LIST_CAR", listCar);
        return SUCCESS;
    }

}
