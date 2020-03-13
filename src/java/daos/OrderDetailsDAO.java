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
                    sql += "rentalDate <= '" + currentDate + "' AND returnDate >= '" + currentDate + "' ";
                } else if (rentalDate != null && returnDate == null) {
                    //accept 1 day
                    sql += "rentalDate <= '" + rentalDate + "' AND returnDate >= '" + rentalDate + "' ";
                } else if (rentalDate == null && returnDate != null) {
                    //accept currentDate - returnDate
                    sql += "(returnDate BETWEEN '"+ currentDate +"' AND '"+ returnDate +"') "
                            + "OR (rentalDate BETWEEN '"+ currentDate +"' AND '"+ returnDate +"') "
                            + "OR (('" + currentDate + "' BETWEEN rentalDate AND returnDate) AND ('" + returnDate + "' BETWEEN rentalDate AND returnDate))";
                } else {
                    //accept rentalDate - returnDate
                    sql += "(returnDate BETWEEN '"+ rentalDate +"' AND '"+ returnDate +"') "
                            + "OR (rentalDate BETWEEN '"+ rentalDate +"' AND '"+ returnDate +"') "
                            + "OR (('" + rentalDate+ "' BETWEEN rentalDate AND returnDate) AND ('" + returnDate + "' BETWEEN rentalDate AND returnDate))";
                }

                sql += "GROUP BY carID";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, "wating");
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

    public boolean checkExist(int orderID, int carID) throws SQLException, ClassNotFoundException  {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
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
    
    public boolean insertCart(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException  {
        boolean flag = false;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "INSERT INTO OrderDetails (price, quantity, orderID, carID) VALUES (?,?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderDetailsDTO.getPrice());
                pstm.setInt(2, 1);
                pstm.setInt(3, orderDetailsDTO.getOrderID());
                pstm.setInt(4, orderDetailsDTO.getCarID());
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
            if(conn != null) {
                String sql = "SELECT price, quantity, carID, rentalDate, returnDate FROM orderDetails WHERE orderID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, orderID);
                rs = pstm.executeQuery();
                while(rs.next()) {
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    OrderDetailsDTO dto = new OrderDetailsDTO();
                    dto.setOrderID(orderID);
                    dto.setCarID(rs.getInt("carID"));
                    dto.setPrice(rs.getInt("price"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setRentalDate(rs.getTimestamp("rentalDate"));
                    dto.setReturnDate(rs.getTimestamp("returnDate"));
                    list.add(dto);
                }
            }
        } finally {
            
        }
        return list;
    }
}
