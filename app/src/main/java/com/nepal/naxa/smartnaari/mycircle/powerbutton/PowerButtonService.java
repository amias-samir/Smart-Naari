package com.nepal.naxa.smartnaari.mycircle.powerbutton;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.mycircle.PermissionActivity;
import com.nepal.naxa.smartnaari.mycircle.shake.ShakeService;


/**
 * Created by nishon on 10/20/17.
 */

public class PowerButtonService extends Service {

    private static final int NOTIFICATION_ID = 1213124;


    SessionManager sessionManager;

    public PowerButtonService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        showForegroundNotification("MyCircle is active", "Hold power button & shake phone to send SMS");
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.isPowerButtonServiceRunning(true);


        LinearLayout mLinear = new LinearLayout(getApplicationContext()) {


            //home or recent button
            public void onCloseSystemDialogs(String reason) {
                if ("globalactions".equals(reason)) {
                    Log.i("Key", "Long press on power button");

                    ShakeService.startService(PowerButtonService.this);


                } else if ("homekey".equals(reason)) {
                    //home key pressed
                } else if ("recentapps".equals(reason)) {
                    // recent apps button clicked
                }

                Log.i("Key", "Reason: " + reason);


            }


            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                        || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
                        || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN
                        || event.getKeyCode() == KeyEvent.KEYCODE_CAMERA
                        || event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
                    Log.i("Key", "keycode " + event.getKeyCode());
                    return true;
                }
                return super.dispatchKeyEvent(event);
            }
        };

        mLinear.setFocusable(true);
        View mView = LayoutInflater.from(this).inflate(R.layout.power_service_layout, mLinear);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        //params
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                100,
                100,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        wm.addView(mView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sessionManager.isPowerButtonServiceRunning(false);
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showForegroundNotification(String title, String contentText) {

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent toNotificationReceiver = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);


        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_logo_notification).setContentText(contentText)
                .setContentTitle(title)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(toNotificationReceiver)
                .addAction(R.drawable.ic_close_black_24dp, "Deactivate", toNotificationReceiver)
                .build();
        startForeground(NOTIFICATION_ID, notification);

    }


}
