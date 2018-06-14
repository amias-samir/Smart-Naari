package com.nepal.naxa.smartnaari.machupbasdina;

import com.google.gson.annotations.SerializedName;

public class Feedback {

    @SerializedName("who_helped")
    public String whoHelped;

    @SerializedName("how_info_helped")
    public String howInfoHelped;

    @SerializedName("user_id")
    public String userId;

    public Feedback(String whoHelped, String howInfoHelped, String userId) {
        this.whoHelped = whoHelped;
        this.howInfoHelped = howInfoHelped;
        this.userId = userId;
    }

    public String getWhoHelped() {
        return whoHelped;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "whoHelped='" + whoHelped + '\'' +
                ", howInfoHelped='" + howInfoHelped + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getHowInfoHelped() {
        return howInfoHelped;
    }

    public String getUserId() {
        return userId;
    }
}
