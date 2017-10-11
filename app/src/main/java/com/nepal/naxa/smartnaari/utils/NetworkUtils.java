package com.nepal.naxa.smartnaari.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created on 10/9/17
 * by nishon.tan@gmail.com
 */

public final class NetworkUtils {

    private NetworkUtils() {

    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isNetworkDisconnected(Context context) {
        return !isNetworkConnected(context);
    }
}
