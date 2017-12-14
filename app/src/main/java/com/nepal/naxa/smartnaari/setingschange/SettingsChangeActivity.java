package com.nepal.naxa.smartnaari.setingschange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.mycircle.PermissionActivity;
import com.nepal.naxa.smartnaari.mycircle.powerbutton.PowerButtonService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsChangeActivity extends BaseActivity {

    @BindView(R.id.settings_enable_disable_mycircle)
    Button btnEnableDisableMycircle;
    SessionManager sessionManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change);
        ButterKnife.bind(this);

        initToolbar();

        sessionManager = new SessionManager(this);

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

        if(sessionManager.doesHaveIntentBackgroundService()){
            stopMyCircleService();
        }else {
            startMyCircleService();
        }
    }


    public void setMyCircleServiceToggleBtnText (){
        if(sessionManager.doesHaveIntentBackgroundService()){
            btnEnableDisableMycircle.setText("Disable MyCircle");

        }else {
            btnEnableDisableMycircle.setText("Enable MyCircle");
        }
    }


    public void startMyCircleService (){
        sessionManager.clearPowerButtonServicePreferences();
        Intent intent = new Intent(SettingsChangeActivity.this, PermissionActivity.class);
        startActivity(intent);
//        btnEnableDisableMycircle.setText("Disable MyCircle");

    }


    public void stopMyCircleService (){
        sessionManager.clearPowerButtonServicePreferences();
//        PowerButtonService powerButtonService = new PowerButtonService();
//        powerButtonService.onDestroy();
        btnEnableDisableMycircle.setText("Enable MyCircle");

    }



}
