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
    private String email;
    private int codeID;
    private Timestamp receiverDate;

    public PromotionDetailsDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodeID() {
        return codeID;
    }

    public void setCodeID(int codeID) {
        this.codeID = codeID;
    }

    public Timestamp getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(Timestamp receiverDate) {
        this.receiverDate = receiverDate;
    }
    
}
