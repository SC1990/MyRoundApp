package com.example.stuar.myroundapp;

public class ImageUpload {

    public String name;
    public String url;
    public String retId;


    public ImageUpload() {
    }

    public ImageUpload(String name, String url, String retId) {
        this.name = name;
        this.url = url;
        this.retId = retId;
    }

    public String getRetId() {
        return retId;
    }

    public void setRetId(String retId) {
        this.retId = retId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
