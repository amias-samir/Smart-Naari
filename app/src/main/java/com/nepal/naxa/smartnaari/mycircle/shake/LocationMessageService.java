package com.nepal.naxa.smartnaari.mycircle.shake;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.UserData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Created on 10/12/17
 * by nishon.tan@gmail.com
 */

public class LocationMessageService extends Service implements LocationListener {

    private static final int NOTIFICATION_ID = 21;
    private static final String LOCATION_COUNT = "locationCount";

    private CountDownTimer locationCountDownTimer;
    private CountDownTimer SMSCountDownTimer;

    private WindowManager.LayoutParams params;
    private PowerManager.WakeLock wakeLock;
    private LocationManager locationManager;
    private Location location;

    private final long WAKE_LOCK_TIME_OUT = TimeUnit.MINUTES.toMillis(6);
    private final long LOCATION_WAIT_TIMEOUT = TimeUnit.MINUTES.toMillis(5);


    private String TAG = this.getClass().getSimpleName();
    private int locationUpdateCounterCurValue = 60;
    private String name = "Nishon Tandukar";
    private boolean gpsOn = false;
    private boolean networkOn = false;
    private double locationAccuracy = 100;
    private int locationCount = 0;


    private WindowManager windowManager;
    private TextView localTextView;
    private LayoutInflater inflater;
    private ViewGroup mView;

    ArrayList<String> contactNo = new ArrayList<String>();
    ArrayList<String> contactName = new ArrayList<String>();

    private String[] loadingMessages = new String[]{"Talking to GPS satellites", "Tracking Your Current location", "Message will be sent in five minute or less"};
    private boolean alreadySentSMS = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: ");
        startForeground(291, new Notification());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        tryDisableKeyguard();

        reportProvidersStatus();
        requestLocationUpdates();

        showView();
        startCountDown(localTextView);
    }

    private void tryDisableKeyguard() {
        this.wakeLock = ((PowerManager) getApplicationContext().getSystemService(POWER_SERVICE)).newWakeLock(268435482, "TAG");
        this.wakeLock.acquire(WAKE_LOCK_TIME_OUT);
    }

    private void reportProvidersStatus() {
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
                gpsOn = true;
            }
            if (provider.equalsIgnoreCase(LocationManager.NETWORK_PROVIDER)) {
                networkOn = true;
            }
        }

        if (!gpsOn && !networkOn) {
            Log.d(TAG, "onCreate: No network Provider Present");
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        boolean hasPermission = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION);

        if (locationManager != null) {
            if (gpsOn && hasPermission) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
            if (networkOn && hasPermission) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }
    }

    private void showView() {
        this.windowManager = ((WindowManager) getSystemService(WINDOW_SERVICE));
        this.inflater = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
        this.mView = ((ViewGroup) this.inflater.inflate(R.layout.layout_notification_msg, null));
        this.params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2010, 4194600, -3);
        this.params.y = 50;

        localTextView = (TextView) mView.findViewById(R.id.tv_notification_msg);
        Button btnCancleDistress = (Button) mView.findViewById(R.id.btn_cancle_distress);
        btnCancleDistress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationCountDown();
                stopSMSCountDown();
                LocationMessageService.this.stopSelf();
            }
        });
        this.windowManager.addView(this.mView, this.params);
    }

    public void startCountDown(final TextView paramTextView) {
        paramTextView.setText(loadingMessages[0]);

        this.locationCountDownTimer = new CountDownTimer(LOCATION_WAIT_TIMEOUT, 1000L) {
            public void onFinish() {
                prepareToSMS();
            }

            public void onTick(long millisUntilFinished) {

                showRandomProgressMsg(millisUntilFinished);
                if (location == null) return;
                if (location.getAccuracy() <= locationAccuracy) {

                    prepareToSMS();

                } else {

                    String msg = "Location has been calculated. The accuracy is " + location.getAccuracy();
                    localTextView.setText(msg);

                }
            }

            private void showRandomProgressMsg(long millisUntilFinished) {
                Random random = new Random();

                Log.d(TAG, "showRandomProgressMsg: minute" + TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                Log.d(TAG, "showRandomProgressMsg: second" + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));

                if (millisUntilFinished % 5 == 0) {
                    int index = random.nextInt(loadingMessages.length);
                    localTextView.setText(loadingMessages[index]);
                }

            }
        }.start();
    }


    private void stopLocationCountDown() {
        try {
            LocationMessageService.this.locationCountDownTimer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopSMSCountDown() {

        try {
            LocationMessageService.this.SMSCountDownTimer.cancel();

        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.locationUpdateCounterCurValue = 3;
        Log.d(TAG, "onDestroy: ");

        if ((this.wakeLock != null) && (this.wakeLock.isHeld())) {
            this.wakeLock.release();
        }
        if (this.mView != null) {
            this.windowManager.removeView(this.mView);
        }
    }

    public void onLocationChanged(Location location) {
        this.location = location;
        if (this.location != null) {
            // Bug report: cached GeoPoint is being returned as the first value.
            // Wait for the 2nd value to be returned, which is hopefully not cached?
            ++locationCount;

            if (locationCount > 1) {
                String msg = getString(R.string.location_provider_accuracy,
                        this.location.getProvider(), truncateDouble(this.location.getAccuracy()));

                Log.d(TAG, "onLocationChanged: " + msg);

                if (this.location.getAccuracy() <= locationAccuracy) {
                    returnLocation();
                }
            }
        } else {

            Log.d(TAG, "onLocationChanged: (" + locationCount + ") null location");

        }
    }

    private void prepareToSMS() {

        if (alreadySentSMS) {
            return;
        }

        stopLocationCountDown();
        alreadySentSMS = true;

        //todo loop smartSMSCountDown with different numbers

        prepareContactList();

        for (int i = 0 ; i < contactNo.size() ; i++ ) {

            startSMSCountdown(contactNo.get(i), contactName.get(i));

        }

    }

    private void startSMSCountdown(final String mobileNoToSendSMS, final String contactNameToSendSMS) {
        SMSCountDownTimer = new CountDownTimer(TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS.toMillis(1)) {
            @Override
            public void onTick(long millisUntilFinished) {

                String msg = "Sending SMS to " + contactNameToSendSMS + " in " + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                localTextView.setText(msg);

                //todo find a better way to show sms sent message
                if (millisUntilFinished < TimeUnit.SECONDS.toMillis(2)) {
                    msg = "Sending SMS to " + contactNameToSendSMS;
                    localTextView.setText(msg);
                }

            }

            @Override
            public void onFinish() {
                String sms = generateMessage(location);
                sendSMS(sms , mobileNoToSendSMS );

                stopSMSCountDown();
                LocationMessageService.this.stopSelf();

            }
        }.start();
    }

    // TODO: 10/30/2017   SMS send
    private void sendSMS(String message, String mobileNoToSendSMS) {
//        ArrayList contactNo = new ArrayList();

//        prepareContactList();

//        for(int i = 1 ; i<= contactNo.size(); i++) {

            Log.i(TAG, " Sending sms " + message);
//         SmsManager.getDefault().sendTextMessage(mobileNoToSendSMS, null, LocationMessageService.this.msg, null, null);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        }

        stopSMSCountDown();

    }



//    prepare contact
    private void prepareContactList (){
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        UserData userData = sessionManager.getUser();

        String firstContactName = userData.getFirstName();
        String firstContactNo = userData.getCircleMobileNumber1();
        contactName.add(firstContactName);
        contactNo.add(firstContactNo);


        String secondContactName = userData.getFirstName();
        String secondContactNo = userData.getCircleMobileNumber1();
        contactName.add(secondContactName);
        contactNo.add(secondContactNo);

        String thirdContactName = userData.getFirstName();
        String thirdContactNo = userData.getCircleMobileNumber1();
        contactName.add(thirdContactName);
        contactNo.add(thirdContactNo);

        String fourthContactName = userData.getFirstName();
        String fourthContactNo = userData.getCircleMobileNumber1();
        contactName.add(fourthContactName);
        contactNo.add(fourthContactNo);

        String fifthContactName = userData.getFirstName();
        String fifthContactNo = userData.getCircleMobileNumber1();
        contactName.add(fifthContactName);
        contactNo.add(fifthContactNo);
    }

    private String generateMessage(Location location) {
        String msg = "Help! This is an Emergency";
        if (location != null) {

            return msg + " My Location is: " + getMapUrl(location);
        }

        return msg;
    }

    private String getMapUrl(Location location) {

        String lat = truncateDouble(location.getLatitude());
        String lon = truncateDouble(location.getLongitude());

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("maps.google.com")
                .appendPath("maps")
                .appendQueryParameter("q", lat + "," + lon);
        return builder.build().toString();
    }

    private String truncateDouble(double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number);
    }

    private void returnLocation() {
        if (location != null) {

            Log.d(TAG, "returnLocation: " + location.getLatitude() + " " + location.getLongitude() + " "
                    + location.getAltitude() + " " + location.getAccuracy());

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
