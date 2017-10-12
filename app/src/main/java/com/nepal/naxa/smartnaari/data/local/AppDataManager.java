package com.nepal.naxa.smartnaari.data.local;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.application.SmartNaari;
import com.nepal.naxa.smartnaari.data.local.model.DaoSession;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;
import com.nepal.naxa.smartnaari.data.network.OwlData;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;

import java.util.List;

import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_OWL_LIST;

/**
 * Created on 10/11/17
 * by nishon.tan@gmail.com
 */

public class AppDataManager {

    private SharedPreferenceUtils utils;
    private Gson gson;
    private DaoSession daoSession;

    public AppDataManager(Context context) {
        utils = SharedPreferenceUtils.getInstance(context, SharedPreferenceUtils.PREF_NETWORK_CACHE);
        gson = new Gson();
        daoSession = ((SmartNaari) context.getApplicationContext()).getDaoSession();
    }

    public void saveOwls(OwlWrapper owlWrapper) {

        String json = gson.toJson(owlWrapper.getData());
        utils.setValue(KEY_OWL_LIST, json);
    }

    public List<OwlData> getOwls() {

        List<OwlData> owls;

        String json = utils.getStringValue(KEY_OWL_LIST, null);
        owls = gson.fromJson(json, new TypeToken<List<OwlData>>() {
        }.getType());

        return owls;
    }

    public void saveYuwaQuestions(List<YuwaQuestion> yuwaQuestion) {

        daoSession.getYuwaQuestionDao().saveInTx(yuwaQuestion);
    }

    public List<YuwaQuestion> getAllYuwaQuestions() {
        return daoSession.getYuwaQuestionDao().loadAll();
    }


}
