package com.nepal.naxa.smartnaari.appnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.mycircle.powerbutton.NotificationReceiver;
import com.nepal.naxa.smartnaari.setingschange.SettingsChangeActivity;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;

public class BirthdayNotification extends Service {
    //Get an instance of NotificationManager//
    private static final int NOTIFICATION_ID = 12131213;


    public void showForegroundBirthdayNotification(Context context, String title, String contentText){

        try {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_logo_notification)
                            .setContentTitle(title)
                            .setContentText(contentText);

            Intent notificationIntent = new Intent(context, BeautifulMainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
