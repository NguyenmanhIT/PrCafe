/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.dao;

import com.nguyenmanh.connection.DBConnection;
import com.nguyenmanh.model.Invoice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

/**
 * @author Nguyen Manh
 */
public class UpdataTableDao {

    public static PreparedStatement pstm = null;
    public static ResultSet rs = null;
    public static Connection conn = DBConnection.getConnection();
    public Invoice invoice = new Invoice();

    /**
     * Function load data to table
     */
    public static void loadData(String sql, JTable table){
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            table.setModel((DbUtils.resultSetToTableModel(rs)));
        } catch (SQLException ex) {
            Logger.getLogger(UpdataTableDao.class.getName()).log(Level.SEVERE, "Error Load Data", ex);
        }
    }
    
     /**
     * Function load data from table to Text Field
     */
    
    public static ResultSet showTextField(String sql){
        try {
            pstm = conn.prepareStatement(sql);
            return pstm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UpdataTableDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
