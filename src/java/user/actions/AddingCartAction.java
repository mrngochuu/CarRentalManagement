/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

import com.opensymphony.xwork2.ActionContext;
import daos.OrderDetailsDAO;
import dtos.OrderDTO;
import dtos.OrderDetailsDTO;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class AddingCartAction {

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    private String carID, price, txtSearch, cbCategory, fromDate, toDate, priceMin, priceMax;

    public AddingCartAction() {
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(String txtSearch) {
        this.txtSearch = txtSearch;
    }

    public String getCbCategory() {
        return cbCategory;
    }

    public void setCbCategory(String cbCategory) {
        this.cbCategory = cbCategory;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String execute() throws Exception {
        String url = ERROR;
        HttpServletRequest request = ServletActionContext.getRequest();
        Map session = ActionContext.getContext().getSession();
        OrderDTO orderDTO = (OrderDTO) session.get("ORDER");
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
        if (orderDetailsDAO.checkExist(orderDTO.getOrderID(), Integer.parseInt(carID))) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
            orderDetailsDTO.setPrice(Integer.parseInt(price));
            orderDetailsDTO.setOrderID(orderDTO.getOrderID());
            orderDetailsDTO.setCarID(Integer.parseInt(carID));
            if (orderDetailsDAO.insertCart(orderDetailsDTO)) {
                request.setAttribute("MESSAGE", "Adding the Car to Cart is successful!");
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Adding the Car to Cart is failed!");
            }
        } else {
            request.setAttribute("MESSAGE", "The car already added to Cart!");
            url = SUCCESS;
        }
        return url;
    }

}
