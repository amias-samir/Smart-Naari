package com.nepal.naxa.smartnaari.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by susan on 9/24/2017.
 */

public class SignUpModel {
    @SerializedName("username")
    String userName;
    @SerializedName("password")
    String password;
    @SerializedName("first_name")
    String firstName;
    @SerializedName("surname")
    String surName;
    @SerializedName("dob")
    String dob;
    @SerializedName("gender")
    String gender;
    @SerializedName("birth_district")
    String birthDistrict;
    @SerializedName("current_district")
    String currentDistrict;
    @SerializedName("email")
    String email;
    @SerializedName("personal_mobile_number")
    String personalMobile;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDistrict() {
        return birthDistrict;
    }

    public void setBirthDistrict(String birthDistrict) {
        this.birthDistrict = birthDistrict;
    }

    public String getCurrentDistrict() {
        return currentDistrict;
    }

    public void setCurrentDistrict(String currentDistrict) {
        this.currentDistrict = currentDistrict;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalMobile() {
        return personalMobile;
    }

    public void setPersonalMobile(String personalMobile) {
        this.personalMobile = personalMobile;
    }
}
