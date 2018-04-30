package com.nepal.naxa.smartnaari.data.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 4/26/2018.
 */

public class ProfileUpdateResponse {

    @SerializedName("status")
    String status;
    @SerializedName("msg")
    String data;

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
}
