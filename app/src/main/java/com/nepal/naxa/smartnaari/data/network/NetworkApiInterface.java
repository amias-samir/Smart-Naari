package com.nepal.naxa.smartnaari.data.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface NetworkApiInterface {
    @FormUrlEncoded
    @POST("control/check_ime")
    Call<SignUpDetailsResponse> getSignupDetails(@Field("data") String data);

    @FormUrlEncoded
    @POST("control/check_ime")
    Call<LoginResponse> getLoginDetails(@Field("data") String data);







}
