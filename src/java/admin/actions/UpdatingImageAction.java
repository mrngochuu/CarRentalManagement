/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.util.logging.Logger;
import dtos.FileUploadDTO;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ngochuu
 */
public class UpdatingImageAction extends ActionSupport implements ModelDriven<FileUploadDTO>, ValidationAware {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    FileUploadDTO model = new FileUploadDTO();

    private String carName, year, color, cbCategory, message, imgURL;
    private int price, quantity;
    
    public UpdatingImageAction() {
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
    
    public String execute() throws Exception {
        try {
            File f = model.getInputFile();
            if (f == null) {
                addFieldError("inputFile", "The input file is required");
                return INPUT;
            }

            HttpServletRequest request = ServletActionContext.getRequest();
            String fileName = model.getInputFileFileName();
            FileUtils.copyFile(f, new File("/Library/Tomcat/webapps/Image/" + fileName));
            request.setAttribute("IMG", fileName);
            return SUCCESS;
        } catch (Exception e) {
            return ERROR;
        }
    }

    @Override
    public FileUploadDTO getModel() {
        return model;
    }

}
