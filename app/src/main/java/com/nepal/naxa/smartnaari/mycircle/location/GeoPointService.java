package com.nepal.naxa.smartnaari.mycircle.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nepal.naxa.smartnaari.R;

import java.text.DecimalFormat;
import java.util.List;



public class GeoPointService extends Service implements LocationListener {

    private static final String LOCATION_COUNT = "locationCount";
    private LocationManager locationManager;
    private Location location;
    private boolean gpsOn = false;
    private boolean networkOn = false;
    private double locationAccuracy = 50;
    private int locationCount = 0;
    private String TAG = this.getClass().getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        reportProvidersStatus();
        requestLocationUpdates();
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


    private void returnLocation() {
        if (location != null) {

            Log.d(TAG, "returnLocation: " + location.getLatitude() + " " + location.getLongitude() + " "
                    + location.getAltitude() + " " + location.getAccuracy());

        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
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

    private String truncateDouble(float number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number);
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

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }


}
