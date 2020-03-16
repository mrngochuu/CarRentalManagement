/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import daos.CarDAO;
import dtos.CarDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class CreatingCarAction extends ActionSupport {
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private static final String INVALID = "invalid";
    
    private String carName, year, color, cbCategory, message, imgURL;
    private int price, quantity;
    public CreatingCarAction() {
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCbCategory() {
        return cbCategory;
    }

    public void setCbCategory(String cbCategory) {
        this.cbCategory = cbCategory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static void setLOG(Logger LOG) {
        ActionSupport.LOG = LOG;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @Override
    public void validate() {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("IMG", imgURL);
        
        if(imgURL == null || imgURL.isEmpty()) {
            addFieldError("imgURL", "The car's image is required!");
        }
        
        if(carName == null || carName.isEmpty() || carName.length() > 50) {
            addFieldError("carName", "The car's name contains 1 to 50 characters!");
        }
        
        if(year == null || year.isEmpty() || year.length() > 30) {
            addFieldError("year", "The car's model contains 1 to 30 characters!");
        }
        
        if(color == null || color.isEmpty() || color.length() > 20) {
            addFieldError("color", "The car's model contains 1 to 20 characters!");
        }
        
        if(cbCategory == null || cbCategory.equals("0")) {
            addFieldError("cbCategory", "The car's category must be chosen!");
        }
        
        if(price <= 0) {
            addFieldError("price", "The car's price must be greater than 0!");
        }
        
        if(quantity <= 0) {
            addFieldError("quantity", "The car's quantity must be greater than 0!");
        }
    }
    
    public String execute() throws Exception {
        String url = ERROR;
        CarDAO carDAO = new CarDAO();
        if(!carDAO.checkTheSameName(carName, year)) {
            CarDTO carDTO = new CarDTO();
            carDTO.setCarName(carName);
            carDTO.setPrice(price);
            carDTO.setQuantity(quantity);
            carDTO.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            carDTO.setStatus("active");
            carDTO.setImgURL(imgURL);
            carDTO.setCategoryID(Integer.parseInt(cbCategory));
            carDTO.setColor(color);
            carDTO.setModel(year);
            
            if(carDAO.insertNewCar(carDTO)) {
                message = "Creating new car is successful!";
                url = SUCCESS;
            } else {
                HttpServletRequest request = ServletActionContext.getRequest();
                request.setAttribute("ERROR", "Creating new car is failed!");
            }
        } else {
            message = "The car's name and model already exists!";
            url = INVALID;
        }
        return url;
    }
    
}
