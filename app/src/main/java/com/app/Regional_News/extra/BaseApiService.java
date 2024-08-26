package com.app.Regional_News.extra;



import com.app.Regional_News.data.Commondata;
import com.app.Regional_News.data.Enewspaper_list_data;
import com.app.Regional_News.data.Event_cal_data;
import com.app.Regional_News.data.Feedbackdata;
import com.app.Regional_News.data.Keywords_fetch_data;
import com.app.Regional_News.data.News_listfetch_data;
import com.app.Regional_News.data.News_showdata;
import com.app.Regional_News.data.Notification_data;
import com.app.Regional_News.data.Profile_Updationdata;
import com.app.Regional_News.data.Quiz_data;
import com.app.Regional_News.data.RN_Udata;
import com.app.Regional_News.data.Registrationdata;
import com.app.Regional_News.data.Saved_news_data;
import com.app.Regional_News.data.Scholarship_listfetch_data;
import com.app.Regional_News.data.Search_News_listfetch_data;
import com.app.Regional_News.data.Enewspaper_PDF_data;
import com.app.Regional_News.data.UMdata;
import com.app.Regional_News.data.Udata;
import com.app.Regional_News.data.User_id_fetch_data;

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
    Call<RN_Udata> rnsLoginRequest(@Field("dss_app") String dss_app,
                                   @Field("u_data") String u_data);


    @FormUrlEncoded
    @POST("dss_api.php")
    Call<User_id_fetch_data> rnsfetchUid(@Field("dss_app") String dss_app,
                                         @Field("u_data") String u_data);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Registrationdata> rnsRegistrationRequest(@Field("dss_app") String dss_app,
                                                  @Field("u_data") String u_data);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Notification_data> rnsNotificationRequest(@Field("dss_app") String dss_app);


    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Commondata> dssaddcomplaintRequest(@Field("dss_app") String dss_app,
                                            @Field("u_data") String u_data);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<News_listfetch_data> rnNewslistRequest(@Field("dss_app") String dss_app);


    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Scholarship_listfetch_data> rnScholarshiplistRequest(@Field("dss_app") String dss_app);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Quiz_data> rnQuizlistRequest(@Field("dss_app") String dss_app);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Event_cal_data> rnEventCalRequest(@Field("dss_app") String dss_app);


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
    Call<Saved_news_data> rnSavedNewslistRequest(@Field("dss_app") String dss_app,
                                                  @Field("u_data") String u_data);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Enewspaper_list_data> rnEnewspaperlistRequest(@Field("dss_app") String dss_app);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Enewspaper_PDF_data> rnEnewspaperPDFRequest(@Field("dss_app") String dss_app,
                                                     @Field("u_data") String u_data);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Feedbackdata> rnsFeedbackRequest(@Field("dss_app") String dss_app,
                                              @Field("u_data") String u_data);

    @FormUrlEncoded
    @POST("dss_api.php")
    Call<Profile_Updationdata> rnsProfileUpdationRequest(@Field("dss_app") String dss_app,
                                                  @Field("u_data") String u_data);



    @FormUrlEncoded
    @POST("dss_api.php")
    Call<UMdata> dssUMRequest(@Field("dss_app") String dss_app,
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
