/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
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
    
    public Hashtable<Integer, Integer> getTotalCarRentaling(Timestamp currentDate) throws ClassNotFoundException, SQLException {
        Hashtable<Integer, Integer> hashtable = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT carID, SUM(quantity) AS total FROM OrderDetails WHERE rentalDate <= ? AND returnDate >= ?  GROUP BY carID";
                pstm = conn.prepareStatement(sql);
                pstm.setTimestamp(1, currentDate);
                pstm.setTimestamp(2, currentDate);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if(hashtable == null) {
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
}
