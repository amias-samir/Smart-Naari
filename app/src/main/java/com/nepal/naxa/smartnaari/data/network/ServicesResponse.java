
package com.nepal.naxa.smartnaari.data.network;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<ServicesData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ServicesData> getData() {
        return data;
    }

    public void setData(List<ServicesData> data) {
        this.data = data;
    }

}
