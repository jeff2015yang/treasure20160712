package com.app.treasure.treasure.user;

import com.app.treasure.treasure.user.account.Update;
import com.app.treasure.treasure.user.account.UpdateResult;
import com.app.treasure.treasure.user.account.UploadResult;
import com.app.treasure.treasure.user.login.LoginResult;
import com.app.treasure.treasure.user.register.RegisterResult;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 将用户模块API，转为Java接口
 * Created by ruifeng on 2016/7/14.
 */
public interface UseApi {

    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterResult> register(@Body User user);

    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginResult> login(@Body User user);

    //(多部分上传)
     @Multipart
    @POST("/Handler/UserLoadPicHandler1.ashx")
    Call<UploadResult> upload(@Part MultipartBody.Part part);

    //更新
    @POST("/Handler/UserHandler.ashx?action=update")
    Call<UpdateResult> update(@Body Update update);
}
