package com.nepal.naxa.smartnaari.data.local;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.nepal.naxa.smartnaari.data.network.UserData;

import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_HAS_USER_LEARNED_APP;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_USER_DATA;

/**
 * Created on 10/9/17
 * by nishon.tan@gmail.com
 */

public class SessionManager {

    private SharedPreferenceUtils utils;
    private Gson gson;

    public SessionManager(Context context) {
        utils = SharedPreferenceUtils.getInstance(context, SharedPreferenceUtils.PREF_NETWORK_CACHE);
        gson = new Gson();
    }

    public void saveUser(UserData userData) {

        String json = gson.toJson(userData);
        utils.setValue(KEY_USER_DATA, json);

    }

    private UserData getUser() {

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


}
