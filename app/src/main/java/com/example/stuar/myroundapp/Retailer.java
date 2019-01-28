package com.example.stuar.myroundapp;

import java.util.ArrayList;

public class Retailer extends User {

    private byte[] profilePic;
    private String hours;
    private ArrayList<ProductList> ret_prodList;

    public Retailer(String name, String address, String town, String mobileNum, String userId, String hours) {
        super(name, address, town, mobileNum, userId);
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
