/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Nguyen Manh
 */
public class Invoice {
    private int invoice_id;
    private String invoice_date;
    private int invoice_pay;
    private Table table;
    private ArrayList<Product> arrProduct;

    public Invoice() {
    }

    public Invoice(int invoice_id, Table table, ArrayList<Product> arrProduct) {
        this.invoice_id = invoice_id;
        this.table = table;
        this.arrProduct = arrProduct;
    }
    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getInvoice_date() {
        Calendar calendar = Calendar.getInstance();
        invoice_date = calendar.get(Calendar.YEAR)+" - "+calendar.get((Calendar.MONTH))+ " - " 
                + calendar.get(Calendar.DATE);
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }
    
    public int getInvoice_pay() {
        return invoice_pay;
    }

    public void setInvoice_pay(int invoice_pay) {
        this.invoice_pay = invoice_pay;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public ArrayList<Product> getArrProduct() {
        return arrProduct;
    }

    public void setArrProduct(ArrayList<Product> arrProduct) {
        this.arrProduct = arrProduct;
    }
    
    public float total_money_cost(){
        float total_money = 0;
        for(Product p : arrProduct){
            total_money += p.total_product_cost();
        }
        return total_money;
    }
    
    public float total_money_price(){
       float total_money = 0;
       for(Product p : arrProduct){
           total_money += p.total_product_price();
       }
       return total_money;
    }
    
    private int total_product(){
        int result = 0;
        for(Product p : arrProduct){
            result += p.getProduct_quantity_sell();
        }
        return result;
    }
}
