/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CarDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import utils.DatabaseUtils;

/**
 *
 * @author ngochuu
 */
public class CarDAO implements Serializable {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public List<CarDTO> findCarsWithoutConditions(Hashtable<Integer, Integer> rentaledCar, String status) throws ClassNotFoundException, SQLException {
        List<CarDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, price, quantity, model, imgURL, color, categoryID FROM cars WHERE status = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "active");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    boolean flag = true;
                    int carID = rs.getInt("carID");
                    CarDTO carDTO = new CarDTO();
                    if (rentaledCar != null && rentaledCar.containsKey(carID)) {
                        int avaiableCar = rs.getInt("quantity") - rentaledCar.get(carID);
                        if (avaiableCar <= 0) {
                            flag = false;
                        } else {
                            carDTO.setQuantity(avaiableCar);
                        }
                    } else {
                        carDTO.setQuantity(rs.getInt("quantity"));
                    }
                    if (flag) {
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        carDTO.setCarID(carID);
                        carDTO.setCarName(rs.getString("carName"));
                        carDTO.setPrice(rs.getInt("price"));
                        carDTO.setModel(rs.getString("model"));
                        carDTO.setImgURL(rs.getString("imgURL"));
                        carDTO.setColor(rs.getString("color"));
                        carDTO.setCategoryID(rs.getInt("categoryID"));
                        list.add(carDTO);
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public List<CarDTO> findCarsWithConditions(Hashtable<Integer, Integer> rentaledCar, String status, String txtSearch, String categoryID, String minPrice, String maxPrice) throws ClassNotFoundException, SQLException {
        List<CarDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, price, quantity, model, imgURL, color, categoryID FROM cars WHERE status = ?";
                if(!txtSearch.isEmpty()) {
                    sql += " AND carName LIKE '%"+ txtSearch +"%'";
                }
                
                if(!categoryID.equals("0") && !categoryID.isEmpty()) {
                    sql += " AND categoryID = " + Integer.parseInt(categoryID);
                }
                
                if(!minPrice.isEmpty()) {
                    sql += " AND price >= " + Integer.parseInt(minPrice);
                }
                
                if(!maxPrice.isEmpty() || maxPrice.equals("Max Price")) {
                    sql += " AND price <= " + Integer.parseInt(maxPrice);
                }
                
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "active");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    boolean flag = true;
                    int carID = rs.getInt("carID");
                    CarDTO carDTO = new CarDTO();
                    if (rentaledCar != null && rentaledCar.containsKey(carID)) {
                        int avaiableCar = rs.getInt("quantity") - rentaledCar.get(carID);
                        if (avaiableCar <= 0) {
                            flag = false;
                        } else {
                            carDTO.setQuantity(avaiableCar);
                        }
                    } else {
                        carDTO.setQuantity(rs.getInt("quantity"));
                    }
                    if (flag) {
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        carDTO.setCarID(carID);
                        carDTO.setCarName(rs.getString("carName"));
                        carDTO.setPrice(rs.getInt("price"));
                        carDTO.setModel(rs.getString("model"));
                        carDTO.setImgURL(rs.getString("imgURL"));
                        carDTO.setColor(rs.getString("color"));
                        carDTO.setCategoryID(rs.getInt("categoryID"));
                        list.add(carDTO);
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public CarDTO getObjectByCarID(int carID) throws ClassNotFoundException, SQLException {
        CarDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT carName, categoryID, model, imgURL FROM Cars WHERE carID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, carID);
                rs = pstm.executeQuery();
                if(rs.next()) {
                    dto = new CarDTO();
                    dto.setCarID(carID);
                    dto.setCarName(rs.getString("carName"));
                    dto.setCategoryID(rs.getInt("categoryID"));
                    dto.setModel(rs.getString("model"));
                    dto.setImgURL(rs.getString("imgURL"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
}
