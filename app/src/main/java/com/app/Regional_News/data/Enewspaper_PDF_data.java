package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Enewspaper_PDF_data {
    @SerializedName("status")
    public String status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("Enewspaper_fetch_PDF")
    public ArrayList<Enewspaper_PDF_listdata> Enewspaper_PDF_listdata;

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

    public ArrayList<Enewspaper_PDF_listdata> getSelected_pdf_listdata() {
        return Enewspaper_PDF_listdata;
    }

    public void setSelected_pdf_listdata(ArrayList<Enewspaper_PDF_listdata> enewspaperPDF_listdata) {
        Enewspaper_PDF_listdata = enewspaperPDF_listdata;
    }
}
