package com.nepal.naxa.smartnaari.mycircle.powerbutton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**https://stackoverflow.com/questions/41359337/android-notification-pendingintent-to-stop-service
 * Created on 12/4/17
 * by nishon.tan@gmail.com
 */

public class NotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, PowerButtonService.class);
        context.stopService(service);
    }
}
