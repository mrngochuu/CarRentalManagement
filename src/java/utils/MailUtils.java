/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author ngochuu
 */
public class MailUtils implements Serializable {
    private static final String HOST_EMAIL = "ngochuu.bts@gmail.com";
    private static final String HOST_PASSWORD = "cwvhesyaudrxazox";
    private String cusEmail;
    private String cusName;
    private String code;

    public MailUtils(String CUS_EMAIL, String CUS_NAME, String code) {
        this.cusEmail = CUS_EMAIL;
        this.cusName = CUS_NAME;
        this.code = code;
    }

    public void sendMail() throws EmailException {
        HtmlEmail email = new HtmlEmail();
        //config
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(HOST_EMAIL, HOST_PASSWORD));
        email.setSSLOnConnect(true);
        email.setFrom(HOST_EMAIL,"NGOC HUU");
        email.addTo(cusEmail, cusName);
        
        email.setSubject("Confirm code to complete registration!");
        email.setHtmlMsg("<html><h2>Car Rental</h2>"
                + "<p>Your code: " + code + " </p></html>");
        email.setTextMsg("Your email client does not support HTML messages!");
        email.send();
    }
}
