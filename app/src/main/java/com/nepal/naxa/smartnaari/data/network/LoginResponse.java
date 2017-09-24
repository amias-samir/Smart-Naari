package com.nepal.naxa.smartnaari.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Samir on 9/24/2017.
 */

public class LoginResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private String data;

    @SerializedName("user_data")
    @Expose
    private List<LoginDetailsModel> user_data = null;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<LoginDetailsModel> getUser_data() {
        return user_data;
    }

    public void setUser_data(List<LoginDetailsModel> user_data) {
        this.user_data = user_data;
    }


}
