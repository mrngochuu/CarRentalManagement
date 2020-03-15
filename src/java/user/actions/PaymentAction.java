/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import daos.CarDAO;
import daos.OrderDAO;
import daos.OrderDetailsDAO;
import daos.PromotionDAO;
import daos.PromotionDetailsDAO;
import dtos.CarDTO;
import dtos.OrderDTO;
import dtos.OrderDetailsDTO;
import dtos.PromotionDTO;
import dtos.UserDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class PaymentAction extends ActionSupport {

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private static final String INVALID = "invalid";

    private String receiverName, receiverPhone, address, message;

    public PaymentAction() {
    }

    @Override
    public void validate() {
        if (receiverName == null || receiverName.isEmpty() || receiverName.length() > 50) {
            addFieldError("receiverName", "Receiver's name is required!");
        }

        if (receiverPhone == null || !receiverPhone.matches("0\\d{2}-\\d{7}")) {
            addFieldError("receiverPhone", "Receiver's phone is 0XX-XXXXXXX (X stands for digit!)");
        }

        if (address == null || address.isEmpty()) {
            addFieldError("address", "Pick up address is required!");
        }
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static void setLOG(Logger LOG) {
        ActionSupport.LOG = LOG;
    }

    public String execute() throws Exception {
        String url = ERROR;
        Map session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        OrderDTO orderDTO = (OrderDTO) session.get("ORDER");
        UserDTO userDTO = (UserDTO) session.get("USER");
        PromotionDetailsDAO promotionDetailsDAO = new PromotionDetailsDAO();
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
        OrderDAO orderDAO = new OrderDAO();

        //check avaiable Promotion
        boolean isAvailablePromotion = true;
        if (orderDTO.getPromotionID() != 0) {
            if (!promotionDetailsDAO.checkAvailablePromotion(orderDTO.getPromotionID(), userDTO.getEmail(), "available")) {
                //remove promotion of order
                orderDAO.updateNULLPromotion(orderDTO.getOrderID());
                orderDTO = orderDAO.getObjectByEmail(userDTO.getEmail(), false);
                session.put("ORDER", orderDTO);
                isAvailablePromotion = false;
            }
        }

        if (isAvailablePromotion) {

            boolean isPayment = true;
            CarDAO carDAO = new CarDAO();

            List<OrderDetailsDTO> listOrderDetailsDTO = orderDetailsDAO.getCart(orderDTO.getOrderID());
            //check condition to payment
            if (listOrderDetailsDTO != null) {
                for (OrderDetailsDTO dto : listOrderDetailsDTO) {
                    CarDTO carDTO = carDAO.getObjectByCarID(dto.getCarID());
                    if (carDTO != null) {
                        if (carDTO.getStatus().equals("active")) {
                            if (dto.getRentalDate() != null && dto.getReturnDate() != null) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date currentDate = sdf.parse(sdf.format(new Date()));
                                if (dto.getRentalDate().getTime() >= currentDate.getTime() && dto.getReturnDate().getTime() >= dto.getRentalDate().getTime()) {
                                    int avaiableCar = carDTO.getQuantity() - orderDetailsDAO.getTotalTheUsedCar(carDTO.getCarID(), dto.getRentalDate(), dto.getReturnDate());
                                    if (dto.getQuantity() > avaiableCar) {
                                        isPayment = false;
                                        break;
                                    }
                                } else {
                                    isPayment = false;
                                    break;
                                }
                            } else {
                                isPayment = false;
                                break;
                            }
                        } else {
                            isPayment = false;
                            break;
                        }
                    } else {
                        isPayment = false;
                        break;
                    }
                }

                if (isPayment) {
                    //change status cart
                    if (orderDAO.payCart(orderDTO.getOrderID(), receiverName, receiverPhone, address)) {
                        if (orderDetailsDAO.updateWatingStatus(orderDTO.getOrderID())) {
                            //change status promotion that applied in cart
                            if (orderDTO.getPromotionID() != 0) {
                                if (!promotionDetailsDAO.updateStatusUsedPromotion(orderDTO.getPromotionID(), userDTO.getEmail(), "unavailable")) {
                                    request.setAttribute("ERROR", "Update promotion status is failed!");
                                } else {
                                    message = "Rental car is successful!";
                                }
                            } else {
                                //if The lastest cart does not apply promotion, check condition to gift discount
                                int total = 0;
                                for (OrderDetailsDTO dto : listOrderDetailsDTO) {
                                    //get total rental date
                                    long diff = dto.getReturnDate().getTime() - dto.getRentalDate().getTime();
                                    int diffHours = (int) (diff / (60 * 60 * 1000) % 24);
                                    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

                                    if (diffHours >= 1) {
                                        diffDays++;
                                    }
                                    total += dto.getPrice() * dto.getQuantity() * diffDays;
                                }

                                List<PromotionDTO> listPromotion = new PromotionDAO().getPromotionIDWithCondition(total);
                                if (listPromotion != null) {
                                    PromotionDTO promotionDTO = null;
                                    for (PromotionDTO dto : listPromotion) {
                                        if (!promotionDetailsDAO.checkExistPromotion(dto.getPromotionID(), userDTO.getEmail())) {
                                            promotionDTO = dto;
                                            break;
                                        }
                                    }

                                    if (promotionDTO != null) {
                                        String code = UUID.randomUUID().toString();
                                        if (promotionDetailsDAO.insertPromotion(promotionDTO.getPromotionID(), userDTO.getEmail(), promotionDTO.getExpiryDate(), code)) {
                                            message = "Rental car is successfull and the " + promotionDTO.getPromotionName() + " discount was sent for you!";
                                        }
                                    }
                                }
                            }
                            //set new cart
                            orderDTO = orderDAO.createObject(userDTO.getEmail());
                            if (orderDTO != null) {
                                session.put("ORDER", orderDTO);
                                url = SUCCESS;
                            } else {
                                request.setAttribute("ERROR", "Creating new cart is failed!");
                            }
                        } else {
                            request.setAttribute("ERROR", "Update status car is failed!");
                        }
                    } else {
                        request.setAttribute("ERROR", "Payment is failed!");
                    }
                } else {
                    message = "Please remove or update the inavailable car to payment!";
                    url = INVALID;
                }
            } else {
                message = "Your cart has no car to payment!";
                url = INVALID;
            }
        } else {
            message = "Your promotion code has expried!";
            url = INVALID;
        }
        return url;
    }

}
