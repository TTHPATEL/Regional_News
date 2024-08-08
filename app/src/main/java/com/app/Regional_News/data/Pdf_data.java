package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pdf_data
{
    @SerializedName("status")
    public String status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("fetch_all_pdfs")
    public ArrayList<Pdf_listdata> Pdf_listdata;

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

    public ArrayList<com.app.Regional_News.data.Pdf_listdata> getPdf_listdata() {
        return Pdf_listdata;
    }

    public void setPdf_listdata(ArrayList<com.app.Regional_News.data.Pdf_listdata> pdf_listdata) {
        Pdf_listdata = pdf_listdata;
    }
}
