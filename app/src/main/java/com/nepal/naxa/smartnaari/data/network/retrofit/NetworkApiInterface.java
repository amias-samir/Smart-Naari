package com.nepal.naxa.smartnaari.data.network.retrofit;

import com.nepal.naxa.smartnaari.data.local.model.YuwaPustaResponse;
import com.nepal.naxa.smartnaari.data.network.GetPinResponse;
import com.nepal.naxa.smartnaari.data.network.HotPotOfPassionDetails;
import com.nepal.naxa.smartnaari.data.network.MyCircleDetails;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;
import com.nepal.naxa.smartnaari.data.network.PasswordResetResponse;
import com.nepal.naxa.smartnaari.data.network.ProfileUpdateResponse;
import com.nepal.naxa.smartnaari.data.network.ServicesResponse;
import com.nepal.naxa.smartnaari.data.network.SignUpDetailsResponse;
import com.nepal.naxa.smartnaari.data.network.UserDetail;
import com.nepal.naxa.smartnaari.data.network.AskAnOwlResponse;
import com.nepal.naxa.smartnaari.data.network.service.MaChupBasdinaResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface NetworkApiInterface {
    @FormUrlEncoded
    @POST("smartapi/register")
    Call<SignUpDetailsResponse> getSignupDetails(@Field("data") String data);

    @FormUrlEncoded
    @POST("smartapi/login")
    Call<UserDetail> getUserData(@Field("data") String data);

    @FormUrlEncoded
    @POST("smartapi/add_circle")
    Call<MyCircleDetails> getCircleData(@Field("data") String data);

    @GET("smartapi/get_owl")
    Call<OwlWrapper> getOwls();

    @FormUrlEncoded
    @POST("smartapi/get_yuwapusta")
    Call<YuwaPustaResponse> getYuwaPustaPosts(@Field("data") String data);

    @FormUrlEncoded
    @POST("smartapi/yuwa_pusta")
    Call<AskAnOwlResponse> getAskAnOwlResponseDetails(@Field("data") String data);

    @FormUrlEncoded
    @POST("smartapi/get_services")
    Call<ServicesResponse> getServices(@Field("data") String data);


    @FormUrlEncoded
    @POST("email/mail_send")
    Call<MaChupBasdinaResponse> getMaChupBasdinaDetails(@Field("data") String data);


    @FormUrlEncoded
    @POST("Smartapi/get_content")
    Call<HotPotOfPassionDetails> getHotPotOfPassion(@Field("data") String data);


    @FormUrlEncoded
    @POST("UpdateController/password_reset")
    Call<GetPinResponse> getPasswordResetPin(@Field("data_pin") String data);

    @FormUrlEncoded
    @POST("UpdateController/password_reset")
    Call<PasswordResetResponse> getPasswordResetResponse(@Field("data_password") String data);

    @Multipart
    @POST("smartapi/profile_update")
    Call<ProfileUpdateResponse> getProfileUpdateDetails(@Part MultipartBody.Part file,
                                                        @Part("data") RequestBody jsonToSend);
}

