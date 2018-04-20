package com.nepal.naxa.smartnaari.application;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import com.nepal.naxa.smartnaari.R;

import java.util.concurrent.atomic.AtomicInteger;

class NotificationUtils {

    private final static AtomicInteger c = new AtomicInteger(0);

    public static void createServicesFeedbackNotification() {
        notifyHeadsUp("Ma Chup Basdina feedback form", "Tap here to help us improve Smart Naari");
    }


    public static void notifyHeadsUp(String title, String msg) {
        int notificationId = getID();
        Context context = SmartNaari.getAppContext();
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_logo_notification)
                .setContentTitle(title)
                .setTicker(msg)
                .setContentText(msg);

        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(new long[0]);
        if (Build.VERSION.SDK_INT >= 15) builder.setPriority(Notification.PRIORITY_HIGH);

        Notification n = builder.build();


        if (manager != null) {
            manager.notify(notificationId, n);
        }


    }


    public static int getID() {
        return c.incrementAndGet();
    }
}
