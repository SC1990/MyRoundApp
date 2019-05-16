package com.example.stuar.myroundapp.Models;

import com.example.stuar.myroundapp.ProductList;

import java.util.ArrayList;

public class Retailer extends User {

    private byte[] profilePic;
    private String hours;
    private ArrayList<ProductList> ret_prodList;
    private String minSpend;
    private double delFee;
    private double rating;
    private ArrayList<String> beerStyles;
    private String password;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
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


    public Retailer(String name, String address, String town, String mobileNum, String userId, String hours, String email, String password) {
        super(name, address, town, mobileNum, userId, password);
        this.hours = hours;
    }

    public Retailer() {

    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
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
