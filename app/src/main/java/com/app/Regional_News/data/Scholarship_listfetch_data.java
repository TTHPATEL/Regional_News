package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Scholarship_listfetch_data {

    @SerializedName("status")
    public String status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("scholarship_list_show")
    public ArrayList<Scholarship_listfetch_listdata> scholarship_list_show;

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

    public ArrayList<Scholarship_listfetch_listdata> getScholarship_list_show() {
        return scholarship_list_show;
    }

    public void setScholarship_list_show(ArrayList<Scholarship_listfetch_listdata> scholarship_list_show) {
        this.scholarship_list_show = scholarship_list_show;
    }
}
