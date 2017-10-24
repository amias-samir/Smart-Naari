package com.nepal.naxa.smartnaari.data.local;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.application.SmartNaari;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.model.DaoSession;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestionDao;
import com.nepal.naxa.smartnaari.data.network.OwlData;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;
import com.nepal.naxa.smartnaari.debug.Dump;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_OWL_LIST;

/**
 * Created on 10/11/17
 * by nishon.tan@gmail.com
 */

public class AppDataManager extends BaseActivity {

    private static final String TAG = "AppDataManager";
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

    public void prepareToSaveYuwaQuestions(List<YuwaQuestion> yuwaQuestion) {
     //loop
        for(int i = 0 ; i< yuwaQuestion.size(); i++){
            if(yuwaQuestion.get(i).getIsDeleted() == 1 ){

                final DeleteQuery<YuwaQuestion> tableDeleteQuery = daoSession.queryBuilder(YuwaQuestion.class)
                        .where(YuwaQuestionDao.Properties.IsDeleted.eq("1"))
                        .buildDelete();
                tableDeleteQuery.executeDeleteWithoutDetachingEntities();
                daoSession.clear();
                Log.e(TAG, "prepareToSaveYuwaQuestions: "+"!!!!!!! row deleted !!!!!!! \n table id :"+yuwaQuestion.get(i).getIdString() );

            }
            else {
                daoSession.getYuwaQuestionDao().insertOrReplaceInTx(yuwaQuestion.get(i));
                Log.e(TAG, "prepareToSaveYuwaQuestions: "+"!!!!!!! row inserted !!!!!!! \n table id :"+yuwaQuestion.get(i).getIdString() );

            }
        }



    }

//        public void saveYuwaQuestions(List<YuwaQuestion> yuwaQuestion) {
//
//        daoSession.getYuwaQuestionDao().insertOrReplaceInTx(yuwaQuestion);
//    }

    public List<YuwaQuestion> getAllYuwaQuestions() {
        return daoSession.getYuwaQuestionDao().loadAll();
    }

    @SuppressWarnings("unchecked")
    public String getLastSyncDateTime(Class classname) {

        Object object;
        QueryBuilder<String> dateAndTimes;
        String dateTime = "";

        dateAndTimes = daoSession.getDao(classname).queryBuilder().orderRaw("last_sync_date_time").limit(1);
        object = dateAndTimes.list().get(0);

        try {
            dateTime = parseResponseCode(object);
        } catch (NullPointerException | IllegalAccessException e) {
            e.printStackTrace();

        }


        return dateTime;

    }


    private String parseResponseCode(Object someObject) throws NullPointerException, IllegalAccessException {

        String dateTime = "";

        for (Field field : someObject.getClass().getDeclaredFields()) {

            field.setAccessible(true);
            Object value;

            value = field.get(someObject);

            if (field.getName().equalsIgnoreCase("last_sync_date_time") ) {
                dateTime = value.toString();
            }
        }

        return dateTime;
    }


}
