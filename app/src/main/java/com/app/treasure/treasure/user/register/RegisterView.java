package com.app.treasure.treasure.user.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by ruifeng on 2016/7/13.
 */
public interface RegisterView extends MvpView{
    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void navigateToHome();
}
