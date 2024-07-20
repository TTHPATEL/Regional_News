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

    @SerializedName("news_list_show")
    public ArrayList<MMlistdata> news_list_show;

    public ArrayList<MMlistdata> getNews_list_show() {
        return news_list_show;
    }

    public void setNews_list_show(ArrayList<MMlistdata> news_list_show) {
        this.news_list_show = news_list_show;
    }


}
