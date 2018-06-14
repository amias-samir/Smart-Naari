package com.nepal.naxa.smartnaari.application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaFeedBackForm;
import com.nepal.naxa.smartnaari.R;

import java.util.concurrent.atomic.AtomicInteger;

//todo https://medium.com/exploring-android/exploring-android-o-notification-channels-94cd274f604c
class NotificationUtils {

    private final static AtomicInteger c = new AtomicInteger(0);

    public static void createServicesFeedbackNotification() {
        notifyHeadsUp("'Ma Chup Basdina' feedback form", "Tap here to help us improve Smart Naari");
    }


    private static void notifyHeadsUp(String title, String msg) {
        int notificationId = getID();
        Context context = SmartNaari.getAppContext();
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notifyIntent = new Intent(context, MaChupBasdinaFeedBackForm.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );


        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_logo_notification)
                .setContentTitle(title)
                .setTicker(msg)
                .setContentText(msg)
                .setContentIntent(notifyPendingIntent);

        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(new long[0]);
        if (Build.VERSION.SDK_INT >= 15) builder.setPriority(Notification.PRIORITY_HIGH);

        Notification n = builder.build();


        if (manager != null) {
            manager.notify(notificationId, n);
        }


    }


    private static int getID() {
        return c.incrementAndGet();
    }
}
