package com.nepal.naxa.smartnaari.splashscreen;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.appnotification.BirthdayNotification;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.login.LoginActivity;
import com.nepal.naxa.smartnaari.mycircle.MyCircleActivity;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;



public class SplashScreenActivity extends BaseActivity {

    private static final String TAG = "SplashScreenActivity" ;
    BirthdayNotification birthdayNotification = new BirthdayNotification();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if ( sessionManager.isUserSessionActive() && sessionManager.doesUserHaveCircle() ) {

            checkBirthMonthAndDay(sessionManager);

            Intent intent = new Intent(this, BeautifulMainActivity.class);
            startActivity(intent);
            finish();

        } else if (sessionManager.isUserSessionActive()) {

            Intent intent = new Intent(this, MyCircleActivity.class);
            startActivity(intent);
            finish();

        } else {

            SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }

    public void checkBirthMonthAndDay(SessionManager sessionManager){

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int calanderMonth = calendar.get(Calendar.MONTH)+1;
        int calanderDay = calendar.get(Calendar.DAY_OF_MONTH);
//        Log.d(TAG, "showBirthdayNotification: Calander "+calanderMonth +" , "+calanderDay);



        String rawDOB = sessionManager.getUser().getDob();
//        Log.d(TAG, "initSpinnerDOB: " + rawDOB);
        String[] parts = rawDOB.split("/");
        String year = parts[0]; // year
        int month = Integer.parseInt(parts[1]); // month
        int day = Integer.parseInt(parts[2]); // day

        if(calanderMonth == month && calanderDay == day) {
            showForegroundBirthdayNotification(getApplicationContext(),
                    "Happy Birthday " + sessionManager.getUser().getUsername(),
                    "As you go through each year, remember to count your blessings, not your age. Count your amazing experiences," +
                            " not your mistakes. Happy Birthday to an insanely awesome person!");
        }
    }

    public void showForegroundBirthdayNotification(Context context, String title, String contentText){

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_logo_notification)
                        .setContentTitle(title)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(contentText))
                        .setContentText(contentText);

        Intent notificationIntent = new Intent(this, BeautifulMainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());


    }


}
