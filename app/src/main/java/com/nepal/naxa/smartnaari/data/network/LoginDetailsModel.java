package com.nepal.naxa.smartnaari.data.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samir on 9/24/2017.
 */

public class LoginDetailsModel {

    @SerializedName("user_id")
    private String user_id;
    @SerializedName("username")
    private String username;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("dob")
    private String dob;
    @SerializedName("gender")
    private String gender;
    @SerializedName("birth_district")
    private String birth_district;
    @SerializedName("current_district")
    private String current_district;
    @SerializedName("email")
    private String email;
    @SerializedName("personal_mobile_number")
    private String personal_mobile_number;
    @SerializedName("circle_mobile_number_1")
    private String circle_mobile_number_1;
    @SerializedName("circle_mobile_number_2")
    private String circle_mobile_number_2;
    @SerializedName("circle_mobile_number_3")
    private String circle_mobile_number_3;
    @SerializedName("circle_mobile_number_4")
    private String circle_mobile_number_4;
    @SerializedName("circle_mobile_number_5")
    private String circle_mobile_number_5;


    LoginDetailsModel(String user_id, String username, String first_name, String surname, String dob, String gender, String birth_district,
                      String current_district, String email, String personal_mobile_number, String circle_mobile_number_1,
                      String circle_mobile_number_2, String circle_mobile_number_3, String circle_mobile_number_4,String circle_mobile_number_5){

        this.user_id = user_id ;
        this.username = username ;
        this.first_name = first_name;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender ;
        this.birth_district = birth_district ;
        this.current_district = current_district;
        this.email = email;
        this.personal_mobile_number = personal_mobile_number;
        this.circle_mobile_number_1 =circle_mobile_number_1;
        this.circle_mobile_number_2 = circle_mobile_number_2 ;
        this.circle_mobile_number_3 = circle_mobile_number_3;
        this.circle_mobile_number_4 = circle_mobile_number_4 ;
        this.circle_mobile_number_5 = circle_mobile_number_5 ;

    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
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

    public String getBirth_district() {
        return birth_district;
    }

    public void setBirth_district(String birth_district) {
        this.birth_district = birth_district;
    }

    public String getCurrent_district() {
        return current_district;
    }

    public void setCurrent_district(String current_district) {
        this.current_district = current_district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonal_mobile_number() {
        return personal_mobile_number;
    }

    public void setPersonal_mobile_number(String personal_mobile_number) {
        this.personal_mobile_number = personal_mobile_number;
    }

    public String getCircle_mobile_number_1() {
        return circle_mobile_number_1;
    }

    public void setCircle_mobile_number_1(String circle_mobile_number_1) {
        this.circle_mobile_number_1 = circle_mobile_number_1;
    }

    public String getCircle_mobile_number_2() {
        return circle_mobile_number_2;
    }

    public void setCircle_mobile_number_2(String circle_mobile_number_2) {
        this.circle_mobile_number_2 = circle_mobile_number_2;
    }

    public String getCircle_mobile_number_3() {
        return circle_mobile_number_3;
    }

    public void setCircle_mobile_number_3(String circle_mobile_number_3) {
        this.circle_mobile_number_3 = circle_mobile_number_3;
    }

    public String getCircle_mobile_number_4() {
        return circle_mobile_number_4;
    }

    public void setCircle_mobile_number_4(String circle_mobile_number_4) {
        this.circle_mobile_number_4 = circle_mobile_number_4;
    }

    public String getCircle_mobile_number_5() {
        return circle_mobile_number_5;
    }

    public void setCircle_mobile_number_5(String circle_mobile_number_5) {
        this.circle_mobile_number_5 = circle_mobile_number_5;
    }


    LoginDetailsModel (){

    }


}
