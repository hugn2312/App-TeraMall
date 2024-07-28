package com.example.teramall.model;

import java.io.Serializable;
import java.util.Comparator;

public class Deal implements Serializable {
    public String price;
    public String shortDescription;
    public String description;
    public String location;
    public String surl;
    public String area;
    public String area1;
    public boolean favourite;
    public Deal(){}

    public Deal(String price, String shortDescription, String description, String location, String surl, String area, String area1, boolean favourite) {
        this.price = price;
        this.shortDescription = shortDescription;
        this.description = description;
        this.location = location;
        this.surl = surl;
        this.area = area;
        this.area1 = area1;
        this.favourite = favourite;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea1() {
        return area1;
    }

    public void setArea1(String area1) {
        this.area1 = area1;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public static Comparator<Deal> priceAscending = new Comparator<Deal>()
    {
        @Override
        public int compare(Deal deal1, Deal deal2)
        {
            String price1 = deal1.getPrice();
            String price2 = deal2.getPrice();
            price1 = price1.toLowerCase();
            price2 = price2.toLowerCase();

            return price1.compareTo(price2);
        }
    };
}
