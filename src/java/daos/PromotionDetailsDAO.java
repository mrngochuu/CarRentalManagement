/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.PromotionDetailsDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import utils.DatabaseUtils;

/**
 *
 * @author ngochuu
 */
public class PromotionDetailsDAO implements Serializable {

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
    
    public PromotionDetailsDTO getPromotionOfUser (int promotionID, String email, String status) throws ClassNotFoundException, SQLException {
        PromotionDetailsDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT receivedDate, expiriedDate, code FROM PromotionDetails WHERE promotionID = ? AND email = ? AND status = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, promotionID);
                pstm.setString(2, email);
                pstm.setString(3, status);
                rs = pstm.executeQuery();
                if(rs.next()) {
                    dto = new PromotionDetailsDTO();
                    dto.setPromotionID(promotionID);
                    dto.setEmail(email);
                    dto.setStatus(status);
                    dto.setReceivedDate(rs.getTimestamp("receivedDate"));
                    dto.setExpiriedDate(rs.getTimestamp("expiriedDate"));
                    dto.setCode(rs.getString("code"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public int getPromotionIDByCode (String email, String code, String status) throws ClassNotFoundException, SQLException {
        int promotionID = 0;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT promotionID FROM PromotionDetails WHERE email = ? AND code = ? AND status = ? AND expiriedDate > ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, email);
                pstm.setString(2, code);
                pstm.setString(3, status);
                pstm.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                rs = pstm.executeQuery();
                if(rs.next()) {
                    promotionID = rs.getInt("promotionID");
                }
            }
        } finally {
            closeConnection();
        }
        return promotionID;
    }
    
    public boolean checkAvailablePromotion(int promotionID, String email, String status) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT promotionID FROM PromotionDetails WHERE promotionID = ? AND"
                        + " email = ? AND"
                        + " status = ? AND"
                        + " expiriedDate > ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, promotionID);
                pstm.setString(2, email);
                pstm.setString(3, status);
                pstm.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                rs = pstm.executeQuery();
                flag = rs.next();
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public boolean updateStatusUsedPromotion(int promotionID, String email, String status) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "UPDATE PromotionDetails SET status = ? WHERE promotionID = ? AND email = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, status);
                pstm.setInt(2, promotionID);
                pstm.setString(3, email);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public boolean checkExistPromotion(int promotionID, String email) throws ClassNotFoundException, SQLException {
        boolean flag = true;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT promotionID FROM PromotionDetails WHERE promotionID = ? AND email = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, promotionID);
                pstm.setString(2, email);
                rs = pstm.executeQuery();
                flag = rs.next();
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
    public boolean insertPromotion(int promotionID, String email, int expiriedDate, String code) throws ClassNotFoundException, SQLException {
        boolean flag = true;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "INSERT INTO PromotionDetails VALUES(?,?,?,?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                pstm.setString(2, email);
                pstm.setInt(3, promotionID);
                pstm.setTimestamp(4, new Timestamp(Timestamp.valueOf(LocalDateTime.now()).getTime() + (((long)expiriedDate) * 24 * 60 * 60 * 1000)));
                pstm.setString(5, code);
                pstm.setString(6, "available");
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
    
}
