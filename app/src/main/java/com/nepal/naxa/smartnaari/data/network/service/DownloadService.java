package com.nepal.naxa.smartnaari.data.network.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.local.model.YuwaPustaResponse;
import com.nepal.naxa.smartnaari.data.network.OwlWrapper;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.data.network.retrofit.ErrorSupportCallback;
import com.nepal.naxa.smartnaari.utils.NetworkUtils;
import com.nepal.naxa.smartnaari.utils.ui.ToastUtils;

import java.util.ArrayList;

import me.jessyan.progressmanager.body.ProgressInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    private ProgressInfo mLastDownloadingInfo;
    private Handler mHandler;
    private ToastUtils toastUtils;
    private ArrayList<String> completedUrls;
    private ArrayList<String> failedUrls;


    public DownloadService() {
        super(DownloadService.class.getName());
        completedUrls = new ArrayList<>();
        failedUrls = new ArrayList<>();

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        toastUtils = new ToastUtils();
        if (NetworkUtils.isNetworkDisconnected(getApplicationContext())) {
            toastUtils.error(getApplicationContext(), getString(R.string.network_disconnected));
            return;
        }

        mHandler = new Handler();
        receiver = intent.getParcelableExtra("receiver");
        broadCastStart();

        getOwls();
        getYuwaPustaPosts();

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

    public void getOwls() {
        NetworkApiInterface apiService = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);
        Call<OwlWrapper> call = apiService.getOwls();
        call.enqueue(new ErrorSupportCallback<>(new Callback<OwlWrapper>() {
            @Override
            public void onResponse(Call<OwlWrapper> call, Response<OwlWrapper> response) {


                AppDataManager appDataManager = new AppDataManager(getApplicationContext());
                appDataManager.saveOwls(response.body());

                Log.i(TAG,response.body().getData().size() + " owls are downloaded ");

            }

            @Override
            public void onFailure(Call<OwlWrapper> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }

        }));

    }

    public void getYuwaPustaPosts() {
        NetworkApiInterface apiService = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);
        Call<YuwaPustaResponse> call = apiService.getYuwaPustaPosts();
        call.enqueue(new ErrorSupportCallback<>(new Callback<YuwaPustaResponse>() {
            @Override
            public void onResponse(Call<YuwaPustaResponse> call, Response<YuwaPustaResponse> response) {

                AppDataManager appDataManager = new AppDataManager(getApplicationContext());
                appDataManager.saveYuwaQuestions(response.body().getData());

                Log.i(TAG,response.body().getData().size() + " Yuwa Pusta posts downloaded ");

                int i = appDataManager.getAllYuwaQuestions().size();
                Log.i(TAG,i+" yuwa posts present in database");

            }

            @Override
            public void onFailure(Call<YuwaPustaResponse> call, Throwable t) {

            }
        }));
    }


    private void markAPIAsCompleted(String url) {
        completedUrls.add(url);
    }

    private int getTotalCompletedAPIs() {
        return completedUrls.size();
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