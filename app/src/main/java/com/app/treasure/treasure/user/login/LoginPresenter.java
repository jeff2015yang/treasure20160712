package com.app.treasure.treasure.user.login;



import android.content.Context;

import com.app.treasure.treasure.net.NetClient;
import com.app.treasure.treasure.user.UseApi;
import com.app.treasure.treasure.user.User;
import com.app.treasure.treasure.user.UserPrefs;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Callback;

/**
 * Created by ruifeng on 2016/7/13.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>  {
    private retrofit2.Call<LoginResult> loginCall;
    private Context context;
    /**
     * 核心业务
     * **/
    public void login(User user,Context context){
        this.context=context;
        getView().showProgress();
        UseApi useApi = NetClient.getInstance().getUseApi();
        if(loginCall!=null){
            loginCall.cancel();
        }
        // 执行登陆请求构建出call模型
        loginCall=useApi.login(user);
        // Call模型异步执行
        loginCall.enqueue(callback);
    }
   private Callback<LoginResult> callback=new Callback<LoginResult>() {
       @Override
       public void onResponse(retrofit2.Call<LoginResult> call, retrofit2.Response<LoginResult> response) {
          getView().hideProgree();
           if(response.isSuccessful()){
               // 取出响应体(retrofit已加了gson转换器的,注意接口的定义)
               LoginResult result = response.body();
               if(result==null){
                   getView().showMessage("unknow error!");
                   return;
               }
               getView().showMessage(result.getMes());
               if(result.getCode()==1){

                   UserPrefs.getInstance().setPhoto(NetClient.BASE_URL+result.getIconUrl());
                   UserPrefs.getInstance().setTokenId(result.getTokenId());
                   getView().navigateToHome();
               }
               return;
           }
       }

       @Override
       public void onFailure(retrofit2.Call<LoginResult> call, Throwable t) {
           getView().hideProgree();
           getView().showMessage(t.getMessage());
       }
   };




    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if(loginCall!=null){
            loginCall.cancel();
        }
    }


}
