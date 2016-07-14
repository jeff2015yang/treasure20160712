package com.app.treasure.treasure.user.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.app.treasure.treasure.net.NetClient;
import com.app.treasure.treasure.user.User;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ruifeng on 2016/7/13.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>  {
    private User user;
    private Call call;
    private Gson gson;
    private Activity activity;
    public static final String FILE_NAME="config";
    public static final String KYE_TOKENID="tokenid";
    public static final String KYE_HEADURL="headurl";
    /**
     * 核心业务
     * **/
    public void login(User user,Activity activity){
        this.activity=activity;
       this.user=user;
        gson=new Gson();
        new LoginTask().execute();
    }



    private final class LoginTask extends AsyncTask<Void,Void,LoginResult>{
      @Override
      protected void onPreExecute() {
          super.onPreExecute();
          getView().showProgress();
      }

      @Override
      protected LoginResult doInBackground(Void... params) {
          OkHttpClient okHttpClient = NetClient.getInstance().getOkHttpClient();
          String content = gson.toJson(user);
          MediaType type = MediaType.parse("treasure/json");
          RequestBody requestBody = RequestBody.create(type, content);
          Request request=new Request.Builder()
                  .url("http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=login")
                  .post(requestBody)
                  .build();
            if(call!=null){
                call.cancel();
            }
          call=okHttpClient.newCall(request);
          try {
              Response response = call.execute();
              if(response==null){
                  return null;
              }
              if(response.isSuccessful()){
                  String string = response.body().string();

                  LoginResult loginResult = gson.fromJson(string, LoginResult.class);
                  return loginResult;
              }
          } catch (IOException e) {
              return null;
          }
          return null;
      }

      @Override
      protected void onPostExecute(LoginResult loginResult) {
          super.onPostExecute(loginResult);
          getView().hideProgree();
          if(loginResult==null){
              getView().showMessage("未知错误！！");
          }
          int code = loginResult.getCode();
          if(code==1){

              SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
              SharedPreferences.Editor edit = sharedPreferences.edit();
              edit.putString(KYE_TOKENID,loginResult.getTokenId());
              edit.putString(KYE_HEADURL,loginResult.getIconUrl());
              edit.commit();
              getView().showMessage(loginResult.getMes());
              getView().navigateToHome();
          }else{
              getView().showMessage(loginResult.getMes());
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
