package com.nepal.naxa.smartnaari.data.network.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import android.os.ResultReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.data.network.retrofit.NullSupportCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.local.SharedPreferenceUtils.KEY_USER_DATA;

/**
 * Created on 10/11/17
 * by nishon.tan@gmail.com
 */

public class DownloadService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";
    private static final String KEY_MSG = "download_message";
    private static final String KEY_URL = "download_message_url";
    private static final String KEY_CODE = "download_resposne_code";

    private ResultReceiver receiver;
    private OwlWrapper owls;


    public DownloadService() {
        super(DownloadService.class.getName());


    }


    @Override
    protected void onHandleIntent(Intent intent) {

        receiver = intent.getParcelableExtra("receiver");
        broadCastStart();

        getOwls();

    }


    private void broadcastError(String url, String msg, String resposneCode) {
        Bundle message = new Bundle();

        message.putString(KEY_URL, url);
        message.putString(KEY_MSG, msg);
        message.putString(KEY_CODE, resposneCode);

        receiver.send(STATUS_ERROR, Bundle.EMPTY);

    }

    private void broadCastStart() {
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);
    }

    private void broadCastProgress(String url, int currentAPIIndex, int totalNoOfAPI, String resposneCode) {
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);
    }

    public OwlWrapper getOwls() {
        NetworkApiInterface apiService = NetworkApiClient.getNotifictionApiClient().create(NetworkApiInterface.class);
        Call<OwlWrapper> call = apiService.getOwls();

        call.enqueue(new NullSupportCallback<>(new Callback<OwlWrapper>() {
            @Override
            public void onResponse(Call<OwlWrapper> call, Response<OwlWrapper> response) {

                AppDataManager appDataManager = new AppDataManager(getApplicationContext());
                appDataManager.saveOwls(response.body());
                Log.d(TAG, appDataManager.getOwls().size() + " owls present ");

            }

            @Override
            public void onFailure(Call<OwlWrapper> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        }));


        return owls;
    }


    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}