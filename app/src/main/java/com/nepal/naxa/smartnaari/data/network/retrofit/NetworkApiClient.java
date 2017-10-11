package com.nepal.naxa.smartnaari.data.network.retrofit;


import android.util.Log;

import com.github.simonpercic.oklog3.OkLogInterceptor;
import com.nepal.naxa.smartnaari.data.network.UrlClass;


import java.io.IOException;
import java.net.SocketTimeoutException;

import me.jessyan.progressmanager.ProgressManager;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;


import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 6/6/2017.
 */

public class NetworkApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getAPIClient() {

        if (retrofit == null) {
            OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(3);

            okHttpBuilder.dispatcher(dispatcher);
            okHttpBuilder.addInterceptor(okLogInterceptor);

            OkHttpClient okHttpClient = okHttpBuilder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlClass.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }


}
