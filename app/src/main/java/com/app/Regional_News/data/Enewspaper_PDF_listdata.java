package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

public class Enewspaper_PDF_listdata {
    @SerializedName("enewspaper_id")
    public String enewspaper_id;
    @SerializedName("enewspaper_title")
    public String enewspaper_title;
    @SerializedName("enews_pdf_name")
    public String enews_pdf_name;
    @SerializedName("enews_pdf_data")
    public String enews_pdf_data;
    @SerializedName("enews_date")
    public String enews_date;
    @SerializedName("enewspaper_status")
    public String enewspaper_status;

    public String getEnewspaper_id() {
        return enewspaper_id;
    }

    public void setEnewspaper_id(String enewspaper_id) {
        this.enewspaper_id = enewspaper_id;
    }

    public String getEnewspaper_title() {
        return enewspaper_title;
    }

    public void setEnewspaper_title(String enewspaper_title) {
        this.enewspaper_title = enewspaper_title;
    }

    public String getEnews_pdf_name() {
        return enews_pdf_name;
    }

    public void setEnews_pdf_name(String enews_pdf_name) {
        this.enews_pdf_name = enews_pdf_name;
    }

    public String getEnews_pdf_data() {
        return enews_pdf_data;
    }

    public void setEnews_pdf_data(String enews_pdf_data) {
        this.enews_pdf_data = enews_pdf_data;
    }

    public String getEnews_date() {
        return enews_date;
    }

    public void setEnews_date(String enews_date) {
        this.enews_date = enews_date;
    }

    public String getEnewspaper_status() {
        return enewspaper_status;
    }

    public void setEnewspaper_status(String enewspaper_status) {
        this.enewspaper_status = enewspaper_status;
    }
}
