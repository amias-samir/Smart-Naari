package com.nepal.naxa.smartnaari.application;

import android.app.Application;

import org.greenrobot.greendao.database.Database;


/**
 * Created on 10/10/17
 * by nishon.tan@gmail.com
 */

public class SmartNaari extends Application {
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = true;



    @Override
    public void onCreate() {
        super.onCreate();


    }


}
