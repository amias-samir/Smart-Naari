package com.nepal.naxa.smartnaari.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.evernote.android.job.JobManager;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.customtextfont.CustomViewWithTypefaceSupport;
import com.nepal.naxa.smartnaari.data.local.model.DaoMaster;
import com.nepal.naxa.smartnaari.data.local.model.DaoSession;
import com.nepal.naxa.smartnaari.data.local.model.DbOpenHelper;
import com.nepal.naxa.smartnaari.debug.AppLogger;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


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
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("font/HelveticaLt.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .addCustomViewWithSetTypeface(CustomViewWithTypefaceSupport.class)
                        .build()
        );
        AppLogger.init();

        context = getApplicationContext();
        daoSession = new DaoMaster(new DbOpenHelper(this, "smart_naari.db").getWritableDb()).newSession();
        JobManager.create(this).addJobCreator(new SmartNaariJobCreator());
    }


    public static Context getAppContext() {
        return SmartNaari.context;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


}
