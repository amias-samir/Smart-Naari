package com.nepal.naxa.smartnaari.data.local;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.data.network.DataItem;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;
import com.nepal.naxa.smartnaari.data.network.UserData;

import java.util.List;

import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_OWL_LIST;
import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_USER_DATA;

/**
 * Created on 10/11/17
 * by nishon.tan@gmail.com
 */

public class AppDataManager {

    private SharedPreferenceUtils utils;
    private Gson gson;

    public AppDataManager(Context context) {
        utils = SharedPreferenceUtils.getInstance(context, SharedPreferenceUtils.PREF_NETWORK_CACHE);
        gson = new Gson();
    }


    public void saveOwls(OwlWrapper owlWrapper) {

        Gson gson = new Gson();
        String json = gson.toJson(owlWrapper.getData());
        utils.setValue(KEY_OWL_LIST, json);

    }

    public List<DataItem> getOwls() {

        List<DataItem> owls;

        String json = utils.getStringValue(KEY_OWL_LIST, null);
        owls = gson.fromJson(json, new TypeToken<List<DataItem>>() {
        }.getType());

        return owls;
    }

}
