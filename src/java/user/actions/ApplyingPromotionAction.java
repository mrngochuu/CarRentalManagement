/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

import com.opensymphony.xwork2.ActionContext;
import daos.OrderDAO;
import daos.PromotionDetailsDAO;
import dtos.OrderDTO;
import dtos.UserDTO;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class ApplyingPromotionAction {

    private static final String SUCCESS = "success";
    private static final String INVALID = "invalid";
    private static final String ERROR = "error";

    private String code, message;

    public ApplyingPromotionAction() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String execute() throws Exception {
        String url = ERROR;
        if (code == null || code.isEmpty()) {
            message = "Please enter promotion code before applying!";
            url = INVALID;
        } else {
            Map session = ActionContext.getContext().getSession();
            HttpServletRequest request = ServletActionContext.getRequest();
            OrderDTO orderDTO = (OrderDTO) session.get("ORDER");
            UserDTO userDTO = (UserDTO) session.get("USER");

            if (orderDTO.getPromotionID() != 0) {
                message = "Your cart already applys promotion!";
                url = INVALID;
            } else {
                int promotionID = new PromotionDetailsDAO().getPromotionIDByCode(userDTO.getEmail(), code, "available");
                if (promotionID != 0) {
                    OrderDAO orderDAO = new OrderDAO();
                    if(orderDAO.applyPromotion(orderDTO.getOrderID(), promotionID)) {
                        orderDTO = orderDAO.getObjectByEmail(userDTO.getEmail(), false);
                        if(orderDTO != null) {
                            session.put("ORDER", orderDTO);
                            url = SUCCESS;
                        } else {
                            request.setAttribute("ERROR", "Your cart does not exist!");
                        }
                    } else {
                        request.setAttribute("ERROR", "Applying Promotion is failed!");
                    }
                } else {
                    message = "Promotion code is not match!";
                    url = INVALID;
                }
            }
        }
        return url;
    }

}
