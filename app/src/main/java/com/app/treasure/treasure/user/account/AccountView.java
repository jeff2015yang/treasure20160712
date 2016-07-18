package com.app.treasure.treasure.user.account;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by ruifeng on 2016/7/15.
 */
public interface AccountView extends MvpView {
    public void showProgress();

    public void hideProgress();

    public void showMessage(String msg);

    public void updatePhoto(String url);
}
