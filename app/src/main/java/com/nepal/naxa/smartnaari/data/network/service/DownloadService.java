package com.nepal.naxa.smartnaari.data.network.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

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


    public DownloadService() {
        super(DownloadService.class.getName());


    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");
        receiver = intent.getParcelableExtra("receiver");

        BroadCastStart();

        Log.d(TAG, "Service Stopping!");

    }


    private void BroadcastError(String url, String msg, String resposneCode) {
        Bundle message = new Bundle();

        message.putString(KEY_URL, url);
        message.putString(KEY_MSG, msg);
        message.putString(KEY_CODE, resposneCode);

        receiver.send(STATUS_ERROR, Bundle.EMPTY);

    }

    private void BroadCastStart() {
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);
    }

    private void BroadCastFinish(String url, int currentAPIIndex, int totalNoOfAPI, String resposneCode) {
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);
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