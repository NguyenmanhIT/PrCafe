/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.model;

/**
 * @author Nguyen Manh
 */
public class Table {
    private int table_id;
    private String table_name;
    private boolean table_status;

    public Table() {
    }

    public Table(int table_id, String table_name, boolean table_status) {
        this.table_id = table_id;
        this.table_name = table_name;
        this.table_status = table_status;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public boolean isTable_status() {
        return table_status;
    }

    public void setTable_status(boolean table_status) {
        this.table_status = table_status;
    }
}
