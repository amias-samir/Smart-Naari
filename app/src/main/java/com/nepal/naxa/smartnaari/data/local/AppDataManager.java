package com.nepal.naxa.smartnaari.data.local;

import android.content.Context;
import android.database.Cursor;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.application.SmartNaari;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.model.DaoSession;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestionDao;
import com.nepal.naxa.smartnaari.data.network.HotPotOfPassionData;
import com.nepal.naxa.smartnaari.data.network.HotPotOfPassionDataDao;
import com.nepal.naxa.smartnaari.data.network.OwlData;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;
import com.nepal.naxa.smartnaari.data.network.ServicesData;
import com.nepal.naxa.smartnaari.data.network.ServicesDataDao;
import com.nepal.naxa.smartnaari.debug.Dump;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;

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

//    ==================== owl lis ==============================//
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
//    ============================================================//

//================================ yuwa pusta question answer =======================//
    public void prepareToSaveYuwaQuestions( List<YuwaQuestion> yuwaQuestion) {
     //loop
//new Thread(new Runnable() {
//    @Override
//    public void run() {

        for (int i = 0; i < yuwaQuestion.size(); i++) {
            try {
                if (daoSession.getYuwaQuestionDao().count() == 0) {
                    daoSession.getYuwaQuestionDao().insertOrReplaceInTx(yuwaQuestion.get(i));
                } else {
                    if (yuwaQuestion.get(i).getIsDeleted() == 1) {

                        final DeleteQuery<YuwaQuestion> tableDeleteQuery = daoSession.queryBuilder(YuwaQuestion.class)
                                .where(YuwaQuestionDao.Properties.IsDeleted.eq("1"))
                                .buildDelete();
                        tableDeleteQuery.executeDeleteWithoutDetachingEntities();
                        daoSession.clear();
//                Log.e(TAG, "prepareToSaveYuwaQuestions: "+"!!!!!!! row deleted !!!!!!! \n table id :"+yuwaQuestion.get(i).getIdString() );

                    } else {
                        daoSession.getYuwaQuestionDao().insertOrReplaceInTx(yuwaQuestion.get(i));
//                Log.e(TAG, "prepareToSaveYuwaQuestions: "+"!!!!!!! row inserted !!!!!!! \n table id :"+yuwaQuestion.get(i).getIdString() );

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
//    }
//});
 }

    public List<YuwaQuestion> getAllYuwaQuestions() {
        return daoSession.getYuwaQuestionDao().loadAll();
    }
//=====================================================================================//

//============================= services list ========================================//
    public void prepareToSaveServices( List<ServicesData> servicesData) {
//        if (Thread.currentThread() == Looper.getMainLooper().getThread())Log.d("Samir","Main");

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        Log.e(TAG, "prepareToServicesData: "+"!!!!!!! row size !!!!!!! \n row size :" +servicesData.size() );

        //loop
        for (int i = 0; i < servicesData.size(); i++) {
            try {
//                Log.e(TAG, "prepareToServicesData: "+"!!!!!!! row id !!!!!!! \n row id :" +i );

                if (daoSession.getServicesDataDao().count() == 0) {
                    daoSession.getServicesDataDao().insertOrReplaceInTx(servicesData.get(i));
                    Log.e(TAG, "prepareToServicesData: "+"!!!!!!! row empty !!!!!!! \n table id :"+servicesData.get(i).getServiceId() );
                }
                else {
                    if (servicesData.get(i).getIsDelete().equals("1")) {

                        final DeleteQuery<ServicesData> tableDeleteQuery = daoSession.queryBuilder(ServicesData.class)
                                .where(ServicesDataDao.Properties.IsDelete.eq("1"))
                                .buildDelete();
                        tableDeleteQuery.executeDeleteWithoutDetachingEntities();
                        daoSession.clear();
                Log.e(TAG, "prepareToSaveServicesData: "+"!!!!!!! row deleted !!!!!!! \n table id :"+servicesData.get(i).getServiceId() );

                    } else {
                        daoSession.getServicesDataDao().insertOrReplaceInTx(servicesData.get(i));
                Log.e(TAG, "prepareToServicesData: "+"!!!!!!! row inserted !!!!!!! \n table id :"+servicesData.get(i).getServiceId() );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//            }
//        });
    }

    public List<ServicesData> getAllServicesdata() {
        return daoSession.getServicesDataDao().loadAll();
    }

    public ArrayList<String> getAllUniqueServicesType(){

        String SQL_DISTINCT_SERVICES_TYPE = "SELECT DISTINCT "+ServicesDataDao.Properties.OfficeType.columnName+" FROM "+ServicesDataDao.TABLENAME;

        ArrayList<String> result = new ArrayList<>();
        Cursor c = daoSession.getDatabase().rawQuery(SQL_DISTINCT_SERVICES_TYPE, null);
        try{
            if (c.moveToFirst()) {
                do {

                    result.add(c.getString(0));
                    Log.d(TAG, "getAllServicesType: "+c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
//        Log.e(TAG, "runAddMarkerSAMIR: "+ "result size "+result.size() );
        return result;

    }
//====================================================================================//


//    ======================================= HotPotOfPassion ===========================//
public void prepareToSaveHotPotOfPassion(final List<HotPotOfPassionData> hotPotOfPassionData) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
    //loop
    for(int i = 0 ; i< hotPotOfPassionData.size(); i++){
        try {
            if(daoSession.getHotPotOfPassionDataDao().count() == 0 ){
                daoSession.getHotPotOfPassionDataDao().insertOrReplaceInTx(hotPotOfPassionData.get(i));
            }
            else {
                if (hotPotOfPassionData.get(i).getIsDelete() .equals("1")) {

                    final DeleteQuery<HotPotOfPassionData> tableDeleteQuery = daoSession.queryBuilder(HotPotOfPassionData.class)
                            .where(HotPotOfPassionDataDao.Properties.IsDelete.eq("1"))
                            .buildDelete();
                    tableDeleteQuery.executeDeleteWithoutDetachingEntities();
                    daoSession.clear();
                Log.e(TAG, "prepareToSaveHOtPotData: "+"!!!!!!! row deleted !!!!!!! \n table id :"+hotPotOfPassionData.get(i).getCntId() );

                } else {
                    daoSession.getHotPotOfPassionDataDao().insertOrReplaceInTx(hotPotOfPassionData.get(i));
                Log.e(TAG, "prepareToSaveHotPotData: "+"!!!!!!! row inserted !!!!!!! \n table id :"+hotPotOfPassionData.get(i).getCntId() );
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
//            }
//        });
}

    public List<HotPotOfPassionData> getAllHotPotOfPassiondata() {
        return daoSession.getHotPotOfPassionDataDao().loadAll();
    }
//    ===================================================================================//

    @SuppressWarnings("unchecked")
    public String getLastSyncDateTime(Class classname) {

        Object object;
        QueryBuilder<String> dateAndTimes;
        String dateTime = "";

        long rowCount = daoSession.getDao(classname).count();

        if (rowCount > 0) {

            try {
                dateAndTimes = daoSession.getDao(classname).queryBuilder().orderRaw("last_sync_date_time").limit(1);
                object = dateAndTimes.list().get(0);
                dateTime = parseResponseCode(object);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }


        return dateTime;

    }


    private String parseResponseCode(Object someObject) throws NullPointerException, IllegalAccessException {

        String dateTime = "";

        for (Field field : someObject.getClass().getDeclaredFields()) {

            field.setAccessible(true);
            Object value;

            value = field.get(someObject);
            Log.d(TAG, "getLastSyncDateTime: "+value.toString());

//            Log.d(TAG, "parseResponseCode: "+field.getName());


            if (field.getName().equalsIgnoreCase("lastSyncDateTime")) {
                dateTime = value.toString();
                Log.d(TAG, "getLastSyncDateTime: Inside Condition "+value.toString());
            }
        }

        return dateTime;
    }

    public void clearAllDAOSessiondata(){
        daoSession.getServicesDataDao().deleteAll();
        daoSession.getYuwaQuestionDao().deleteAll();
        daoSession.getHotPotOfPassionDataDao().deleteAll();
    }

}
