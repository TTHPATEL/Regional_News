package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Quiz_data {

    @SerializedName("status")
    public String status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("quiz_list")
    public ArrayList<Quiz_listdata> quiz_list;

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

    public ArrayList<Quiz_listdata> getQuiz_list() {
        return quiz_list;
    }

    public void setQuiz_list(ArrayList<Quiz_listdata> quiz_list) {
        this.quiz_list = quiz_list;
    }
}
