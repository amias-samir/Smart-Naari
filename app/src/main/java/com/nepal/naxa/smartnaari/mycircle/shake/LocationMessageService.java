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
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.UserData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;


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

    private boolean gpsOn = false;
    private boolean networkOn = false;
    private double locationAccuracy = 50;
    private int locationCount = 0;


    private WindowManager windowManager;
    private TextView localTextView;
    private LayoutInflater inflater;
    private ViewGroup mView;


    private String[] loadingMessages = new String[]{"Talking to GPS satellites", "Tracking Your Current location", "Message will be sent in five minute or less"};
    private ArrayList<String> names;
    private ArrayList<String> contactNumber;


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
        contactNumber = new ArrayList<>();
        names = new ArrayList<>();

        tryDisableKeyguard();
        showView();

        reportProvidersStatus();
        requestLocationUpdates();

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
            sendSMSWithoutGPS();
        }
    }

    private void sendSMSWithoutGPS() {
        localTextView.setText("GPS is turned off, sending SMS anyway");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareToSMS();
            }
        }, TimeUnit.SECONDS.toMillis(5));

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
        this.mView.bringToFront();

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

                    stopLocationCountDown();
                    prepareToSMS();

                } else {

                    String msg = "Location has been calculated. The accuracy is " + location.getAccuracy();
                    localTextView.setText(msg);

                }
            }

            private void showRandomProgressMsg(long millisUntilFinished) {
                Random random = new Random();

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
//            Crashlytics.logException(e);
        }
    }

    private void stopSMSCountDown() {

        try {
            LocationMessageService.this.SMSCountDownTimer.cancel();

        } catch (Exception localException) {
            localException.printStackTrace();
//            Crashlytics.logException(localException);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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
        try {

            loadContacts();
            Timber.i("Sending sms to %s diffrent numbers ", contactNumber.size());
            startSMSCountdown(names.get(0), contactNumber.get(0));

        } catch (Exception e) {
            e.printStackTrace();
            reportAndClose();
//            Crashlytics.logException(e);
        }


    }


    private void reportAndClose() {
        localTextView.setText("Failed to send SMS");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onDestroy();
            }
        }, TimeUnit.SECONDS.toMillis(4));

    }

    private void startSMSCountdown(@NonNull final String name, @NonNull final String number) {
        SMSCountDownTimer = new CountDownTimer(TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS.toMillis(1)) {
            @Override
            public void onTick(long millisUntilFinished) {

                String msg = "Sending SMS to " + name + " in " + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                localTextView.setText(msg);


                if (millisUntilFinished < TimeUnit.SECONDS.toMillis(2)) {
                    msg = "Sending SMS to " + name;
                    localTextView.setText(msg);
                }

            }

            @Override
            public void onFinish() {
                String sms = generateMessage(location);
                sendSMS(sms, number);

            }
        }.start();
    }

    private void sendSMS(String message, String number) {

        Timber.i("SMS sent to %s", number);
//        SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        removeContact();
        if (contactNumber.size() <= 0) {
            stopSMSCountDown();
            LocationMessageService.this.stopSelf();
        } else {

            startSMSCountdown(names.get(0), contactNumber.get(0));
        }
    }

    private void loadContacts() throws Exception {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        UserData userData = sessionManager.getUser();
        addNameAndNumber(userData.getCircleName1(), userData.getCircleMobileNumber1());
        addNameAndNumber(userData.getCircleName2(), userData.getCircleMobileNumber2());
        addNameAndNumber(userData.getCircleName3(), userData.getCircleMobileNumber3());
        addNameAndNumber(userData.getCircleName4(), userData.getCircleMobileNumber4());
        addNameAndNumber(userData.getCircleName5(), userData.getCircleMobileNumber5());
    }

    private void addNameAndNumber(String name, String number) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
            return;
        }

        names.add(name);
        contactNumber.add(number);

    }

    private void removeContact() {
        if(contactNumber.size()>0) {
            names.remove(0);
            contactNumber.remove(0);
        }
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
