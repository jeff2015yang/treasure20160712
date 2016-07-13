package com.app.treasure.treasure.user.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by ruifeng on 2016/7/13.
 */
public interface LoginView extends MvpView{
    void showProgress();

    void hideProgree();

    void showMessage(String msg);

    void navigateToHome();
}
