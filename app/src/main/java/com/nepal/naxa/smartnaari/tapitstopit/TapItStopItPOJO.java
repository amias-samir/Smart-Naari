package com.nepal.naxa.smartnaari.tapitstopit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 2/19/2018.
 */

public class TapItStopItPOJO {
    @SerializedName("Type")
    String type;

    @SerializedName("Name")
    String name;

    @SerializedName("Number")
    String contact;

    @SerializedName("Number2")
    String contact2;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }


}
