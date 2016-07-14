package com.app.treasure.treasure.user;

import com.app.treasure.treasure.user.login.LoginResult;
import com.app.treasure.treasure.user.register.RegisterResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 将用户模块API，转为Java接口
 * Created by ruifeng on 2016/7/14.
 */
public interface UseApi {

    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterResult> register(@Body User user);

    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginResult> login(@Body User user);
}
