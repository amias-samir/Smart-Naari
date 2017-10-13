package com.nepal.naxa.smartnaari.data.network;

import java.util.List;


import com.google.gson.annotations.SerializedName;

public class OwlWrapper {

    @SerializedName("data")
    private List<OwlData> data;

    @SerializedName("status")
    private String status;

    public void setData(List<OwlData> data) {
        this.data = data;
    }

    public List<OwlData> getData() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}