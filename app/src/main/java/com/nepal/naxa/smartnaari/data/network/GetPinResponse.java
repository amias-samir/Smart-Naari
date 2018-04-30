package com.nepal.naxa.smartnaari.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 4/29/2018.
 */

public class GetPinResponse {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("pin")
    @Expose
    String pin ;

    @SerializedName("msg")
    @Expose
    String msg ;

    public GetPinResponse(String status, String pin, String msg) {
        this.status = status;
        this.pin = pin;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
