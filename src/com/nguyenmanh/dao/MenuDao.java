/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.dao;

import com.nguyenmanh.connection.DBConnection;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * @author Nguyen Manh
 */
public class MenuDao {
    public static Connection conn = DBConnection.getConnection();
    public static PreparedStatement pstm =null;
    public static ResultSet rs = null;
    
        public static void insertMenu(String idP, 
                String nameP, String cost, String price, String imageP, String unit){
        String sql="INSERT INTO product_info(product_id, product_name, product_cost, product_price, product_image ,product_unit) VALUES (?,?,?,?,?,?);";
        try{
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, idP);
            pstm.setString(2, nameP);
            pstm.setString(3, cost);
            pstm.setString(4, price);
            pstm.setString(5, imageP);
            pstm.setString(6, unit);
            pstm.execute();
            JOptionPane.showMessageDialog(null, "Đã thêm " +nameP+ " thành công", "Thông báo", 1);
        }catch(SQLException | HeadlessException ex){
            JOptionPane.showMessageDialog(null, "ID " +nameP+ " trùng nhau. Nhập lại", "Thông báo", 1);
        }
    }
        
    public static void updateMenu(String idP1, String idP2, String nameP, String cost, 
                                            String price, String imageP, String unit){
        String sql = "UPDATE product_info SET product_name=N'"+nameP+"', product_cost='"+cost+"', product_price='"+price+"', product_image =N'"+imageP+"', product_unit =N'"+unit+"' WHERE product_id='"+idP1+"'";
        try{
            pstm = conn.prepareStatement(sql);
            pstm.execute();
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!", "Thông báo", 1);
        }catch(SQLException | HeadlessException ex){
            ex.printStackTrace();
        }
    }
    
    public static void deleteMenu(String idP){
        String sql ="DELETE FROM product_info WHERE product_id ='"+idP+"'";
        try{
            pstm =conn.prepareStatement(sql);
            pstm.execute();
            JOptionPane.showMessageDialog(null, "Sản Phẩm " +idP+ " đã được xóa", "Thông báo", 1);
        }catch(SQLException | HeadlessException ex){
             ex.printStackTrace();
        }
    }
}
