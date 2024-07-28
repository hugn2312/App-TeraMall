package com.example.teramall.model;

import java.io.Serializable;

public class Store implements Serializable  {
    public String imagelogo;
    public String imageShop;
    public String address;
    public String name;
    public String location;
    public String time;
    public String website;
    public String danhMuc;
    public String phoneNumber;
    public Store(){}

    public Store(String imagelogo, String imageShop, String address, String name, String location, String time, String wesite, String phoneNumber,String danhMuc) {
        this.imagelogo = imagelogo;
        this.imageShop = imageShop;
        this.address = address;
        this.name = name;
        this.location = location;
        this.time = time;
        this.website = wesite;
        this.phoneNumber = phoneNumber;
        this.danhMuc = danhMuc;
    }

    public String getImagelogo() {
        return imagelogo;
    }

    public void setImagelogo(String imagelogo) {
        this.imagelogo = imagelogo;
    }

    public String getImageShop() {
        return imageShop;
    }

    public void setImageShop(String imageShop) {
        this.imageShop = imageShop;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String wesite) {
        this.website = wesite;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }
}
