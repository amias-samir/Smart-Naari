
package com.nepal.naxa.smartnaari.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birth_district")
    @Expose
    private String birthDistrict;
    @SerializedName("current_district")
    @Expose
    private String currentDistrict;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("personal_mobile_number")
    @Expose
    private String personalMobileNumber;
    @SerializedName("circle_mobile_number_1")
    @Expose
    private String circleMobileNumber1;
    @SerializedName("circle_mobile_number_2")
    @Expose
    private String circleMobileNumber2;
    @SerializedName("circle_mobile_number_3")
    @Expose
    private String circleMobileNumber3;
    @SerializedName("circle_mobile_number_4")
    @Expose
    private String circleMobileNumber4;
    @SerializedName("circle_mobile_number_5")
    @Expose
    private String circleMobileNumber5;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getPersonalMobileNumber() {
        return personalMobileNumber;
    }

    public void setPersonalMobileNumber(String personalMobileNumber) {
        this.personalMobileNumber = personalMobileNumber;
    }

    public String getCircleMobileNumber1() {
        return circleMobileNumber1;
    }

    public void setCircleMobileNumber1(String circleMobileNumber1) {
        this.circleMobileNumber1 = circleMobileNumber1;
    }

    public String getCircleMobileNumber2() {
        return circleMobileNumber2;
    }

    public void setCircleMobileNumber2(String circleMobileNumber2) {
        this.circleMobileNumber2 = circleMobileNumber2;
    }

    public String getCircleMobileNumber3() {
        return circleMobileNumber3;
    }

    public void setCircleMobileNumber3(String circleMobileNumber3) {
        this.circleMobileNumber3 = circleMobileNumber3;
    }

    public String getCircleMobileNumber4() {
        return circleMobileNumber4;
    }

    public void setCircleMobileNumber4(String circleMobileNumber4) {
        this.circleMobileNumber4 = circleMobileNumber4;
    }

    public String getCircleMobileNumber5() {
        return circleMobileNumber5;
    }

    public void setCircleMobileNumber5(String circleMobileNumber5) {
        this.circleMobileNumber5 = circleMobileNumber5;
    }

}
