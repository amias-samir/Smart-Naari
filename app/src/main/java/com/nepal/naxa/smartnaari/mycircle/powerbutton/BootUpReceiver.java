package com.nepal.naxa.smartnaari.mycircle.powerbutton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by samir on 11/24/2017.
 */

public class BootUpReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, PowerButtonService.class);
        context.startService(myIntent);
    }




}