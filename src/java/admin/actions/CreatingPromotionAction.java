/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import daos.PromotionDAO;
import dtos.PromotionDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class CreatingPromotionAction extends ActionSupport {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private String promotionName, startedDate, endedDate, message;
    private int percents, conditionAmount, expiryDate;

    public CreatingPromotionAction() {
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(String endedDate) {
        this.endedDate = endedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public int getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static void setLOG(Logger LOG) {
        ActionSupport.LOG = LOG;
    }

    @Override
    public void validate() {
        if(promotionName == null || promotionName.isEmpty()) {
            addFieldError("promotionName", "Promotion name is required!");
        }
        
        if(percents <= 0) {
            addFieldError("percents", "Percent discount must be greater than 0!");
        }
        
        if(conditionAmount <= 0) {
            addFieldError("conditionAmount", "Condition discount must be greater than 0!");
        }
        
        if(startedDate == null || startedDate.isEmpty()) {
            addFieldError("startedDate", "Started date must be chosen!");
        }
        
        if(endedDate == null || endedDate.isEmpty()) {
            addFieldError("endedDate", "Ended date must be chosen!");
        }
        
        if(expiryDate <= 0) {
            addFieldError("expiryDate", "Expiry day of discount must be greater than 0!");
        }
    }
    
    public String execute() throws Exception {
        String url = ERROR;
        HttpServletRequest request = ServletActionContext.getRequest();
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setPromotionName(promotionName);
        promotionDTO.setPercents(percents);
        promotionDTO.setConditionAmount(conditionAmount);
        promotionDTO.setStartedDate(Timestamp.valueOf(startedDate + " 00:00:00.000"));
        promotionDTO.setEndedDate(Timestamp.valueOf(endedDate + " 00:00:00.000"));
        promotionDTO.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        promotionDTO.setStatus("active");
        promotionDTO.setExpiryDate(expiryDate);
        if(new PromotionDAO().createNewPromotion(promotionDTO)) {
            message = "Creating new Promotion is success!";
            url = SUCCESS;
        } else {
            request.setAttribute("ERROR", "Creating new Promotion is failed!");
        }
        return url;
    }

}
