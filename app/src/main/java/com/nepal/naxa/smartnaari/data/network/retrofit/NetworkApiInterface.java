package com.nepal.naxa.smartnaari.data.network.retrofit;

import com.nepal.naxa.smartnaari.data.local.model.YuwaPustaResponse;
import com.nepal.naxa.smartnaari.data.network.MyCircleDetails;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;
import com.nepal.naxa.smartnaari.data.network.ServicesResponse;
import com.nepal.naxa.smartnaari.data.network.SignUpDetailsResponse;
import com.nepal.naxa.smartnaari.data.network.UrlClass;
import com.nepal.naxa.smartnaari.data.network.UserDetail;
import com.nepal.naxa.smartnaari.data.network.YuwaPustaQueryResponse;
import com.nepal.naxa.smartnaari.data.network.service.MaChupBasdinaResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface NetworkApiInterface {
    @FormUrlEncoded
    @POST("register")
    Call<SignUpDetailsResponse> getSignupDetails(@Field("data") String data);

    @FormUrlEncoded
    @POST("login")
    Call<UserDetail> getUserData(@Field("data") String data);

    @FormUrlEncoded
    @POST("add_circle")
    Call<MyCircleDetails> getCircleData(@Field("data") String data);

    @GET("get_owl")
    Call<OwlWrapper> getOwls();

    @FormUrlEncoded
    @POST("get_yuwapusta")
    Call<YuwaPustaResponse> getYuwaPustaPosts(@Field("last_sync_date_time") String data);

    @FormUrlEncoded
    @POST("yuwa_pusta")
    Call<YuwaPustaQueryResponse> getYuwaPusaQueryDetails(@Field("data") String data);

    @FormUrlEncoded
    @POST("get_services")
    Call<ServicesResponse> getServices(@Field("last_sync_date_time") String data);


    @FormUrlEncoded
    @POST("ma_chup_basdina")
    Call<MaChupBasdinaResponse> getMaChupBasdinaDetails(@Field("data") String data);
}

