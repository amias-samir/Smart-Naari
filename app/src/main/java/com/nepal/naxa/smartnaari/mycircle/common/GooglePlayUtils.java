package com.nepal.naxa.smartnaari.mycircle.common;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by nishon on 10/25/17.
 */

public class GooglePlayUtils {

    public boolean isPlayServicesAvailable(Context context) {

        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().getErrorDialog((Activity) context, resultCode, 2).show();
            return false;
        }

        return true;
    }
}
