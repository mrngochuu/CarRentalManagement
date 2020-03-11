/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quest.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import daos.UserDAO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class VerificationAction extends ActionSupport {
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private static final String INVALID = "invalid";
    
    private String code, email, fullname;
    
    public VerificationAction() {
    }

    @Override
    public void validate() {
        if(code == null || code.isEmpty()) {
            addFieldError("code", "Code is required!");
        }
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
        Map session = ActionContext.getContext().getSession();
        Object codeObj = session.get("CODE");
        String verificationCode;
        if(codeObj != null) {
            verificationCode = codeObj.toString();
        } else {
            verificationCode = "";
        }
        
        if(verificationCode.equals(code)) {
            if(new UserDAO().updateStatusWithCurrentTime(email, "active", Timestamp.valueOf(LocalDateTime.now()))) {
                session.remove("CODE");
                request.setAttribute("MESSAGE", "Register account is successful!");
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Updating status is failed!");
            }
        } else {
            request.setAttribute("INVALID", "Code is not match!");
            url = INVALID;
        }
        return url;
    }
    
}
