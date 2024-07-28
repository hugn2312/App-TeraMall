package com.example.teramall.model;

import java.io.Serializable;

public class Voucher implements Serializable {
    public String nameVoucher;
    public String description;
    public String imgVoucher;

    public  Voucher(){}

    public Voucher(String nameVoucher, String description, String imgVoucher) {
        this.nameVoucher = nameVoucher;
        this.description = description;
        this.imgVoucher = imgVoucher;
    }

    public String getNameVoucher() {
        return nameVoucher;
    }

    public void setNameVoucher(String nameVoucher) {
        this.nameVoucher = nameVoucher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgVoucher() {
        return imgVoucher;
    }

    public void setImgVoucher(String imgVoucher) {
        this.imgVoucher = imgVoucher;
    }


}
