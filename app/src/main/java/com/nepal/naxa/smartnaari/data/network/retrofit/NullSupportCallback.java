package com.nepal.naxa.smartnaari.data.network.retrofit;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 10/11/17
 * by nishon.tan@gmail.com
 *
 * Catches bad null resposnes from server
 * and throws them to retrofit onFailure
 */

public class NullSupportCallback<T> implements Callback<T> {

    private static final String TAG = "NullSupportCallback";
    private Callback<T> callback;

    public NullSupportCallback(Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.body() == null) {
            callback.onFailure(call, new NullPointerException("Empty response"));

        }else {
            callback.onResponse(call, response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e(TAG, t.toString());
        callback.onFailure(call, t);
    }
}