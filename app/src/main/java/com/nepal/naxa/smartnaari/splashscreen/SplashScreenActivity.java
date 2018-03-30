package com.nepal.naxa.smartnaari.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.login.LoginActivity;
import com.nepal.naxa.smartnaari.mycircle.MyCircleActivity;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;

import java.util.concurrent.TimeUnit;



public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if ( sessionManager.isUserSessionActive() && sessionManager.doesUserHaveCircle() ) {

            Intent intent = new Intent(this, BeautifulMainActivity.class);
            startActivity(intent);
            finish();

        } else if (sessionManager.isUserSessionActive()) {

            Intent intent = new Intent(this, MyCircleActivity.class);
            startActivity(intent);
            finish();

        } else {

            SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }


}
