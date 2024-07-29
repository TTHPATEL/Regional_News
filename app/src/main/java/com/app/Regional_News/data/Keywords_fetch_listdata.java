package com.app.Regional_News.data;

import com.google.gson.annotations.SerializedName;

public class Keywords_fetch_listdata {

    @SerializedName("keyword")
    public String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
