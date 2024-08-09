package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

public class Feedbackdata
{
    @SerializedName("msg")
    public String msg;
    @SerializedName("status")
    public String status;
    @SerializedName("sm_u_id")
    public String sm_u_id;
    @SerializedName("feedback_title")
    public String feedback_title;

    @SerializedName("feedback_desc")
    public String feedback_desc;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSm_u_id() {
        return sm_u_id;
    }

    public void setSm_u_id(String sm_u_id) {
        this.sm_u_id = sm_u_id;
    }

    public String getFeedback_title() {
        return feedback_title;
    }

    public void setFeedback_title(String feedback_title) {
        this.feedback_title = feedback_title;
    }

    public String getFeedback_desc() {
        return feedback_desc;
    }

    public void setFeedback_desc(String feedback_desc) {
        this.feedback_desc = feedback_desc;
    }
}
