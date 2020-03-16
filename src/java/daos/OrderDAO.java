/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.OrderDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseUtils;

/**
 *
 * @author ngochuu
 */
public class OrderDAO implements Serializable {

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
    
    public OrderDTO getObjectByID(int orderID) throws ClassNotFoundException, SQLException {
        OrderDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT receiverName, receiverPhone, address, paymentDate, email, promotionID FROM orders WHERE orderID = ? AND isPayment = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderID);
                pstm.setBoolean(2, true);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new OrderDTO();
                    dto.setEmail(rs.getString("email"));
                    dto.setOrderID(orderID);
                    dto.setPromotionID(rs.getInt("promotionID"));
                    dto.setReceiverName(rs.getString("receiverName"));
                    dto.setReceiverPhone(rs.getString("receiverPhone"));
                    dto.setAddress(rs.getString("address"));
                    dto.setPaymentDate(rs.getTimestamp("paymentDate"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public OrderDTO getObjectByEmail(String email, boolean payment) throws SQLException, ClassNotFoundException {
        OrderDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT orderID, promotionID FROM orders WHERE email = ? AND isPayment = ? AND status = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, email);
                pstm.setBoolean(2, payment);
                pstm.setString(3, "active");
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new OrderDTO();
                    dto.setEmail(email);
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setPromotionID(rs.getInt("promotionID"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public OrderDTO createObject(String email) throws ClassNotFoundException, SQLException {
        OrderDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO orders (email, isPayment, promotionID, status) VALUES (?,?,?,?)";
                String generatedColumns[] = {"OrderID"};
                pstm = conn.prepareStatement(sql, generatedColumns);
                pstm.setString(1, email);
                pstm.setBoolean(2, false);
                pstm.setNull(3, java.sql.Types.INTEGER);
                pstm.setString(4, "active");
                if(pstm.executeUpdate() > 0) {
                    rs = pstm.getGeneratedKeys();
                    if(rs.next()) {
                        dto = new OrderDTO();
                        dto.setEmail(email);
                        dto.setOrderID(rs.getInt(1));
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public boolean updateNULLPromotion(int orderID) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "UPDATE Orders SET promotionID = ? WHERE orderID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setNull(1, java.sql.Types.INTEGER);
                pstm.setInt(2, orderID);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public boolean applyPromotion(int orderID, int promotionID) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "UPDATE Orders SET promotionID = ? WHERE orderID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, promotionID);
                pstm.setInt(2, orderID);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public boolean payCart(int orderID, String receiverName, String receiverPhone, String address) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "UPDATE Orders SET receiverName = ?, receiverPhone = ?, address = ?, paymentDate = ?, isPayment = ? WHERE orderID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, receiverName);
                pstm.setString(2, receiverPhone);
                pstm.setString(3, address);
                pstm.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                pstm.setBoolean(5, true);
                pstm.setInt(6, orderID);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public List<OrderDTO> getPaymentOrder(String email, Timestamp fromDate, Timestamp toDate) throws ClassNotFoundException, SQLException {
        List<OrderDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT orderID, receiverName, receiverPhone, address, paymentDate, promotionID FROM Orders WHERE email = ? AND isPayment = ? AND status = ?";
                if(fromDate != null) {
                    sql += " AND paymentDate >= '" + fromDate.toString() + "'";
                }
                
                if(toDate != null) {
                    sql += " AND paymentDate <= '" + toDate.toString() + "'";
                }
                
                sql += " ORDER BY paymentDate DESC";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, email);
                pstm.setBoolean(2, true);
                pstm.setString(3, "active");
                rs = pstm.executeQuery();
                while(rs.next()) {
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    OrderDTO dto = new OrderDTO();
                    dto.setEmail(email);
                    dto.setPayment(true);
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setReceiverName(rs.getString("receiverName"));
                    dto.setReceiverPhone(rs.getString("receiverPhone"));
                    dto.setAddress(rs.getString("address"));
                    dto.setPaymentDate(rs.getTimestamp("paymentDate"));
                    dto.setPromotionID(rs.getInt("promotionID"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public List<OrderDTO> getAllPaymentOrder(String email, Timestamp fromDate, Timestamp toDate) throws ClassNotFoundException, SQLException {
        List<OrderDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT orderID, receiverName, receiverPhone, address, paymentDate, promotionID, status FROM Orders WHERE email LIKE ? AND isPayment = ?";
                if(fromDate != null) {
                    sql += " AND paymentDate >= '" + fromDate.toString() + "'";
                }
                
                if(toDate != null) {
                    sql += " AND paymentDate <= '" + toDate.toString() + "'";
                }
                
                sql += " ORDER BY paymentDate DESC";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "%" + email + "%");
                pstm.setBoolean(2, true);
                rs = pstm.executeQuery();
                while(rs.next()) {
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    OrderDTO dto = new OrderDTO();
                    dto.setEmail(email);
                    dto.setPayment(true);
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setReceiverName(rs.getString("receiverName"));
                    dto.setReceiverPhone(rs.getString("receiverPhone"));
                    dto.setAddress(rs.getString("address"));
                    dto.setPaymentDate(rs.getTimestamp("paymentDate"));
                    dto.setPromotionID(rs.getInt("promotionID"));
                    dto.setStatus(rs.getString("status"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public List<OrderDTO> getPaymentOrderWithoutStatus(String email, Timestamp fromDate, Timestamp toDate) throws ClassNotFoundException, SQLException {
        List<OrderDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT orderID, receiverName, receiverPhone, address, paymentDate, promotionID, status FROM Orders WHERE email = ? AND isPayment = ?";
                if(fromDate != null) {
                    sql += " AND paymentDate >= '" + fromDate.toString() + "'";
                }
                
                if(toDate != null) {
                    sql += " AND paymentDate <= '" + toDate.toString() + "'";
                }
                
                sql += " ORDER BY paymentDate DESC";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, email);
                pstm.setBoolean(2, true);
                rs = pstm.executeQuery();
                while(rs.next()) {
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    OrderDTO dto = new OrderDTO();
                    dto.setEmail(email);
                    dto.setPayment(true);
                    dto.setOrderID(rs.getInt("orderID"));
                    dto.setReceiverName(rs.getString("receiverName"));
                    dto.setReceiverPhone(rs.getString("receiverPhone"));
                    dto.setAddress(rs.getString("address"));
                    dto.setPaymentDate(rs.getTimestamp("paymentDate"));
                    dto.setPromotionID(rs.getInt("promotionID"));
                    dto.setStatus(rs.getString("status"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public boolean updateStatusOrder(int orderID, String status) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "UPDATE Orders SET status = ? WHERE orderID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, status);
                pstm.setInt(2, orderID);
                flag = pstm.executeUpdate() > 0;
            }
        }finally {
            closeConnection();
        }
        return flag;
    }
}
