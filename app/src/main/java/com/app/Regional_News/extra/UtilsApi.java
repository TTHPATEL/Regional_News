package com.app.Regional_News.extra;

public class UtilsApi {
    //    public static final String BASE_URL_API = "http://192.168.218.179/KutchToday/";  // TIRTH PHONE
//    public static final String BASE_URL_API = "http://192.168.243.178/KutchToday/"; // MUMMY PHONE
    public static final String BASE_URL_API = "http://192.168.0.195/KutchToday/"; // 5G WIFI ROUTER


    //  Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}

    