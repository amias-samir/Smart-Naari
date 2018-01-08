package com.nepal.naxa.smartnaari.data.network;


import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Index;


public class OwlData {

    @Index(unique = true)
    @SerializedName("owl_id")
    private String owlId;

    @SerializedName("owl_gender")
    private String owlGender;

    @SerializedName("owl_introduction_text")
    private String owlIntroductionText;

    @SerializedName("owl_name")
    private String owlName;

    @SerializedName("photo")
    private String owlPhotoPath;

    public void setOwlGender(String owlGender) {
        this.owlGender = owlGender;
    }

    public String getOwlGender() {
        return owlGender;
    }

    public void setOwlIntroductionText(String owlIntroductionText) {
        this.owlIntroductionText = owlIntroductionText;
    }

    public String getOwlIntroductionText() {
        return owlIntroductionText;
    }

    public void setOwlName(String owlName) {
        this.owlName = owlName;
    }

    public String getOwlName() {
        return owlName;
    }

    public void setOwlId(String owlId) {
        this.owlId = owlId;
    }

    public String getOwlId() {
        return owlId;
    }


    public String getOwlPhotoPath() {
        return owlPhotoPath;
    }

    public void setOwlPhotoPath(String owlPhotoPath) {
        this.owlPhotoPath = owlPhotoPath;
    }

}