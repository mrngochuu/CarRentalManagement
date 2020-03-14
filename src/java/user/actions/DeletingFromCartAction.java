/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

import com.opensymphony.xwork2.ActionContext;
import daos.OrderDetailsDAO;
import dtos.OrderDTO;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class DeletingFromCartAction {

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    private String carID;

    public DeletingFromCartAction() {
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String execute() throws Exception {
        String url = ERROR;
        HttpServletRequest request = ServletActionContext.getRequest();
        Map session = ActionContext.getContext().getSession();
        OrderDTO orderDTO = (OrderDTO) session.get("ORDER");
        if(new OrderDetailsDAO().deleteQuantityInCart(orderDTO.getOrderID(), Integer.parseInt(carID))) {
            url = SUCCESS;
        } else {
            request.setAttribute("ERROR", "Deleting Car From Cart is failed!");
        }
        return url;
    }

}
