package com.example.stuar.myroundapp.Models;

public class Product {

    private String pId;
    private String pName;
    private String product_abv;
    private String product_category;
    private String product_unit_size;
    private String description, price, pImage, retId, time, date, volume;

    public Product() {
    }

    public Product(String product_id, String product_name, String description, String price, String pImage, String retId) {
        this.pId = product_id;
        this.pName = product_name;
        this.description = description;
        this.price = price;
        this.pImage = pImage;
        this.retId = retId;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRetId() {
        return retId;
    }

    public void setRetId(String retId) {
        this.retId = retId;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
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
