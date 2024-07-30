package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Search_News_listfetch_data {

    @SerializedName("status")
    public String status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("search_news_list_show")
    public ArrayList<Search_News_listfetch_listdata> search_news_list_show;

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

    public ArrayList<Search_News_listfetch_listdata> getSearch_news_list_show() {
        return search_news_list_show;
    }

    public void setSearch_news_list_show(ArrayList<Search_News_listfetch_listdata> search_news_list_show) {
        this.search_news_list_show = search_news_list_show;
    }
}
