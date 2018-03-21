/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.dao;

import com.nguyenmanh.connection.DBConnection;
import com.nguyenmanh.model.Table;
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
public class TableDao {
    
    private Connection connection;
    
    public TableDao(){
        DBConnection dbc = new DBConnection();
        this.connection = dbc.getCon();
    }
    
    public ArrayList<Table> getAllTable(){
        ArrayList<Table> arrTable = new ArrayList<Table>();
        String sql = "SELECT * FROM table_info";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {                
                Table table = new Table();
                table.setTable_id(rs.getInt("table_id"));
                table.setTable_name(rs.getString("table_name"));
                table.setTable_status(rs.getBoolean("table_status"));
                arrTable.add(table);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrTable;
    }
    
    public void chooseTable(Table table){
        String sql = "UPDATE table_info SET table_status = ? WHERE table_id = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setBoolean(1, table.isTable_status());
            pstm.setInt(1, table.getTable_id());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
