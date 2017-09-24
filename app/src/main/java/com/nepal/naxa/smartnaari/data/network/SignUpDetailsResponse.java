package com.nepal.naxa.smartnaari.data.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by susan on 9/9/2017.
 */

public class SignUpDetailsResponse {

    @SerializedName("status")
    String status;
    @SerializedName("data")
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
