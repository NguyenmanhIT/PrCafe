/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.model;

/**
 * @author Nguyen Manh
 */
public class Product {
    private int product_id;
    private String product_name;
    private float product_cost;
    private float product_price;
    private String product_image;
    private String product_unit;
    private int product_quantity_sell;

    public Product() {
    }

    public Product(int product_id, String product_name, float product_cost, float product_price, String product_image, String product_unit, int product_quantity_sell) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_cost = product_cost;
        this.product_price = product_price;
        this.product_image = product_image;
        this.product_unit = product_unit;
        this.product_quantity_sell = product_quantity_sell;
    }

    public Product(String product_name, float product_cost, float product_price, String product_image, String product_unit, int product_quantity_sell) {
        this.product_name = product_name;
        this.product_cost = product_cost;
        this.product_price = product_price;
        this.product_image = product_image;
        this.product_unit = product_unit;
        this.product_quantity_sell = product_quantity_sell;
    }
    
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getProduct_cost() {
        return product_cost;
    }

    public void setProduct_cost(float product_cost) {
        this.product_cost = product_cost;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public int getProduct_quantity_sell() {
        return product_quantity_sell;
    }

    public void setProduct_quantity_sell(int product_quantity_sell) {
        this.product_quantity_sell = product_quantity_sell;
    }

    public float total_product_price(){
        return product_quantity_sell*product_price;
    }
    
    public float total_product_cost(){
        return product_quantity_sell*product_cost;
    }
}
