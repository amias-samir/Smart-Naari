package com.nepal.naxa.smartnaari.data.network;

/**
 * Created by Samir on 9/9/2017.
 */

public class UrlClass {

    public static final String REQUEST_OK = "200";
    public static final String REQUEST_401 = "401";
    public static final String REQUEST_400 = "400";

    public static final String BASE_URL = "http://naxa.com.np/smartnaari/smartapi/";
    public static final String SIGNUP_URL = BASE_URL + "register";
    public static final String LOGIN_URL = BASE_URL + "Smartapi/login";


    public static  String SMART_NARI_BASE_URL ;


    public static boolean isInvalidResponse(String responseCode) {
        return !responseCode.equals(UrlClass.REQUEST_OK);
    }
}
