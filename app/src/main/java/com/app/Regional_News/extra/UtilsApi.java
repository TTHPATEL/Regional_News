package com.app.Regional_News.extra;

public class UtilsApi {
    public static final String BASE_URL_API = "http://192.168.0.194/KutchToday/";
//    public static final String BASE_URL_APINgo = "http://192.168.29.117/ndss/"; TOOLBARSS  UPDATED 1234567890

    //  Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}

