package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Enewspaper_list_data
{
    @SerializedName("status")
    public String status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("fetch_all_pdfs")
    public ArrayList<Enewspaper_list_listdata> Enewspaper_list_listdata;

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

    public ArrayList<com.app.Regional_News.data.Enewspaper_list_listdata> getEnewspaper_list_listdata() {
        return Enewspaper_list_listdata;
    }

    public void setEnewspaper_list_listdata(ArrayList<com.app.Regional_News.data.Enewspaper_list_listdata> enewspaper_list_listdata) {
        Enewspaper_list_listdata = enewspaper_list_listdata;
    }
}
