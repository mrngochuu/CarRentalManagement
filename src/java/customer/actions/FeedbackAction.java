/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.actions;

import com.opensymphony.xwork2.ActionContext;
import daos.FeedbackDAO;
import daos.OrderDAO;
import daos.OrderDetailsDAO;
import dtos.FeedbackDTO;
import dtos.OrderDTO;
import dtos.UserDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class FeedbackAction {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String INVALID = "invalid";

    private String carID, rating, feedback, message;

    public FeedbackAction() {
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String execute() throws Exception {
        String url = ERROR;
        if (rating == null || feedback == null || rating.isEmpty() || feedback.isEmpty()) {
            url = INVALID;
            message = "Rate and feedback before submitting!";
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            Map session = ActionContext.getContext().getSession();
            UserDTO userDTO = (UserDTO) session.get("USER");
            //check condition to feedback
            //People who rented this car can feedback
            List<OrderDTO> listOrder = new OrderDAO().getPaymentOrderWithoutStatus(userDTO.getEmail(), null, null);
            boolean conditionFeedback = false;
            if (listOrder != null) {
                OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                for (OrderDTO orderDTO : listOrder) {
                    if(orderDetailsDAO.checkConditionToFeedback(orderDTO.getOrderID(), Integer.parseInt(carID), "returned")) {
                        conditionFeedback = true;
                        break;
                    }
                }
            }
            
            //feedback
            if (conditionFeedback) {
                FeedbackDTO feedbackDTO = new FeedbackDTO();
                feedbackDTO.setCarID(Integer.parseInt(carID));
                feedbackDTO.setEmail(userDTO.getEmail());
                feedbackDTO.setRating(Integer.parseInt(rating));
                feedbackDTO.setFeedback(feedback);
                feedbackDTO.setFeedbackDate(Timestamp.valueOf(LocalDateTime.now()));
                if (new FeedbackDAO().feedback(feedbackDTO)) {
                    message = "Thanks for your feedback!";
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Feedback is failed!");
                }
            } else {
                message = "You must rent this car and return it beforing feedback!";
                url = INVALID;
            }
        }
        return url;
    }

}
