/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.dispatcher.SessionMap;

/**
 *
 * @author ngochuu
 */
public class LogoutAction {
    private static final String SUCCESS = "success";
    public LogoutAction() {
    }
    
    public String execute() throws Exception {
        SessionMap session = (SessionMap) ActionContext.getContext().getSession();
        session.invalidate();
        return SUCCESS;
    }
    
}
