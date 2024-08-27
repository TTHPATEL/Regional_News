package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Advertisement_data {

    @SerializedName("status")
    public String status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("advertisment_list")
    public ArrayList<Advertisement_listdata> Advertisement_listdata;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<com.app.Regional_News.data.Advertisement_listdata> getAdvertisement_listdata() {
        return Advertisement_listdata;
    }

    public void setAdvertisement_listdata(ArrayList<com.app.Regional_News.data.Advertisement_listdata> advertisement_listdata) {
        Advertisement_listdata = advertisement_listdata;
    }
}
