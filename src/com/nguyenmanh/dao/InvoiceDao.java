/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.dao;

import com.nguyenmanh.connection.DBConnection;
import com.nguyenmanh.model.Invoice;
import com.nguyenmanh.model.Product;
import com.nguyenmanh.model.Table;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
public class InvoiceDao {
    private Connection conn;

    public InvoiceDao() {
        DBConnection dbc = new DBConnection();
        this.conn = dbc.getCon();
    }
    
    /**
    * function payInvoice
    */
    public void payInvoice(Invoice invoice){
        addCart(invoice);
        
         /**
         * print to word
         */
        String s = "";
        s+="Hóa đơn của "+invoice.getTable().getTable_name()+"\r\n";
        s+="Ngày "+invoice.getInvoice_date()+"\r\n";
        s+="Danh sách sản phẩm\r\n";
        s+="Mã SP | Tên Sản Phẩm  |  Đơn Giá | Số Lượng | Tổng Tiền\r\n";
        for(Product p : invoice.getArrProduct()){
            if (p.getProduct_quantity_sell()>0) {
                s+=p.getProduct_id()+"      "+p.getProduct_name()+"      "+p.getProduct_price()+"        "+p.getProduct_quantity_sell()+"      "+p.total_product_price();
                s+="\r\n";
            }
        }
        s+="\r\n";
        s+="Tổng số tiền phải thanh toán: "+invoice.total_money_price() + " VNĐ";
        
        try {
            FileOutputStream outputStream = new FileOutputStream("Invoice"+invoice.getInvoice_id()+".txt");
            PrintWriter writer = new PrintWriter(outputStream);
            writer.print(s);
            writer.close();
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
    * function add to card
    */
    public void addCart(Invoice invoice){
        String sql = "DECLARE @invoice_id INT ";
        sql += "INSERT INTO invoice_info(table_id, invoice_date, invoice_pay) VALUES("+invoice.getTable().getTable_id()+", GETDATE(),"+invoice.total_money_price()+")"
                + "SET @invoice_id = SCOPE_IDENTITY()";
        for(Product p: invoice.getArrProduct()){
            if(p.getProduct_quantity_sell()>0){
                sql += "INSERT INTO cart_info(product_id, invoice_id, cart_quantity) VALUES ("+p.getProduct_id()+", @invoice_id, "+p.getProduct_quantity_sell()+")";
            }
        }
        try {
            Statement st = conn.createStatement();
            st.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
    * function payInvoice
    */
    public Invoice getCart(Table table){
        Invoice invoice = new Invoice();
        String sql = "SELECT cart.*,invoice.*, product.*, invoice.invoice_id invoice_id \n"
                + "FROM cart_info cart, invoice_info invoice, table_info table_info, product_info product \n"
                + "WHERE cart.invoice_id = invoice.invoice_id \n"
                + "AND invoice.table_id = table_info.table_id \n"
                + "AND product.product_id = cart.product_id \n"
                + "AND table_info.table_id = ? \n"
                + "AND table_info.table_status = 'true'";
        try {
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setInt(1, table.getTable_id());
            ResultSet rs = prst.executeQuery();
            ArrayList<Product> arrProduct = new ArrayList<Product>();
            while(rs.next()){
                invoice.setInvoice_id(rs.getInt("invoice_id"));
                invoice.setInvoice_date(rs.getDate("invoice_date").toString());
                Product product = new Product();
                product.setProduct_id(rs.getInt("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setProduct_unit(rs.getString("product_unit"));
                product.setProduct_price(rs.getFloat("product_price"));
                product.setProduct_quantity_sell(rs.getInt("product_quantity_sell"));
                product.setProduct_image(rs.getString("product_image"));
                arrProduct.add(product);
            }
            invoice.setTable(table);
            invoice.setArrProduct(arrProduct);
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoice;
    }
    
    /**
    * function get all invoice in a day
    */
    
    public ArrayList<Invoice> getAllInvoiceInADay(){
        ArrayList<Invoice> arrInvoice = new ArrayList<>();
        String sql = "SELECT * FROM invoice_info invoice, table_info table_info\n" +
                "WHERE  invoice.invoice_id = table_info.table_id\n" +
                "AND invoice.employee_name IS NULL \n" +
                "AND table_info.table_status = 1";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Invoice invoice = new Invoice();
                invoice.setTable(new Table(rs.getInt("table_id"), rs.getString("table_name"), rs.getBoolean("table_status")));
                invoice.setInvoice_id(rs.getInt("invoice_id"));
                invoice.setInvoice_date(rs.getDate("invoice_date").toString());
                arrInvoice.add(invoice);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < arrInvoice.size(); i++) {
            Invoice invoice = arrInvoice.get(i);
            sql = "SELECT product.*, cart.cart_quantity\n"
                    + "FROM invoice_info invoice, cart_info cart, product_info product\n"
                    + "WHERE invoice.invoice_id = cart.invoice_id\n"
                    + "AND product.product_id = cart.product_id\n"
                    + "AND invoice.invoice_id = "+invoice.getInvoice_id();
            try {
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                ArrayList<Product> cart = new ArrayList<>();
                while(rs.next()){
                    Product p = new Product();
                    p.setProduct_id(rs.getInt("product_id"));
                    p.setProduct_name(rs.getString("product_name"));
                    p.setProduct_unit(rs.getString("product_unit"));
                    p.setProduct_price(rs.getFloat("product_price"));
                    p.setProduct_quantity_sell(rs.getInt("product_quantity_sell"));
                    cart.add(p);
                }
                arrInvoice.get(i).setArrProduct(cart);
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return arrInvoice;
    }
    
    public static void main(String[] args) {
        InvoiceDao dao = new InvoiceDao();
        dao.getAllInvoiceInADay();
    }
    
    
    
}
