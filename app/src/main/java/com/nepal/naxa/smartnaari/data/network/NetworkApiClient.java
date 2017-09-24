package com.nepal.naxa.smartnaari.data.network;

import com.github.simonpercic.oklog3.OkLogInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 6/6/2017.
 */

public class NetworkApiClient {

//    public static final String BASE_URL = UrlClass.DEVICE_REG_URL;
    public static final String BASE_URL = UrlClass.BASE_URL;
    private static Retrofit retrofit = null;


    public static Retrofit getNotifictionApiClient() {

        if (retrofit==null) {

            // create an instance of OkLogInterceptor using a builder()
            OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();

// create an instance of OkHttpClient builder
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

// add OkLogInterceptor to OkHttpClient's application interceptors
            okHttpBuilder.addInterceptor(okLogInterceptor);

// build
            OkHttpClient okHttpClient = okHttpBuilder.build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
