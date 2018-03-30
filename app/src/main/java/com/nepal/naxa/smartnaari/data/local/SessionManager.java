package com.nepal.naxa.smartnaari.data.local;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.nepal.naxa.smartnaari.data.network.MyCircleData;
import com.nepal.naxa.smartnaari.data.network.UserData;
import com.nepal.naxa.smartnaari.debug.Dump;

import org.json.JSONException;
import org.json.JSONObject;

import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_HAS_INTENT_SERVICE;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_HAS_USER_LEARNED_APP;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_IS_USER_MY_CIRCLE_FIRST_TIME_;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_IS_USER_MY_CIRCLE_PROTECTOR_FIRST_TIME_;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_MY_CIRCLE;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_USER_DATA;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_USER_MY_CIRCLE_PASSWORD;

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

        String json ="";
//        String json = gson.toJson(myCircleData);

        try {
            JSONObject header = new JSONObject();

            header.put("user_id", myCircleData.getUserId());
            header.put("c1",myCircleData.getContactName1());
            header.put("n1",myCircleData.getContactNumber1());
            header.put("c2",myCircleData.getContactName2());
            header.put("n2",myCircleData.getContactNumber2());
            header.put("c3",myCircleData.getContactName3());
            header.put("n3",myCircleData.getContactNumber3());
            header.put("c4",myCircleData.getContactName4());
            header.put("n4",myCircleData.getContactNumber4());
            header.put("c5",myCircleData.getContactName5());
            header.put("n5",myCircleData.getContactNumber5());

            json = header.toString();

        } catch (Exception e ){
            e.printStackTrace();
        }

//todo
// Data aairaako xa but GSON to JSON convert vairaa chhaina

        Log.d(TAG, "saveUserCircle: SAMIR JSON:= "+json);
        utils.setValue(KEY_MY_CIRCLE, json);
    }

    public MyCircleData getMyCircleContact() {
        MyCircleData myCircleData = new MyCircleData();

        String json = utils.getStringValue(KEY_MY_CIRCLE, null);
//        myCircleData = gson.fromJson(json, MyCircleData.class);

        if(json == null){

        }else {
            try {
                JSONObject jsonObject = new JSONObject(json);
                myCircleData.setContactNumber1(jsonObject.getString("n1"));
                myCircleData.setContactNumber2(jsonObject.getString("n2"));
                myCircleData.setContactNumber3(jsonObject.getString("n3"));
                myCircleData.setContactNumber4(jsonObject.getString("n4"));
                myCircleData.setContactNumber5(jsonObject.getString("n5"));
                myCircleData.setContactName1(jsonObject.getString("c1"));
                myCircleData.setContactName2(jsonObject.getString("c2"));
                myCircleData.setContactName3(jsonObject.getString("c3"));
                myCircleData.setContactName4(jsonObject.getString("c4"));
                myCircleData.setContactName5(jsonObject.getString("c5"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "getMyCircleContact: JSON:= "+json);
        Log.d(TAG, "getMyCircleContact: JSON:= "+myCircleData.getContactNumber1());


        return myCircleData;
    }


    public String usresCircleContact(){

        String json = utils.getStringValue(KEY_MY_CIRCLE, null);
        return json ;

    }

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

        Log.d(TAG, "getUser: JSON == "+json);
//        Log.d(TAG, "getUser: 1 == "+userData.getCircleMobileNumber1());
//        Log.d(TAG, "getUser: 2 == "+userData.getCircleMobileNumber2());
//        Log.d(TAG, "getUser: 3 == "+userData.getCircleMobileNumber3());
//        Log.d(TAG, "getUser: 4 == "+userData.getCircleMobileNumber4());
//        Log.d(TAG, "getUser: 5 == "+userData.getCircleMobileNumber5());
        return userData;
    }

    public String getUserId() {

        return getUser().getUserId();
    }


    public boolean doesUserHaveCircle() {

        MyCircleData userData = getMyCircleContact();

        return ( !TextUtils.isEmpty(userData.getContactNumber1()) ||
                !TextUtils.isEmpty(userData.getContactNumber1()) ||
                !TextUtils.isEmpty(userData.getContactNumber1()) ||
                !TextUtils.isEmpty(userData.getContactNumber1()) ||
                !TextUtils.isEmpty(userData.getContactNumber1()) );


    }


    public boolean isUserSessionActive() {
        return getUser() != null;

    }

    public boolean isFirstTimeLoad() {
        return utils.getBooleanValue(KEY_HAS_USER_LEARNED_APP, true);
    }

//    ================================ check background servive status =========================//
    public void isPowerButtonServiceRunning(Boolean aBoolean){
        utils.setValue(KEY_HAS_INTENT_SERVICE, aBoolean);
    }

    public void clearPowerButtonServicePreferences(){
        utils.setValue(KEY_HAS_INTENT_SERVICE, false);
    }

    public boolean doesHaveIntentBackgroundService (){
        return utils.getBooleanValue(KEY_HAS_INTENT_SERVICE, false);
    }

//    ================================ end of checking background servive status====================== //

    public boolean isMyCircleFirstTimeLoad(){
        return utils.getBooleanValue(KEY_IS_USER_MY_CIRCLE_FIRST_TIME_, true);
    }

    public void setIsMyCircleFirstTimeLoad(Boolean aboolean){
        utils.setValue(KEY_IS_USER_MY_CIRCLE_FIRST_TIME_, aboolean);
    }
//    my circle password
    public boolean isMyCircleProtectorFirstTimeLoad(){
        return utils.getBooleanValue(KEY_IS_USER_MY_CIRCLE_PROTECTOR_FIRST_TIME_, true);
    }

    public void setIsMyCircleProtectorFirstTimeLoad(Boolean aboolean){
        utils.setValue(KEY_IS_USER_MY_CIRCLE_PROTECTOR_FIRST_TIME_, aboolean);
    }

    public void setUserMyCirclePassword(String password){
        utils.setValue(KEY_USER_MY_CIRCLE_PASSWORD, password);
    }
    public String getUserMyCirclePassword(){
        return utils.getStringValue(KEY_USER_MY_CIRCLE_PASSWORD, null);
    }


    public void logoutUser (){
        utils.clear();
    }

}
