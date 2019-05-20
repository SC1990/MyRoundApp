package com.example.stuar.myroundapp.Models;

public class Cart {
    private String pId, pName, price, quantity, cartImage;

    public Cart(){

    }

    public Cart(String pId, String pName, String price, String quantity, String cartImage) {
        this.pId = pId;
        this.pName = pName;
        this.price = price;
        this.quantity = quantity;
        this.cartImage = cartImage;
    }

    public String getCartImage() {
        return cartImage;
    }

    public void setCartImage(String cartImage) {
        this.cartImage = cartImage;
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
