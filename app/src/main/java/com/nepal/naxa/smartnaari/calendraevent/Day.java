
package com.nepal.naxa.smartnaari.calendraevent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("category")
    @Expose
    private String category;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
