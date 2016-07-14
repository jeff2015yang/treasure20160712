
package com.app.treasure.treasure.user.register;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.app.treasure.treasure.R;
import com.app.treasure.treasure.net.NetClient;
import com.app.treasure.treasure.user.User;
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

/**
 * Created by ruifeng on 2016/7/13.
 */
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView>  {
    private User user;
    private Call call;
    private Gson gson;
    private Activity activity;
    public static final String FILE_NAME = "config";
    public static final String KYE_TOKENID = "tokenid";

    public void register(User user, Activity activity) {
        this.user = user;
        this.activity = activity;
        Log.e("sssss1111111111",user.toString());
        gson = new Gson();
        new RegisterTask().execute();
    }



    private final class RegisterTask extends AsyncTask<Void, Void, RegisterResult> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getView().showProgress();
        }

        @Override
        protected RegisterResult doInBackground(Void... params) {
            RegisterResult result=null;

            OkHttpClient okHttpClient = NetClient.getInstance().getOkHttpClient();
            Log.e("sssss",user.toString());
            String content = gson.toJson(user);
            RequestBody requestBody = RequestBody.create(null, content);
            Log.e("content",content);
            Request request = new Request.Builder()
                    .url("http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=register")
                    .post(requestBody)
                    .build();
            if (call != null) {
                call.cancel();
            }

            call = okHttpClient.newCall(request);
            try {
                Log.e("----------","============");
                Response response = call.execute();
                if(response==null){
                    Log.e("==========","============");
                   return null;
                }
                if(response.isSuccessful()){
                    Log.e("==========","++++++++++++++");
                    String string = response.body().string();
                    Log.e("------------", string);
                     result = gson.fromJson(string, RegisterResult.class);
                    return result;
                }
            } catch (IOException e) {
                Log.e("--------------","--------------");
                return null;

            }
            Log.e("--------------","---------111111");
            return result;
        }

        @Override
        protected void onPostExecute(RegisterResult registerResult) {
            super.onPostExecute(registerResult);

            getView().hideProgress();
            if(registerResult==null){
                getView().showMessage("未知错误！");
                return;
            }
            int code = registerResult.getCode();
            if(code==1){
                getView().showMessage(registerResult.getMessage());
                getView().navigateToHome();
                SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(KYE_TOKENID,registerResult.getTokenid());
                edit.commit();
            }else{
                getView().showMessage(registerResult.getMessage());
            }
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if(call!=null){
            call.cancel();
        }
    }
}
