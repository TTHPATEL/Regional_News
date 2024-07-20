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

    @SerializedName("news_list_user")
    public ArrayList<News_showlistdata> news_list_user;

    public ArrayList<News_showlistdata> getNews_list_user() {
        return news_list_user;
    }

    public void setNews_list_user(ArrayList<News_showlistdata> news_list_user) {
        this.news_list_user = news_list_user;
    }
}
