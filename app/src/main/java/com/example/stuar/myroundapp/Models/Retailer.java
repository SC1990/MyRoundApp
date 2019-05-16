package com.example.stuar.myroundapp.Models;

import com.example.stuar.myroundapp.ProductList;

import java.util.ArrayList;

public class Retailer extends User {

    private String hours;
    private ArrayList<ProductList> ret_prodList;
    private String minSpend;
    private double delFee;
    private double rating;
    private ArrayList<String> beerStyles;

    public Retailer(String name, String phone, String password, String image, String address, String userId) {
        super(name, phone, password, image, address, userId);
    }

    public Retailer() {

    }

    public String getMinSpend() {
        return minSpend;
    }

    public void setMinSpend(String minSpend) {
        this.minSpend = minSpend;
    }

    public double getDelFee() {
        return delFee;
    }

    public void setDelFee(double delFee) {
        this.delFee = delFee;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getBeerStyles() {
        return beerStyles;
    }

    public void setBeerStyles(ArrayList<String> beerStyles) {
        this.beerStyles = beerStyles;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public ArrayList<ProductList> getRet_prodList() {
        return ret_prodList;
    }

    public void setRet_prodList(ArrayList<ProductList> ret_prodList) {
        this.ret_prodList = ret_prodList;
    }
}
