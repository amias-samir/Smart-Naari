package com.nepal.naxa.smartnaari.data.network.retrofit;

import com.nepal.naxa.smartnaari.data.network.MyCircleDetails;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;
import com.nepal.naxa.smartnaari.data.network.SignUpDetailsResponse;
import com.nepal.naxa.smartnaari.data.network.UrlClass;
import com.nepal.naxa.smartnaari.data.network.UserDetail;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface NetworkApiInterface {
    @FormUrlEncoded
    @POST(UrlClass.SIGNUP_URL)
    Call<SignUpDetailsResponse> getSignupDetails(@Field("data") String data);

    @FormUrlEncoded
    @POST("login")
    Call<UserDetail> getUserData(@Field("data") String data);

    @FormUrlEncoded
    @POST("add_circle")
    Call<MyCircleDetails> getCircleData(@Field("data") String data);

    @GET("get_owl")
    Call<OwlWrapper> getOwls();
}

