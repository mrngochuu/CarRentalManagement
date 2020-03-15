/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.PromotionDTO;
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
public class PromotionDAO implements Serializable {

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
    
    public PromotionDTO getObjectByID(int promotionID) throws SQLException, ClassNotFoundException {
        PromotionDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT PromotionName, percents FROM promotions WHERE promotionID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, promotionID);
                rs = pstm.executeQuery();
                if(rs.next()) {
                    dto = new PromotionDTO();
                    dto.setPromotionID(promotionID);
                    dto.setPromotionName(rs.getString("PromotionName"));
                    dto.setPercents(rs.getInt("percents"));
                }
            }
        }finally {
            closeConnection();
        }
        return dto;
    }
    
    public List<PromotionDTO> getPromotionIDWithCondition(int totalAmount) throws ClassNotFoundException, SQLException {
        List<PromotionDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT promotionID, expiryDate, promotionName From promotions WHERE status = ? AND (startedDate <= ? AND EndedDate >= ?) AND conditionAmount < ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "active");
                pstm.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                pstm.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                pstm.setInt(4, totalAmount);
                rs = pstm.executeQuery();
                while(rs.next()) {
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    PromotionDTO dto = new PromotionDTO();
                    dto.setPromotionID(rs.getInt("promotionID"));
                    dto.setExpiryDate(rs.getInt("expiryDate"));
                    dto.setPromotionName(rs.getString("promotionName"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
