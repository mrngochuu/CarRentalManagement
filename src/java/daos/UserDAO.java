/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.UserDTO;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DatabaseUtils;

/**
 *
 * @author ngochuu
 */
public class UserDAO implements Serializable {

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
    
    public UserDTO checkLogin(String email, String password, String status) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        UserDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if(conn != null) {
                String sql = "SELECT sex, fullname, phone, address, roleID FROM Users WHERE email = ? AND password = ? AND status = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, email);
                pstm.setString(2, password);
                pstm.setString(3, status);
                rs = pstm.executeQuery();
                if(rs.next()) {
                    dto = new UserDTO();
                    dto.setEmail(email);
                    dto.setFullname(rs.getString("fullname"));
                    dto.setPhone(rs.getString("phone"));
                    dto.setSex(rs.getString("sex"));
                    dto.setAddress(rs.getString("address"));
                    dto.setRoleID(rs.getInt("roleID"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public boolean checkAccountExisted(String email, String status) throws ClassNotFoundException, SQLException {
        boolean checked = true;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT email FROM Users WHERE email = ? AND status = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, email);
                pstm.setString(2, status);
                rs = pstm.executeQuery();
                checked = rs.next();
            }
        } finally {
            closeConnection();
        }
        return checked;
    }
    
    public boolean storeAccount(UserDTO dto) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        boolean checked = true;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO Users(email, password, fullname, sex, phone, address, status, createdDate, roleID) VALUES(?,?,?,?,?,?,?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, dto.getEmail());
                pstm.setString(2, dto.getPassword());
                pstm.setString(3, dto.getFullname());
                pstm.setString(4, dto.getSex());
                pstm.setString(5, dto.getPhone());
                pstm.setString(6, dto.getAddress());
                pstm.setString(7, dto.getStatus());
                pstm.setTimestamp(8, dto.getCreatedDate());
                pstm.setInt(9, dto.getRoleID());
                checked = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checked;
    }
    
    public boolean updateAccount(UserDTO dto) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        boolean checked = true;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE Users SET password = ?, fullname = ?, sex = ?, phone = ?, address = ?, status = ?, createdDate = ?, roleID = ? WHERE email = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, dto.getPassword());
                pstm.setString(2, dto.getFullname());
                pstm.setString(3, dto.getSex());
                pstm.setString(4, dto.getPhone());
                pstm.setString(5, dto.getAddress());
                pstm.setString(6, dto.getStatus());
                pstm.setTimestamp(7, dto.getCreatedDate());
                pstm.setInt(8, dto.getRoleID());
                pstm.setString(9, dto.getEmail());
                checked = pstm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checked;
    }
}
