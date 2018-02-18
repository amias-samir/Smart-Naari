
package com.nepal.naxa.smartnaari.calendraevent;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalendraEvent {

    @SerializedName("nepali_date")
    @Expose
    private String nepaliDate;
    @SerializedName("english_date")
    @Expose
    private String englishDate;
    @SerializedName("day")
    @Expose
    private List<Day> day = null;

    public String getNepaliDate() {
        return nepaliDate;
    }

    public void setNepaliDate(String nepaliDate) {
        this.nepaliDate = nepaliDate;
    }

    public String getEnglishDate() {
        return englishDate;
    }

    public void setEnglishDate(String englishDate) {
        this.englishDate = englishDate;
    }

    public List<Day> getDay() {
        return day;
    }

    public void setDay(List<Day> day) {
        this.day = day;
    }

}
