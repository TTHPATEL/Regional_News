package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

public class Quiz_listdata {

    @SerializedName("quiz_id")
    public String quiz_id;
    @SerializedName("quiz_name")
    public String quiz_name;
    @SerializedName("quiz_start_date")
    public String quiz_start_date;
    @SerializedName("quiz_end_date")
    public String quiz_end_date;
    @SerializedName("quiz_link")
    public String quiz_link;
    @SerializedName("quiz_status")
    public String quiz_status;

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getQuiz_start_date() {
        return quiz_start_date;
    }

    public void setQuiz_start_date(String quiz_start_date) {
        this.quiz_start_date = quiz_start_date;
    }

    public String getQuiz_end_date() {
        return quiz_end_date;
    }

    public void setQuiz_end_date(String quiz_end_date) {
        this.quiz_end_date = quiz_end_date;
    }

    public String getQuiz_link() {
        return quiz_link;
    }

    public void setQuiz_link(String quiz_link) {
        this.quiz_link = quiz_link;
    }

    public String getQuiz_status() {
        return quiz_status;
    }

    public void setQuiz_status(String quiz_status) {
        this.quiz_status = quiz_status;
    }
}
