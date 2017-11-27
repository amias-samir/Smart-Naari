package com.nepal.naxa.smartnaari.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.mycircle.powerbutton.PowerButtonService;

import java.io.IOException;

/**
 * Created by samir on 11/26/2017.
 */

public class NotificationHelperActivity  extends Activity {

    private NotificationHelperActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context =this;
        String action= (String)getIntent().getExtras().get("DO");



        if(action.equals("disable")){
//            new MyNotification(this);
            new PowerButtonService().stopSelf();
        }

//        if(!action.equals("reboot"))
//            finish();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}

