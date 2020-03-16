/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.OrderDetailsDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import utils.DatabaseUtils;

/**
 *
 * @author ngochuu
 */
public class OrderDetailsDAO implements Serializable {

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

    public Hashtable<Integer, Integer> getTotalCarRentalingWithDate(Timestamp rentalDate, Timestamp returnDate, Timestamp currentDate) throws ClassNotFoundException, SQLException {
        Hashtable<Integer, Integer> hashtable = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT carID, SUM(quantity) AS total FROM OrderDetails WHERE (status = ? OR status = ?) AND ";
                if (rentalDate == null && returnDate == null) {
                    //accept 1 day
                    sql += "(rentalDate <= '" + currentDate + "' AND returnDate >= '" + currentDate + "') ";
                } else if (rentalDate != null && returnDate == null) {
                    //accept 1 day
                    sql += "(rentalDate <= '" + rentalDate + "' AND returnDate >= '" + rentalDate + "') ";
                } else if (rentalDate == null && returnDate != null) {
                    //accept currentDate - returnDate
                    sql += "((returnDate BETWEEN '" + currentDate + "' AND '" + returnDate + "') "
                            + "OR (rentalDate BETWEEN '" + currentDate + "' AND '" + returnDate + "') "
                            + "OR (('" + currentDate + "' BETWEEN rentalDate AND returnDate) AND ('" + returnDate + "' BETWEEN rentalDate AND returnDate))) ";
                } else {
                    //accept rentalDate - returnDate
                    sql += "((returnDate BETWEEN '" + rentalDate + "' AND '" + returnDate + "') "
                            + "OR (rentalDate BETWEEN '" + rentalDate + "' AND '" + returnDate + "') "
                            + "OR (('" + rentalDate + "' BETWEEN rentalDate AND returnDate) AND ('" + returnDate + "' BETWEEN rentalDate AND returnDate)))";
                }

                sql += "GROUP BY carID";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "waiting");
                pstm.setString(2, "rentaling");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if (hashtable == null) {
                        hashtable = new Hashtable<>();
                    }
                    hashtable.put(rs.getInt("carID"), rs.getInt("total"));
                }
            }
        } finally {
            closeConnection();
        }
        return hashtable;
    }
    
    public int getTotalTheUsedCar(int carID, Timestamp rentalDate, Timestamp returnDate) throws ClassNotFoundException, SQLException {
        int total = 0;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT SUM(quantity) AS total FROM OrderDetails WHERE (status = ? OR status = ?) AND carID = ? AND "
                        //accept rentalDate - returnDate
                        + "((returnDate BETWEEN '" + rentalDate + "' AND '" + returnDate + "') "
                        + "OR (rentalDate BETWEEN '" + rentalDate + "' AND '" + returnDate + "') "
                        + "OR (('" + rentalDate + "' BETWEEN rentalDate AND returnDate) AND ('" + returnDate + "' BETWEEN rentalDate AND returnDate)))";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "waiting");
                pstm.setString(2, "rentaling");
                pstm.setInt(3, carID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public boolean checkExist(int orderID, int carID) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT carID FROM OrderDetails WHERE orderID = ? AND carID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderID);
                pstm.setInt(2, carID);
                rs = pstm.executeQuery();
                flag = rs.next();
            }
        } finally {
            closeConnection();
        }
        return flag;
    }

    public boolean insertCart(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO OrderDetails (price, quantity, orderID, carID, rentalDate, returnDate) VALUES (?,?,?,?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderDetailsDTO.getPrice());
                pstm.setInt(2, 1);
                pstm.setInt(3, orderDetailsDTO.getOrderID());
                pstm.setInt(4, orderDetailsDTO.getCarID());
                String nowDate = LocalDateTime.now().toString();
                int index = nowDate.indexOf("T");
                nowDate = nowDate.substring(0, index);
                pstm.setTimestamp(5, Timestamp.valueOf(nowDate + " 00:00:00.000"));
                pstm.setTimestamp(6, Timestamp.valueOf(nowDate + " 23:59:59.998"));
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }

    public List<OrderDetailsDTO> getCart(int orderID) throws SQLException, ClassNotFoundException {
        List<OrderDetailsDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT price, quantity, carID, rentalDate, returnDate, status FROM orderDetails WHERE orderID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderID);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    OrderDetailsDTO dto = new OrderDetailsDTO();
                    dto.setOrderID(orderID);
                    dto.setCarID(rs.getInt("carID"));
                    dto.setPrice(rs.getInt("price"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setRentalDate(rs.getTimestamp("rentalDate"));
                    dto.setReturnDate(rs.getTimestamp("returnDate"));
                    dto.setStatus(rs.getString("status"));
                    list.add(dto);
                }
            }
        } finally {

        }
        return list;
    }

    public boolean updateQuantityInCart(int orderID, int carID, int quantity) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "Update orderDetails SET quantity = ? WHERE orderID = ? AND carID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, quantity);
                pstm.setInt(2, orderID);
                pstm.setInt(3, carID);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public boolean deleteQuantityInCart(int OrderID, int carID) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "DELETE FROM orderDetails WHERE orderID = ? AND carID = ?";
                pstm = conn.prepareCall(sql);
                pstm.setInt(1, OrderID);
                pstm.setInt(2, carID);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public boolean updateDateInCart(int orderID, int carID, Timestamp rentalDate, Timestamp returnDate) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "Update orderDetails SET rentalDate = ?, returnDate = ? WHERE orderID = ? AND carID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setTimestamp(1, rentalDate);
                pstm.setTimestamp(2, returnDate);
                pstm.setInt(3, orderID);
                pstm.setInt(4, carID);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public boolean updateWatingStatus(int orderID) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "Update orderDetails SET status = ? WHERE orderID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "waiting");
                pstm.setInt(2, orderID);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public boolean checkConditionToFeedback(int orderID, int carID, String status) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT orderID FROM OrderDetails WHERE orderID = ? AND carID = ? AND status = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderID);
                pstm.setInt(2, carID);
                pstm.setString(3, status);
                rs = pstm.executeQuery();
                flag = rs.next();
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public List<OrderDetailsDTO> getAllCarWithDateAndStatus(Timestamp rentalFromDate, Timestamp rentalToDate, Timestamp returnFromDate, Timestamp returnToDate, String status) throws ClassNotFoundException, SQLException {
        List<OrderDetailsDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT price, quantity, orderID, carID, rentalDate, returnDate, status FROM OrderDetails WHERE status IS NOT NULL";
                
                if(rentalFromDate != null) {
                    sql += " AND rentalDate >= '" + rentalFromDate +"'";
                }
                
                if(rentalToDate != null) {
                    sql += " AND rentalDate <= '" + rentalToDate +"'";
                }
                
                if(returnFromDate != null) {
                    sql += " AND returnDate >= '" + returnFromDate +"'";
                }
                
                if(returnToDate != null) {
                    sql += " AND returnDate <= '" + returnToDate +"'";
                }
                
                if(!status.isEmpty()) {
                    sql += " AND status = '" + status + "'";
                }
                
                pstm = conn.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    OrderDetailsDTO dto = new OrderDetailsDTO();
                    dto.setPrice(rs.getInt("price"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setCarID(rs.getInt("carID"));
                    dto.setRentalDate(rs.getTimestamp("rentalDate"));
                    dto.setReturnDate(rs.getTimestamp("returnDate"));
                    dto.setStatus(rs.getString("status"));
                    list.add(dto);  
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public boolean updateStatusRental(int orderID, int carID, String status) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "Update orderDetails SET status = ? WHERE orderID = ? AND carID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, status);
                pstm.setInt(2, orderID);
                pstm.setInt(3, carID);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
}
