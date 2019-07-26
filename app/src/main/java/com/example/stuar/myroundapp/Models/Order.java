package com.example.stuar.myroundapp.Models;

public class Order {
    private String rname, timeDate, discount, subtotal, dFee, total;

    public Order(){

    }

    public Order(String rname, String timeDate, String discount, String subtotal, String dFee, String total) {
        this.rname = rname;
        this.timeDate = timeDate;
        this.discount = discount;
        this.subtotal = subtotal;
        this.dFee = dFee;
        this.total = total;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getdFee() {
        return dFee;
    }

    public void setdFee(String dFee) {
        this.dFee = dFee;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

