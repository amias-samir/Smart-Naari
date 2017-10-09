package com.nepal.naxa.smartnaari.data.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface NetworkApiInterface {
    @FormUrlEncoded
    @POST(UrlClass.SIGNUP_URL)
    Call<SignUpDetailsResponse> getSignupDetails(@Field("data") String data);

    @FormUrlEncoded
    @POST("Smartapi/login")
    Call<UserDetail> getUserData(@Field("data") String data);

    @FormUrlEncoded
    @POST("Smartapi/add_circle")
    Call<MyCircleDetails> getCircleData(@Field("data") String data);


}
