package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Keywords_fetch_data {
    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("keyword_fetch")
    private ArrayList<Keywords_fetch_listdata> keyword_fetch_list;  // Change this type

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

    public ArrayList<Keywords_fetch_listdata> getKeyword_fetch_list() {
        return keyword_fetch_list;
    }

    public void setKeyword_fetch_list(ArrayList<Keywords_fetch_listdata> keyword_fetch_list) {
        this.keyword_fetch_list = keyword_fetch_list;
    }
}
