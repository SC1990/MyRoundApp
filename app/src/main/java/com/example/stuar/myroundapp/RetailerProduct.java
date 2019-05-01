package com.example.stuar.myroundapp;

public class RetailerProduct {

    private int retId;
    private int retailPrice;
    private String name;
    private String abv;
    private String unitSize;
    private String category;
    private ImageUpload image;

    public RetailerProduct(int retId) {
        this.retId = retId;
    }

    public RetailerProduct() {

    }

    public ImageUpload getImage() {
        return image;
    }

    public void setImage(ImageUpload image) {
        this.image = image;
    }

    public int getRetId() {
        return retId;
    }

    public void setRetId(int retId) {
        this.retId = retId;
    }
}
