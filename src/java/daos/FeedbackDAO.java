/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.FeedbackDTO;
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
public class FeedbackDAO implements Serializable {

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
    
    public List<FeedbackDTO> getAllFeedbacks (int carID) throws ClassNotFoundException, SQLException {
        List<FeedbackDTO> list = null;
        try {
            conn = DatabaseUtils.getConnection();
            if( conn != null) {
                String sql = "SELECT rating, feedback, feedbackDate, email FROM Feedbacks WHERE carID = ? ORDER BY feedbackDate DESC";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, carID);
                rs = pstm.executeQuery();
                while(rs.next()) {
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    FeedbackDTO dto = new FeedbackDTO();
                    dto.setCarID(carID);
                    dto.setRating(rs.getInt("rating"));
                    dto.setFeedback(rs.getString("feedback"));
                    dto.setFeedbackDate(rs.getTimestamp("feedbackDate"));
                    dto.setEmail(rs.getString("email"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public boolean feedback(FeedbackDTO dto) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "INSERT INTO Feedbacks (rating, feedback, feedbackDate, email, carID) VALUES (?,?,?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, dto.getRating());
                pstm.setString(2, dto.getFeedback());
                pstm.setTimestamp(3, dto.getFeedbackDate());
                pstm.setString(4, dto.getEmail());
                pstm.setInt(5, dto.getCarID());
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return flag;
    }
}
