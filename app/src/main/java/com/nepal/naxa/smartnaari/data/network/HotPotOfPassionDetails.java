
package com.nepal.naxa.smartnaari.data.network;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotPotOfPassionDetails {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<HotPotOfPassionData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HotPotOfPassionData> getData() {
        return data;
    }

    public void setData(List<HotPotOfPassionData> data) {
        this.data = data;
    }

}
