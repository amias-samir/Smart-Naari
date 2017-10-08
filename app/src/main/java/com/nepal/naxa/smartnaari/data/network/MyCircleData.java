package com.nepal.naxa.smartnaari.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Samir on 10/8/2017.
 */

public class MyCircleData {

    @SerializedName("user_id")
    @Expose
    private static String userId;
    @SerializedName("c1")
    @Expose
    private static String contactName1;
    @SerializedName("n1")
    @Expose
    private static String contactNumber1;

    @SerializedName("c2")
    @Expose
    private static String contactName2;
    @SerializedName("n2")
    @Expose
    private static String contactNumber2;

    @SerializedName("c3")
    @Expose
    private static String contactName3;
    @SerializedName("n3")
    @Expose
    private static String contactNumber3;

    @SerializedName("c4")
    @Expose
    private static String contactName4;
    @SerializedName("n4")
    @Expose
    private static String contactNumber4;

    @SerializedName("c5")
    @Expose
    private static String contactName5;
    @SerializedName("n5")
    @Expose
    private static String contactNumber5;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactName1() {
        return contactName1;
    }

    public void setContactName1(String contactName1) {
        this.contactName1 = contactName1;
    }

    public String getContactNumber1() {
        return contactNumber1;
    }

    public void setContactNumber1(String contactNumber1) {
        this.contactNumber1 = contactNumber1;
    }

    public String getContactName2() {
        return contactName2;
    }

    public void setContactName2(String contactName2) {
        this.contactName2 = contactName2;
    }

    public String getContactNumber2() {
        return contactNumber2;
    }

    public void setContactNumber2(String contactNumber2) {
        this.contactNumber2 = contactNumber2;
    }

    public String getContactName3() {
        return contactName3;
    }

    public void setContactName3(String contactName3) {
        this.contactName3 = contactName3;
    }

    public String getContactNumber3() {
        return contactNumber3;
    }

    public void setContactNumber3(String contactNumber3) {
        this.contactNumber3 = contactNumber3;
    }

    public String getContactName4() {
        return contactName4;
    }

    public void setContactName4(String contactName4) {
        this.contactName4 = contactName4;
    }

    public String getContactNumber4() {
        return contactNumber4;
    }

    public void setContactNumber4(String contactNumber4) {
        this.contactNumber4 = contactNumber4;
    }

    public String getContactName5() {
        return contactName5;
    }

    public void setContactName5(String contactName5) {
        this.contactName5 = contactName5;
    }

    public String getContactNumber5() {
        return contactNumber5;
    }

    public void setContactNumber5(String contactNumber5) {
        this.contactNumber5 = contactNumber5;
    }


}
