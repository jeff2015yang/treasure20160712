
package com.app.treasure.treasure.user.register;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.app.treasure.treasure.R;
import com.app.treasure.treasure.net.NetClient;
import com.app.treasure.treasure.user.UseApi;
import com.app.treasure.treasure.user.User;
import com.app.treasure.treasure.user.UserPrefs;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import retrofit2.Retrofit;

/**
 * Created by ruifeng on 2016/7/13.
 */
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView>  {
    private retrofit2.Call<RegisterResult>  registerResultCall;
          private  Context context;
    /**
     * 核心业务
     * **/
    public void register(User user,Context context) {
        this.context=context;
        getView().showProgress();
        UseApi useApi = NetClient.getInstance().getUseApi();
        if(registerResultCall!=null){
            registerResultCall.cancel();
        }
          registerResultCall=useApi.register(user);
        registerResultCall.enqueue(callback);
    }
    private retrofit2.Callback<RegisterResult> callback=new retrofit2.Callback<RegisterResult>() {
        @Override
        public void onResponse(retrofit2.Call<RegisterResult> call, retrofit2.Response<RegisterResult> response) {
            getView().hideProgress();
            if(response.isSuccessful()){
                RegisterResult result = response.body();
                if(result==null){
                    getView().showMessage("unknow error!");
                }
                getView().showMessage(result.getMessage());
                if(result.getCode()==1){

                    UserPrefs.getInstance().setTokenId(result.getTokenid());
                    getView().navigateToHome();
                }
                return;
            }
        }

        @Override
        public void onFailure(retrofit2.Call<RegisterResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
        }
    };




    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if(registerResultCall!=null){
            registerResultCall.cancel();
        }
    }
}
