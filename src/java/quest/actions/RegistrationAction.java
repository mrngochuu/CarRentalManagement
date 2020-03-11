/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quest.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import daos.RoleDAO;
import daos.UserDAO;
import dtos.RoleDTO;
import dtos.UserDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import utils.PasswordUtils;

/**
 *
 * @author ngochuu
 */
public class RegistrationAction extends ActionSupport {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String INVALID = "invalid";

    private String email, password, confirm, fullname, sex, phone, address;

    public RegistrationAction() {
    }

    @Override
    public void validate() {
        if (email.length() <= 0 || email.length() > 50) {
            addFieldError("email", "Email contains from 1 to 50 characters!");
        }

        if (!email.matches("[a-zA-Z0-9]+([!#$%&'*+-/=?^_`{|}~.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+(.[a-zA-Z0-9]+){1,2}")) {
            addFieldError("email", "Email invalid!");
        }

        if (password.length() <= 0 || password.length() > 50) {
            addFieldError("password", "Password contains from 1 to 50 characters!");
        } else {
            if (!confirm.equals(password)) {
                addFieldError("confirm", "Re-type password is not match!");
            }
        }

        if (fullname.length() <= 0 || fullname.length() > 50) {
            addFieldError("fullname", "Fullname contains from 1 to 50 characters!");
        }

        if (sex == null || sex.isEmpty()) {
            addFieldError("sex", "Sex must be chosen!");
        }

        if (!phone.matches("0\\d{2}-\\d{7}")) {
            addFieldError("phone", "Form phone is 0XX-XXXXXXX (X stands for digit)!");
        }

        if (address.isEmpty()) {
            addFieldError("address", "Address is required!");
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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        UserDAO userDAO = new UserDAO();
        if (!userDAO.checkAccountExisted(email, "active")) {
            RoleDTO roleDTO = new RoleDAO().getObjectByName("customer");
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            userDTO.setPassword(PasswordUtils.hashPassword(password));
            userDTO.setFullname(fullname);
            userDTO.setSex(sex);
            userDTO.setPhone(phone);
            userDTO.setAddress(address);
            userDTO.setStatus("new");
            userDTO.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));

            if (roleDTO != null) {
                userDTO.setRoleID(roleDTO.getRoleID());
            } else {
                request.setAttribute("ERROR", "Role is not found!");
            }

            if (!userDAO.checkAccountExisted(email, "new")) {
                if (userDAO.storeAccount(userDTO)) {
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Register account is failed!");
                }
            } else {
                if (userDAO.updateAccount(userDTO)) {
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Update account is failed!");
                }
            }
        } else {
            request.setAttribute("INVALID", "Email already exists!");
            url = INVALID;
        }
        return url;
    }

}
