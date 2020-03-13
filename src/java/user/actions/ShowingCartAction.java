/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

import com.opensymphony.xwork2.ActionContext;
import daos.CarDAO;
import daos.OrderDetailsDAO;
import dtos.CarDTO;
import dtos.OrderDTO;
import dtos.OrderDetailsDTO;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class ShowingCartAction {

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    public ShowingCartAction() {
    }

    public String execute() throws Exception {
        String url = ERROR;
        HttpServletRequest request = ServletActionContext.getRequest();
        Map session = ActionContext.getContext().getSession();
        OrderDTO orderDTO = (OrderDTO) session.get("ORDER");
        if (orderDTO != null) {
            List<OrderDetailsDTO> listOrderDetails = new OrderDetailsDAO().getCart(orderDTO.getOrderID());
            if (listOrderDetails != null) {
                CarDAO carDAO = new CarDAO();
                Hashtable<Integer, CarDTO> hashtableCar = new Hashtable<>();
                Hashtable<Integer, Integer> hashtableDay = new Hashtable<>();

                for (OrderDetailsDTO dto : listOrderDetails) {
                    CarDTO carDTO = carDAO.getObjectByCarID(dto.getCarID());
                    if (carDTO != null) {
                        hashtableCar.put(carDTO.getCarID(), carDTO);
                    }
                    if (dto.getRentalDate() != null && dto.getReturnDate() != null) {
                        long diff = dto.getReturnDate().getTime() - dto.getRentalDate().getTime();
                        int diffMinutes = (int) (diff / (60 * 1000) % 60);
                        int diffHours = (int) (diff / (60 * 60 * 1000) % 24);
                        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

                        if (diffMinutes >= 30) {
                            diffHours++;
                        }

                        if (diffHours >= 1) {
                            diffDays++;
                        }
                        hashtableDay.put(dto.getCarID(), diffDays);
                    }
                }
                request.setAttribute("HASHTABLE_DAY", hashtableDay);
                request.setAttribute("HASHTABLE_CAR", hashtableCar);
            }
            request.setAttribute("LIST_ORDER_DETAILS", listOrderDetails);
            url = SUCCESS;
        } else {
            request.setAttribute("ERROR", "The cart does not exist!");
        }
        return url;
    }

}
