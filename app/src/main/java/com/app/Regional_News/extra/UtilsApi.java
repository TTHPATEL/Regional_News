package com.app.Regional_News.extra;

public class UtilsApi {
    //    public static final String BASE_URL_API = "http://192.168.218.179/RNS/";  // TIRTH PHONE
//    public static final String BASE_URL_API = "http://192.168.243.178/RNS/"; // MUMMY PHONE
    public static final String BASE_URL_API = "http://192.168.0.192/RNS/"; // 5G WIFI ROUTER
//    public static final String BASE_URL_API = "http://192.168.180.178/RNS/"; // M33 PHONE


    //  Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}

    