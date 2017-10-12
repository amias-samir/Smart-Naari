package com.nepal.naxa.smartnaari.application;

import android.app.Application;

import com.nepal.naxa.smartnaari.data.local.model.DaoMaster;
import com.nepal.naxa.smartnaari.data.local.model.DaoSession;
import com.nepal.naxa.smartnaari.data.local.model.DbOpenHelper;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;


/**
 * Created on 10/10/17
 * by nishon.tan@gmail.com
 */

public class SmartNaari extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        daoSession = new DaoMaster(new DbOpenHelper(this, "smart_naari.db").getWritableDb()).newSession();

        if (daoSession.getYuwaQuestionDao().loadAll().size() == 0) {
            daoSession.getYuwaQuestionDao().insert(new YuwaQuestion(1L,
                    "1", "What's a good question? ", " I don't know", "2015/2/12","22"));
        }



    }


    public DaoSession getDaoSession() {
        return daoSession;
    }


}
