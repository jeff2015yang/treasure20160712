package com.app.treasure.treasure.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by ruifeng on 2016/7/13.
 */
public class NetClient {
    private static NetClient netClient;
    private final OkHttpClient okHttpClient;
    private NetClient(){
        okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    public static NetClient getInstance(){
        if(netClient==null){
            netClient=new NetClient();
        }
        return netClient;
    }

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }
}
