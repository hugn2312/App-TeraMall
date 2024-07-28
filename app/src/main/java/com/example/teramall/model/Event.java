package com.example.teramall.model;

import com.example.teramall.Adapter.EventAdapter;

import java.io.Serializable;

public class Event implements Serializable {
    public String nameEvent;
    public String date;
    public String description;
    public String imgEvent;
    public Event(){};
    public Event(String nameEvent, String date, String description, String imgEvent) {
        this.nameEvent = nameEvent;
        this.date = date;
        this.description = description;
        this.imgEvent = imgEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgEvent() {
        return imgEvent;
    }

    public void setImgEvent(String imgEvent) {
        this.imgEvent = imgEvent;
    }
}
