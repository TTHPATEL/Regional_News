package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Saved_news_data {


    @SerializedName("status")
    public String status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("saved_news")
    public ArrayList<Saved_news_datalist> Saved_news_datalist;

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

    public ArrayList<com.app.Regional_News.data.Saved_news_datalist> getSaved_news_datalist() {
        return Saved_news_datalist;
    }

    public void setSaved_news_datalist(ArrayList<com.app.Regional_News.data.Saved_news_datalist> saved_news_datalist) {
        Saved_news_datalist = saved_news_datalist;
    }
}
