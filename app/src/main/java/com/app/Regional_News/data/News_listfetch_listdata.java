package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

public class News_listfetch_listdata {
    @SerializedName("news_id")
    public String news_id;
    @SerializedName("news_headline")
    public String news_headline;
    @SerializedName("news_des_1")
    public String news_des_1;
    @SerializedName("news_des_2")
    public String news_des_2;
    @SerializedName("news_imgurl")
    public String news_imgurl;
    @SerializedName("news_datetime")
    public String news_datetime;
    @SerializedName("news_status")
    public String news_status;


    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getNews_headline() {
        return news_headline;
    }

    public void setNews_headline(String news_headline) {
        this.news_headline = news_headline;
    }

    public String getNews_des_1() {
        return news_des_1;
    }

    public void setNews_des_1(String news_des_1) {
        this.news_des_1 = news_des_1;
    }

    public String getNews_des_2() {
        return news_des_2;
    }

    public void setNews_des_2(String news_des_2) {
        this.news_des_2 = news_des_2;
    }

    public String getNews_imgurl() {
        return news_imgurl;
    }

    public void setNews_imgurl(String news_imgurl) {
        this.news_imgurl = news_imgurl;
    }

    public String getNews_datetime() {
        return news_datetime;
    }

    public void setNews_datetime(String news_datetime) {
        this.news_datetime = news_datetime;
    }

    public String getNews_status() {
        return news_status;
    }

    public void setNews_status(String news_status) {
        this.news_status = news_status;
    }
}
