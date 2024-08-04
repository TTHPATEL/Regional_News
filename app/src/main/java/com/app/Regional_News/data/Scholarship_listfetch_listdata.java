package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

public class Scholarship_listfetch_listdata {

    @SerializedName("scholarship_id")
    public String scholarship_id;
    @SerializedName("scholarship_name")
    public String scholarship_name;
    @SerializedName("scholarship_des")
    public String scholarship_des;
    @SerializedName("scholarship_status_word")
    public String scholarship_status_word;
    @SerializedName("s_date_from")
    public String s_date_from;
    @SerializedName("s_date_to")
    public String s_date_to;
    @SerializedName("scholarship_link")
    public String scholarship_link;
    @SerializedName("scholarship_status")
    public String scholarship_status;
    @SerializedName("scholarship_post_date")
    public String scholarship_post_date;
    @SerializedName("keyword")
    public String keyword;
    @SerializedName("s_imgurl")
    public String s_imgurl;

    public String getScholarship_id() {
        return scholarship_id;
    }

    public void setScholarship_id(String scholarship_id) {
        this.scholarship_id = scholarship_id;
    }

    public String getScholarship_name() {
        return scholarship_name;
    }

    public void setScholarship_name(String scholarship_name) {
        this.scholarship_name = scholarship_name;
    }

    public String getScholarship_des() {
        return scholarship_des;
    }

    public void setScholarship_des(String scholarship_des) {
        this.scholarship_des = scholarship_des;
    }

    public String getScholarship_status_word() {
        return scholarship_status_word;
    }

    public void setScholarship_status_word(String scholarship_status_word) {
        this.scholarship_status_word = scholarship_status_word;
    }

    public String getS_date_from() {
        return s_date_from;
    }

    public void setS_date_from(String s_date_from) {
        this.s_date_from = s_date_from;
    }

    public String getS_date_to() {
        return s_date_to;
    }

    public void setS_date_to(String s_date_to) {
        this.s_date_to = s_date_to;
    }

    public String getScholarship_link() {
        return scholarship_link;
    }

    public void setScholarship_link(String scholarship_link) {
        this.scholarship_link = scholarship_link;
    }

    public String getScholarship_status() {
        return scholarship_status;
    }

    public void setScholarship_status(String scholarship_status) {
        this.scholarship_status = scholarship_status;
    }

    public String getScholarship_post_date() {
        return scholarship_post_date;
    }

    public void setScholarship_post_date(String scholarship_post_date) {
        this.scholarship_post_date = scholarship_post_date;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getS_imgurl() {
        return s_imgurl;
    }

    public void setS_imgurl(String s_imgurl) {
        this.s_imgurl = s_imgurl;
    }
}
