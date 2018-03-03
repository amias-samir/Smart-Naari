package com.nepal.naxa.smartnaari.data_glossary.muth_busters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samir on 12/18/2017.
 */

public class WordsWithDetailsModel implements Parcelable {

    private String category;
    private String title;
    private String desc;
    private String error;

    public WordsWithDetailsModel(Parcel in) {
        category = in.readString();
        title = in.readString();
        desc = in.readString();
    }

    public String getError() {
        return error;
    }

    public WordsWithDetailsModel(String error){
        this.error = error;
    }

    public WordsWithDetailsModel(String category, String title, String desc) {
        this.category = category;
        this.title = title;
        this.desc = desc;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(title);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WordsWithDetailsModel> CREATOR = new Creator<WordsWithDetailsModel>() {
        @Override
        public WordsWithDetailsModel createFromParcel(Parcel in) {
            return new WordsWithDetailsModel(in);
        }

        @Override
        public WordsWithDetailsModel[] newArray(int size) {
            return new WordsWithDetailsModel[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
