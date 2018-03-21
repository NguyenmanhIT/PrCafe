/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Nguyen Manh
 */
public class DBConnection {
    private Connection con;
    private String dbUsername = "sa";
    private String dbPassword = "123456";
    private String databaseName = "PrCafe";
    private String hostName = "localhost";
    private String port = "1433";
 
    public DBConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://"+hostName+":"+port+";databaseName="+databaseName;
            try {
                this.con = DriverManager.getConnection(url, dbUsername, dbPassword);
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connection getConnection(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PrCafe", "sa", "123456");
            return conn;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Connect Database Error!", "Warning", 1);
            return null;
        }
    } 
    public static void main(String[] args) {
        System.out.println(getConnection());
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
}
