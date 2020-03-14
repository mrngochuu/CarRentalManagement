/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author ngochuu
 */
public class PromotionDetailsDTO implements Serializable {
    private String email, status, code;
    private int promotionID;
    private Timestamp receivedDate, expiriedDate;
    public PromotionDetailsDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public Timestamp getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Timestamp receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Timestamp getExpiriedDate() {
        return expiriedDate;
    }

    public void setExpiriedDate(Timestamp expiriedDate) {
        this.expiriedDate = expiriedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
