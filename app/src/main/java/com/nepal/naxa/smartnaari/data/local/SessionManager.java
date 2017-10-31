package com.nepal.naxa.smartnaari.data.local;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.nepal.naxa.smartnaari.data.network.MyCircleData;
import com.nepal.naxa.smartnaari.data.network.UserData;
import com.nepal.naxa.smartnaari.debug.Dump;

import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_HAS_INTENT_SERVICE;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_HAS_USER_LEARNED_APP;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_MY_CIRCLE;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_USER_DATA;

/**
 * Created on 10/9/17
 * by nishon.tan@gmail.com
 */

public class SessionManager {

    private String TAG = "SessionManager";

    private SharedPreferenceUtils utils;
    private Gson gson;

    public SessionManager(Context context) {
        utils = SharedPreferenceUtils.getInstance(context, SharedPreferenceUtils.PREF_NETWORK_CACHE);
        gson = new Gson();
    }

//    ========================Circle sharedpref  data operation===================================//

    public void saveUserCircle(MyCircleData myCircleData) {


        Log.d(TAG, "saveUserCircle: SAMIR Contct name:= "+myCircleData.getContactName2());
        Dump.object(TAG, myCircleData);

//todo
// Data aairaako xa but GSON to JSON convert vairaa chhaina
        String json = gson.toJson(myCircleData);
        Log.d(TAG, "saveUserCircle: SAMIR JSON:= "+json);
        utils.setValue(KEY_MY_CIRCLE, json);
    }

    public MyCircleData getMyCircleContact() {
        MyCircleData myCircleData;

        String json = utils.getStringValue(KEY_MY_CIRCLE, null);
        myCircleData = gson.fromJson(json, MyCircleData.class);

        Log.d(TAG, "getMyCircleContact: JSON:= "+json);

        return myCircleData;
    }

//    public void usresCircleContact(){
//        MyCircleData myCircleData = getMyCircleContact();
//
//    }

//======================================== end of circle data operation ==========================//


    public void saveUser(UserData userData) {

        String json = gson.toJson(userData);
        Log.d(TAG, "saveUser: "+json);
        utils.setValue(KEY_USER_DATA, json);

    }

    public UserData getUser() {

        UserData userData;

        String json = utils.getStringValue(KEY_USER_DATA, null);
        userData = gson.fromJson(json, UserData.class);

        return userData;
    }

    public String getUserId() {

        return getUser().getUserId();
    }


    public boolean doesUserHaveCircle() {

        UserData userData = getUser();

        return TextUtils.isEmpty(userData.getCircleMobileNumber1()) ||
                TextUtils.isEmpty(userData.getCircleMobileNumber2()) ||
                TextUtils.isEmpty(userData.getCircleMobileNumber3()) ||
                TextUtils.isEmpty(userData.getCircleMobileNumber4()) ||
                TextUtils.isEmpty(userData.getCircleMobileNumber5());


    }


    public boolean isUserSessionActive() {
        return getUser() != null;

    }

    public boolean isFirstTimeLoad() {
        return utils.getBooleanValue(KEY_HAS_USER_LEARNED_APP, true);
    }

//    ================================ check background servive status =========================//
    public void setIntentServiceStatus (Boolean aBoolean){
        utils.setValue(KEY_HAS_INTENT_SERVICE, aBoolean);
    }

    public boolean doesHaveIntentBackgroundService (){
        return utils.getBooleanValue(KEY_HAS_INTENT_SERVICE, false);
    }

//    ================================ end of checking background servive status====================== //

}
