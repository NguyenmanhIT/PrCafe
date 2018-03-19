package com.nguyenmanh.connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class CheckLogin {
    
    public static Connection conn = null;
    public static ResultSet rs = null;
    public static PreparedStatement pst = null;
    
    public static String checkConnect(){
        try{
            conn = DBConnection.getConnection();
            return "";
        }catch(Exception e){
            return "Lỗi kết nối";
        }
    }
    public static ResultSet cLog(String username, String pass){
        try {
            checkConnect();
            String sql = "SELECT * FROM employee_info WHERE employee_name = ? and employee_password = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, pass);
            return rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(CheckLogin.class.getName()).log(Level.SEVERE, null, ex);
            return rs = null;
        }
    }
}
