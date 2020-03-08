/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quest.actions;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import java.util.UUID;
import utils.MailUtils;

/**
 *
 * @author ngochuu
 */
public class SendingMailAction {
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    
    private String email, fullname;
    
    public SendingMailAction() {
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
    
    public String execute() throws Exception {
        String url = ERROR;
        String code = UUID.randomUUID().toString();
        MailUtils mails = new MailUtils(email, fullname, code);
        mails.sendMail();
        Map session = ActionContext.getContext().getSession();
        session.put("CODE", code);
        url = SUCCESS;
        return url;
    }
    
}
