/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quest.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import daos.OrderDAO;
import daos.RoleDAO;
import daos.UserDAO;
import dtos.OrderDTO;
import dtos.RoleDTO;
import dtos.UserDTO;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class LoginAction extends ActionSupport {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String INVALID = "invalid";

    private String email, password;

    public LoginAction() {
    }

    @Override
    public void validate() {
        if(email.isEmpty()) {
            addFieldError("email", "Email is required!");
        }
        
        if(password.isEmpty()) {
            addFieldError("password", "Password is required!");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static void setLOG(Logger LOG) {
        ActionSupport.LOG = LOG;
    }

    public String execute() throws Exception {
        String url = ERROR;
        HttpServletRequest request = ServletActionContext.getRequest();

        UserDTO userDTO = new UserDAO().checkLogin(email, password, "active");
        if (userDTO != null) {
            RoleDTO roleDTO = new RoleDAO().getObjectByID(userDTO.getRoleID());
            if (roleDTO != null) {
                Map session = ActionContext.getContext().getSession();
                if (roleDTO.getRoleName().equals("customer")) {
                    OrderDTO orderDTO = new OrderDAO().getObjectByEmail(email, false);
                    if(orderDTO == null) {
                        orderDTO = new OrderDAO().createObject(email);
                    }
                    session.put("ORDER", orderDTO);
                }
                session.put("USER", userDTO);
                session.put("ROLE", roleDTO);
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Role is not found!");
            }
        } else {
            url = INVALID;
            request.setAttribute("INVALID", "Email or password invalid!");
        }
        return url;
    }

}
