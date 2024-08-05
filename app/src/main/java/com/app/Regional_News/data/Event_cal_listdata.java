package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

public class Event_cal_listdata {

    @SerializedName("event_id")
    public String event_id;
    @SerializedName("event_name")
    public String event_name;
    @SerializedName("event_desc")
    public String event_desc;
    @SerializedName("event_date")
    public String event_date;
    @SerializedName("event_status")
    public String event_status;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }
}
