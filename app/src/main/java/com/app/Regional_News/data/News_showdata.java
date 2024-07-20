package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class News_showdata {

    @SerializedName("status")
    public String status;

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

    @SerializedName("msg")
    public String msg;

    @SerializedName("news_show")
    public ArrayList<News_showlistdata> news_show;

    public ArrayList<News_showlistdata> getNews_show() {
        return news_show;
    }

    public void setNews_show(ArrayList<News_showlistdata> news_show) {
        this.news_show = news_show;
    }
}
