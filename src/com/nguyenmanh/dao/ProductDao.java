/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.dao;

import com.nguyenmanh.connection.DBConnection;
import com.nguyenmanh.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nguyen Manh
 */
public class ProductDao {
    
    private Connection conn = DBConnection.getConnection();
    private PreparedStatement pstm = null;
    public static ResultSet rs = null;
    
    public ProductDao(){
        DBConnection dbc = new DBConnection();
        this.conn = dbc.getCon();
    }
    
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> arrProduct = new ArrayList<>();
        String sql = "SELECT * FROM product_info";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product();
                product.setProduct_id(rs.getInt("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setProduct_cost(rs.getFloat("product_cost"));
                product.setProduct_price(rs.getFloat("product_price"));
                product.setProduct_unit(rs.getString("product_unit"));
                product.setProduct_image(rs.getString("product_image"));
                arrProduct.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrProduct;
    }
    
    public void addProduct(Product product){
        String sql = "INSERT INTO product_info("
                + "product_name, "
                + "product_cost, "
                + "product_price, "
                + "product_unit, "
                + "product_image) "
                + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, product.getProduct_name());
            pstm.setFloat(2, product.getProduct_cost());
            pstm.setFloat(3, product.getProduct_price());
            pstm.setString(4, product.getProduct_unit());
            pstm.setString(5, product.getProduct_image());
            pstm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
