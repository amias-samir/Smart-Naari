package com.nepal.naxa.smartnaari.setingschange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.mycircle.PermissionActivity;
import com.nepal.naxa.smartnaari.mycircle.activitydetect.ActivityRecognizedService;
import com.nepal.naxa.smartnaari.mycircle.location.GeoPointService;
import com.nepal.naxa.smartnaari.mycircle.location.LocationUpdateService;
import com.nepal.naxa.smartnaari.mycircle.powerbutton.PowerButtonService;
import com.nepal.naxa.smartnaari.mycircle.shake.LocationMessageService;
import com.nepal.naxa.smartnaari.mycircle.shake.ShakeService;
import com.nepal.naxa.smartnaari.splashscreen.SplashScreenActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsChangeActivity extends BaseActivity {

    @BindView(R.id.settings_enable_disable_mycircle)
    Button btnEnableDisableMycircle;
    SessionManager sessionManager;
    AppDataManager appDataManager ;
    @BindView(R.id.settings_user_logout)
    Button btnUserLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change);
        ButterKnife.bind(this);

        initToolbar();

        sessionManager = new SessionManager(this);
        appDataManager = new AppDataManager(this);

        setMyCircleServiceToggleBtnText();


    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Settings/Change");
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.black);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @OnClick(R.id.settings_enable_disable_mycircle)
    public void onViewClicked() {

        if (sessionManager.doesHaveIntentBackgroundService()) {
            stopMyCircleService();
        } else {
            startMyCircleService();
        }
    }


    public void setMyCircleServiceToggleBtnText() {
        if (sessionManager.doesHaveIntentBackgroundService()) {
            btnEnableDisableMycircle.setText("Disable MyCircle");

        } else {
            btnEnableDisableMycircle.setText("Enable MyCircle");
        }
    }


    public void startMyCircleService() {
        sessionManager.clearPowerButtonServicePreferences();
        sessionManager.isPowerButtonServiceRunning(true);
        Intent intent = new Intent(SettingsChangeActivity.this, PermissionActivity.class);
        startActivity(intent);
//        btnEnableDisableMycircle.setText("Disable MyCircle");

    }


    public void stopMyCircleService() {
        sessionManager.clearPowerButtonServicePreferences();
//        PowerButtonService powerButtonService = new PowerButtonService();
//        powerButtonService.stopSelf();

        stopService(new Intent(getApplicationContext(), ActivityRecognizedService.class));
        stopService(new Intent(getApplicationContext(), PowerButtonService.class));
        stopService(new Intent(getApplicationContext(), ShakeService.class));
        stopService(new Intent(getApplicationContext(), GeoPointService.class));
        stopService(new Intent(getApplicationContext(), LocationUpdateService.class));
        stopService(new Intent(getApplicationContext(), LocationMessageService.class));

//        Intent service = new Intent(this, PowerButtonService.class);
//        this.stopService(service);

        btnEnableDisableMycircle.setText("Enable MyCircle");

    }


    @OnClick(R.id.settings_user_logout)
    public void onViewUserLogout() {

        showLoading("Logging Out ...");

        sessionManager.logoutUser();
        appDataManager.clearAllDAOSessiondata();

//        hideLoading();

        showInfoToast("You have successfully logged out");

        Intent intent = new Intent(SettingsChangeActivity.this, SplashScreenActivity.class);
        startActivity(intent);
        finish();

    }
}
