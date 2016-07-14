package com.app.treasure.treasure.net;

import com.app.treasure.treasure.user.UseApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ruifeng on 2016/7/13.
 */
public class NetClient {

    public static final String BASE_URL = "http://admin.syfeicuiedu.com";
    private final Gson gson;
    private final Retrofit retrofit;
    private static NetClient netClient;
    private final OkHttpClient okHttpClient;
    private NetClient(){
        gson=new GsonBuilder().setLenient().create();
        okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    public static NetClient getInstance(){
        if(netClient==null){
            netClient=new NetClient();
        }
        return netClient;
    }

    /**
     * 获取用户模型API对象
     */
    private UseApi useApi;
    public UseApi getUseApi(){
        if(useApi==null){
            // retrofit核心代码
            // 将http api转化的java接口进行代码构建(根据注解等)
           useApi=retrofit.create(UseApi.class);
        }
        return  useApi;
    }
    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }
}
