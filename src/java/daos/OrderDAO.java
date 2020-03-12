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

    public OrderDTO getObjectByEmail(String email, boolean payment) throws SQLException, ClassNotFoundException {
        OrderDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT orderID FROM orders WHERE email = ? AND isPayment = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, email);
                pstm.setBoolean(2, payment);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new OrderDTO();
                    dto.setEmail(email);
                    dto.setOrderID(rs.getInt("orderID"));
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
                String sql = "INSERT INTO orders (email, isPayment) VALUES (?,?)";
                String generatedColumns[] = {"OrderID"};
                pstm = conn.prepareStatement(sql, generatedColumns);
                pstm.setString(1, email);
                pstm.setBoolean(2, false);
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
}
