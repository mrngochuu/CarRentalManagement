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
}
