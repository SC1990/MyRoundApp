package com.example.stuar.myroundapp.Models;

public class Cart {
    private String pId, pName, price, quantity;

    public Cart(){

    }

    public Cart(String pId, String pName, String price, String quantity) {
        this.pId = pId;
        this.pName = pName;
        this.price = price;
        this.quantity = quantity;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
