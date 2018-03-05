
package com.nepal.naxa.smartnaari.aboutboardmembers;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;


public class MemberPojo {

    @SerializedName("person_name")
    @Expose
    private String personName;
    @SerializedName("person_description")
    @Expose
    private String personDescription;
    @SerializedName("smart_naari_post")
    @Expose
    private String smartNaariPost;
    @SerializedName("external_post")
    @Expose
    private String externalPost;
    @SerializedName("external_office")
    @Expose
    private String externalOffice;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("type")
    @Expose
    private String type;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonDescription() {
        return personDescription;
    }

    public void setPersonDescription(String personDescription) {
        this.personDescription = personDescription;
    }

    public String getSmartNaariPost() {
        return smartNaariPost;
    }

    public void setSmartNaariPost(String smartNaariPost) {
        this.smartNaariPost = smartNaariPost;
    }

    public String getExternalPost() {
        return externalPost;
    }

    public void setExternalPost(String externalPost) {
        this.externalPost = externalPost;
    }

    public String getExternalOffice() {
        return externalOffice;
    }

    public void setExternalOffice(String externalOffice) {
        this.externalOffice = externalOffice;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
