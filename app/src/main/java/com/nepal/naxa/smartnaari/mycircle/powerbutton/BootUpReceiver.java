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
//        /****** For Start Activity *****/
//        Intent i = new Intent(context, MyActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);

        /***** For start Service  ****/
        Intent myIntent = new Intent(context, PowerButtonService.class);
        context.startService(myIntent);
    }

}