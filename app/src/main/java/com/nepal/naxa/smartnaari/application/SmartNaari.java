package com.nepal.naxa.smartnaari.application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.nepal.naxa.smartnaari.data.local.model.DaoMaster;
import com.nepal.naxa.smartnaari.data.local.model.DaoSession;
import com.nepal.naxa.smartnaari.data.local.model.DbOpenHelper;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;
import com.nepal.naxa.smartnaari.debug.AppLogger;
import io.fabric.sdk.android.Fabric;


/**
 * Created on 10/10/17
 * by nishon.tan@gmail.com
 */

public class SmartNaari extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        AppLogger.init();

        daoSession = new DaoMaster(new DbOpenHelper(this, "smart_naari.db").getWritableDb()).newSession();

    }


    public DaoSession getDaoSession() {
        return daoSession;
    }


}
