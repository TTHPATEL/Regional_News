package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Notification_data {

    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;
    @SerializedName("notification_list")
    public ArrayList<Notification_listdata> Notification_listdata;

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

    public ArrayList<com.app.Regional_News.data.Notification_listdata> getNotification_listdata() {
        return Notification_listdata;
    }

    public void setNotification_listdata(ArrayList<com.app.Regional_News.data.Notification_listdata> notification_listdata) {
        Notification_listdata = notification_listdata;
    }
}
