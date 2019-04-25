package com.example.stuar.myroundapp;

public class Product {

    private int product_id;
    private String product_name, product_abv, product_category, product_unit_size;

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

    public String getProduct_abv() {
        return product_abv;
    }

    public void setProduct_abv(String product_abv) {
        this.product_abv = product_abv;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_unit_size() {
        return product_unit_size;
    }

    public void setProduct_unit_size(String product_unit_size) {
        this.product_unit_size = product_unit_size;
    }
}
