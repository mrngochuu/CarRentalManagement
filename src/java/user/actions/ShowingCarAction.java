/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

import daos.CarDAO;
import daos.FeedbackDAO;
import dtos.CarDTO;
import dtos.FeedbackDTO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class ShowingCarAction {
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    
    private String carID, message;
    public ShowingCarAction() {
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String execute() throws Exception {
        String url = ERROR;
        HttpServletRequest request = ServletActionContext.getRequest();
        CarDTO carDTO = new CarDAO().getObjectByCarID(Integer.parseInt(carID));
        if(carDTO != null) {
            if(carDTO.getStatus().equals("active")) {
            List<FeedbackDTO> listFeedbackDTO = new FeedbackDAO().getAllFeedbacks(Integer.parseInt(carID));
            if(listFeedbackDTO != null) {
                int averageRating = 0;
                for (FeedbackDTO feedbackDTO : listFeedbackDTO) {
                    averageRating += feedbackDTO.getRating();
                }
                averageRating = Math.round(averageRating / listFeedbackDTO.size());
                request.setAttribute("AVERAGE_RATING", averageRating);
                request.setAttribute("LIST_FEEDBACK", listFeedbackDTO);
            }
            request.setAttribute("CAR", carDTO);
            url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "The car is inactive!");
            }
        } else {
            request.setAttribute("ERROR", "The car is not found!");
        }
        return url;
    }
    
}
