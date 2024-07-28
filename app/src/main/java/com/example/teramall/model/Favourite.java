package com.example.teramall.model;

import java.io.Serializable;

public class Favourite implements Serializable {
    public String price;
    public String shortDescription;
    public String location;
    public String surl;
    public Favourite(){};

    public Favourite(String price, String shortDescription, String location, String surl) {
        this.price = price;
        this.shortDescription = shortDescription;
        this.location = location;
        this.surl = surl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }
}
