package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MMdata {
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
    public ArrayList<MMlistdata> news_show;

    public ArrayList<MMlistdata> getNews_show() {
        return news_show;
    }

    public void setNews_show(ArrayList<MMlistdata> news_show) {
        this.news_show = news_show;
    }


}
