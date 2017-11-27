package com.nepal.naxa.smartnaari.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.nepal.naxa.smartnaari.R;


/**
 * Created by samir on 11/26/2017.
 */

public class MyNotification extends Notification {

    private Context ctx;
    private NotificationManager mNotificationManager;

    public MyNotification(Context ctx){
        super();
        this.ctx=ctx;
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) ctx.getSystemService(ns);
        CharSequence tickerText = "Shortcuts";
        long when = System.currentTimeMillis();
        Notification.Builder builder = new Notification.Builder(ctx);
        Notification notification=builder.getNotification();
        notification.when=when;
        notification.tickerText=tickerText;
        notification.icon= R.mipmap.ic_launcher;

        RemoteViews contentView=new RemoteViews(ctx.getPackageName(), R.layout.notification_layout);

        //set the button listeners
        setListeners(contentView);

        notification.contentView = contentView;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        CharSequence contentTitle = "From Shortcuts";
        mNotificationManager.notify(548853, notification);
    }

    public void setListeners(RemoteViews view){
        //TODO screencapture listener
        //adb shell /system/bin/screencap -p storage/sdcard0/SimpleAndroidTest/test.png

        //app listener
        Intent app=new Intent(ctx, NotificationHelperActivity.class);
        app.putExtra("DO", "disable");
        PendingIntent pApp = PendingIntent.getActivity(ctx, 4, app, 0);
        view.setOnClickPendingIntent(R.id.disable_service, pApp);
    }

}

