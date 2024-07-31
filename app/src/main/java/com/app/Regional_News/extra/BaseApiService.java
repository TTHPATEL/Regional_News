package com.app.Regional_News.extra;



import com.app.Regional_News.data.Commondata;
import com.app.Regional_News.data.Complaintdata;
import com.app.Regional_News.data.Expensedata;
import com.app.Regional_News.data.Incomedata;
import com.app.Regional_News.data.Keywords_fetch_data;
import com.app.Regional_News.data.News_listfetch_data;
import com.app.Regional_News.data.Memberdata;
import com.app.Regional_News.data.News_showdata;
import com.app.Regional_News.data.Notidata;
import com.app.Regional_News.data.Registrationdata;
import com.app.Regional_News.data.Search_News_listfetch_data;
import com.app.Regional_News.data.UMdata;
import com.app.Regional_News.data.Udata;
import com.app.Regional_News.data.Visitordata;
import com.app.Regional_News.data.WLdata;
import com.app.Regional_News.data.Wingdata;
import com.app.Regional_News.data.Workerdata;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface BaseApiService {
    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Udata> dssloginRequest(@Field("dss_app") String dss_app,
                                @Field("u_data") String u_data);


    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Registrationdata> rnsRegistrationRequest(@Field("dss_app") String dss_app,
                                                  @Field("u_data") String u_data);


    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Notidata> dssnotiRequest(@Field("dss_app") String dss_app);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Workerdata> dssworkerRequest(@Field("dss_app") String dss_app);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Complaintdata> dsscomplaintRequest(@Field("dss_app") String dss_app,
                                            @Field("u_data") String u_data);
    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Commondata> dssaddcomplaintRequest(@Field("dss_app") String dss_app,
                                            @Field("u_data") String u_data);
    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Visitordata> dssvisitorRequest(@Field("dss_app") String dss_app,
                                        @Field("u_data") String u_data);
    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Incomedata> dssincomeRequest(@Field("dss_app") String dss_app);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Expensedata> dssexpenseRequest(@Field("dss_app") String dss_app);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<News_listfetch_data> rnNewslistRequest(@Field("dss_app") String dss_app);



    @FormUrlEncoded
    @POST("dss_api.php")
    Call<News_showdata> rnNewsshowRequest(@Field("dss_app") String dss_app,
                                          @Field("u_data") String u_data);


    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Keywords_fetch_data> rnKeywordRequest(@Field("dss_app") String dss_app);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Search_News_listfetch_data> rnSearchNewslistRequest(@Field("dss_app") String dss_app,
                                                             @Field("u_data") String u_data);




    @FormUrlEncoded
    @POST("dss_api.php")
    Call<UMdata> dssUMRequest(@Field("dss_app") String dss_app,
                              @Field("u_data") String u_data);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Wingdata> dssWingRequest(@Field("dss_app") String dss_app);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Memberdata> dssMemberRequest(@Field("dss_app") String dss_app,
                                      @Field("u_data") String u_data);
    @FormUrlEncoded
    @POST("dss_api.php")
    Call<WLdata> dssWLRequest(@Field("dss_app") String dss_app,
                              @Field("u_data") String u_data);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Commondata> dssaddvisitorRequest(@Field("dss_app") String dss_app,
                              @Field("u_data") String u_data);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Commondata> dsspaymRequest(@Field("dss_app") String dss_app,
                                          @Field("u_data") String u_data);
}
