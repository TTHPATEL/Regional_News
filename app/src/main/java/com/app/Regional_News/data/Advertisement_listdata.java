package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

public class Advertisement_listdata {

    @SerializedName("adv_id")
    public String adv_id;
    @SerializedName("adv_name")
    public String adv_name;
    @SerializedName("adv_desc")
    public String adv_desc;
    @SerializedName("adv_img")
    public String adv_img;
    @SerializedName("adv_weblink")
    public String adv_weblink;
    @SerializedName("adv_date")
    public String adv_date;
    @SerializedName("adv_status")
    public String adv_status;

    public String getAdv_id() {
        return adv_id;
    }

    public void setAdv_id(String adv_id) {
        this.adv_id = adv_id;
    }

    public String getAdv_name() {
        return adv_name;
    }

    public void setAdv_name(String adv_name) {
        this.adv_name = adv_name;
    }

    public String getAdv_desc() {
        return adv_desc;
    }

    public void setAdv_desc(String adv_desc) {
        this.adv_desc = adv_desc;
    }

    public String getAdv_img() {
        return adv_img;
    }

    public void setAdv_img(String adv_img) {
        this.adv_img = adv_img;
    }

    public String getAdv_weblink() {
        return adv_weblink;
    }

    public void setAdv_weblink(String adv_weblink) {
        this.adv_weblink = adv_weblink;
    }

    public String getAdv_date() {
        return adv_date;
    }

    public void setAdv_date(String adv_date) {
        this.adv_date = adv_date;
    }

    public String getAdv_status() {
        return adv_status;
    }

    public void setAdv_status(String adv_status) {
        this.adv_status = adv_status;
    }
}
