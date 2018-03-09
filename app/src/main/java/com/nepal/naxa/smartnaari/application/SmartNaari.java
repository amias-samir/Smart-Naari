package com.nepal.naxa.smartnaari.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.nepal.naxa.smartnaari.data.local.model.DaoMaster;
import com.nepal.naxa.smartnaari.data.local.model.DaoSession;
import com.nepal.naxa.smartnaari.data.local.model.DbOpenHelper;
import com.nepal.naxa.smartnaari.debug.AppLogger;


/**
 * Created on 10/10/17
 * by nishon.tan@gmail.com
 */

public class SmartNaari extends MultiDexApplication {

    private static Context context;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        AppLogger.init();

        context = getApplicationContext();
        daoSession = new DaoMaster(new DbOpenHelper(this, "smart_naari.db").getWritableDb()).newSession();

    }


    public static Context getAppContext() {
        return SmartNaari.context;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


}
