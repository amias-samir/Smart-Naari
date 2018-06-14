package com.nepal.naxa.smartnaari.mycircle.shake;


import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RemoteViews;

import com.nepal.naxa.smartnaari.R;

import java.util.concurrent.TimeUnit;



/**
 * Created on 10/12/17
 * by nishon.tan@gmail.com
 */

public class ShakeService extends Service implements SensorEventListener {

    private static String TAG = "ShakeService";
    private String name;
    private SensorManager sensorManager;
    private Vibrator v;
    boolean listenSensorIndefinetely;
    private static final String EXTRA_ENABLE_INDIFINITE_MODE = "popup_service_burst_mode";

    private View mView;
    private WindowManager wm;
    private WindowManager.LayoutParams params;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startIndefinite(Context context) {

        Intent intent = new Intent(context, ShakeService.class);
        intent.putExtra(EXTRA_ENABLE_INDIFINITE_MODE, true);
        context.startService(intent);
    }

    public static void startService(Context context) {

        Log.i(TAG, "startService ");
        Intent intent = new Intent(context, ShakeService.class);
        intent.putExtra(EXTRA_ENABLE_INDIFINITE_MODE, false);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        listenSensorIndefinetely = intent.getBooleanExtra(EXTRA_ENABLE_INDIFINITE_MODE, true);
        Log.i(TAG, "onStartCommand  " + listenSensorIndefinetely);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        startForeground(1, createNotification("Shake Device"));
        setupAccelerometer();

    }

    private void setupAccelerometer() {

        if (listenSensorIndefinetely) {

            Log.i(TAG, "Listening to shake indefinetly ");
            listenToSensor();
        } else {
            long totalDuration = TimeUnit.SECONDS.toMillis(5);
            Log.i(TAG, "Listening to shake for  " + totalDuration + " miliseconds ");
            showShakeActivatedMsg();
            setServiceCloseCounter(totalDuration);
        }
    }

    private void setServiceCloseCounter(final long totalDuration) {
        new CountDownTimer(totalDuration, TimeUnit.SECONDS.toMillis(1)) {
            @Override
            public void onTick(long millisUntilFinished) {


                listenToSensor();


            }

            @Override
            public void onFinish() {
                ShakeService.this.stopSelf();
                hideShakeActivatedMsg();

            }
        }.start();
    }

    private void listenToSensor() {
        ServiceActivity.isServiceRunning = true;
        sensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        sensorManager.registerListener(ShakeService.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 1);
        v = ((Vibrator) getSystemService(VIBRATOR_SERVICE));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.sensorManager.unregisterListener(this);
        ServiceActivity.isServiceRunning = false;

        Log.i(TAG, "Shake Service onDestroy() ");
    }


    private Notification createNotification(String s) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker(getResources().getString(R.string.action_search));
        builder.setSmallIcon(R.drawable.ic_logo_notification);
        builder.setAutoCancel(true);

        Notification notification = builder.build();
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);


        contentView.setTextViewText(R.id.textView, s);

        notification.contentView = contentView;

        if (Build.VERSION.SDK_INT >= 16) {
            // Inflate and set the layout for the expanded notification view
            RemoteViews expandedView =
                    new RemoteViews(getPackageName(), R.layout.notification_expanded);
            notification.bigContentView = expandedView;
        }
//        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        nm.notify(0, notification);
//
        return notification;
    }


    @Override
    public void onSensorChanged(SensorEvent paramSensorEvent) {
        if (paramSensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(paramSensorEvent);
        }
    }

    private void getAccelerometer(SensorEvent paramSensorEvent) {
        float data[] = paramSensorEvent.values;

        float x = data[0];
        float y = data[1];
        float z = data[2];


        if ((x * x + y * y + z * z) / 50.0F >= 10) {

            startService(new Intent(this, LocationMessageService.class));
            this.v.vibrate(500L);
        }
    }

    private void showShakeActivatedMsg() {

        mView = LayoutInflater.from(this).inflate(R.layout.layout_shake_activated, null);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2010, 4194600, -3);
        params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        wm.addView(mView, params);
    }

    private void hideShakeActivatedMsg() {
        wm.removeViewImmediate(mView);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
