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
public class PromotionDTO implements Serializable {
    private int PromotionID, percents, conditionAmount, expiryDate;
    private String PromotionName, status;
    private Timestamp startedDate, endedDate, createdDate;

    public PromotionDTO() {
    }

    public int getPromotionID() {
        return PromotionID;
    }

    public void setPromotionID(int PromotionID) {
        this.PromotionID = PromotionID;
    }

    public int getPercents() {
        return percents;
    }

    public void setPercents(int percents) {
        this.percents = percents;
    }

    public int getConditionAmount() {
        return conditionAmount;
    }

    public void setConditionAmount(int conditionAmount) {
        this.conditionAmount = conditionAmount;
    }

    public String getPromotionName() {
        return PromotionName;
    }

    public void setPromotionName(String PromotionName) {
        this.PromotionName = PromotionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Timestamp startedDate) {
        this.startedDate = startedDate;
    }

    public Timestamp getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Timestamp endedDate) {
        this.endedDate = endedDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public int getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }
    
}
