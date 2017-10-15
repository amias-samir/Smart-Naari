package com.nepal.naxa.smartnaari.data.network.retrofit;

import com.nepal.naxa.smartnaari.data.local.model.YuwaPustaResponse;
import com.nepal.naxa.smartnaari.data.network.MyCircleDetails;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;
import com.nepal.naxa.smartnaari.data.network.SignUpDetailsResponse;
import com.nepal.naxa.smartnaari.data.network.UrlClass;
import com.nepal.naxa.smartnaari.data.network.UserDetail;
import com.nepal.naxa.smartnaari.data.network.YuwaPustaQueryResponse;

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

    @GET("get_yuwapusta")
    Call<YuwaPustaResponse> getYuwaPustaPosts();

    @FormUrlEncoded
    @POST("yuwa_pusta")
    Call<YuwaPustaQueryResponse> getYuwaPusaQueryDetails(@Field("data") String data);
}

