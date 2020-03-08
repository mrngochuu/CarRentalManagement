/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.RoleDTO;
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
public class RoleDAO implements Serializable {

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

    public RoleDTO getObjectByID(int roleID) throws SQLException, ClassNotFoundException {
        RoleDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT roleName FROM roles WHERE roleID = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, roleID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new RoleDTO();
                    dto.setRoleID(roleID);
                    dto.setRoleName(rs.getString("roleName"));
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public RoleDTO getObjectByName(String roleName) throws ClassNotFoundException, SQLException {
        RoleDTO dto = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT RoleID FROM Roles WHERE Rolename = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, roleName);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    dto = new RoleDTO();
                    dto.setRoleID(rs.getInt("RoleID"));
                    dto.setRoleName(roleName);
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
}
