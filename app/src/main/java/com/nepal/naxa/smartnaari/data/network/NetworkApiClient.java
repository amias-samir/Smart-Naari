package com.nepal.naxa.smartnaari.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 6/6/2017.
 */

public class NetworkApiClient {

//    public static final String BASE_URL = UrlClass.DEVICE_REG_URL;
    public static final String BASE_URL = UrlClass.SMART_NARI_BASE_URL;
    private static Retrofit retrofit = null;


    public static Retrofit getNotifictionApiClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
