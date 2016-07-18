package com.app.treasure.treasure.user.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.app.treasure.treasure.BaseActivity;
import com.app.treasure.treasure.R;
import com.app.treasure.treasure.commons.ActivityUtils;
import com.app.treasure.treasure.components.IconSelectWindow;
import com.app.treasure.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ruifeng on 2016/7/15.
 */
public class AccountActivity extends MvpActivity<AccountView, AccountPresenter> implements AccountView {
    private ActivityUtils activityUtils;
    private IconSelectWindow iconSelectWindow;

    @Bind(R.id.toobar_account)
    Toolbar toolbar;
   @Bind(R.id.iv_userIcon_account)
    CircularImageView ivUserIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_account);
        String imageurl= UserPrefs.getInstance().getPhoto();
        if(imageurl!=null){
            ImageLoader.getInstance().displayImage(imageurl,ivUserIcon);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @OnClick(R.id.iv_userIcon_account)
    public void click(View v) {
        if (iconSelectWindow == null) {
            iconSelectWindow = new IconSelectWindow(this, listener);
        }
        if (iconSelectWindow.isShowing()) {
            iconSelectWindow.dismiss();
            return;
        }
        iconSelectWindow.show();
    }

    private final IconSelectWindow.Listener listener = new IconSelectWindow.Listener() {
        @Override
        public void toGallery() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent, CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
    }

    /**
     * Cropper handler
     */
    private final CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            File file=new File(uri.getPath());
              getPresenter().uploadPhoto(file);
            //执行业务
        }

        @Override
        public void onCropCancel() {
            activityUtils.showToast("onCropCancel");
        }

        @Override
        public void onCropFailed(String message) {
            activityUtils.showToast(message);
        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams=new CropParams();
            cropParams.aspectX=300;
            cropParams.aspectY=300;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return AccountActivity.this;
        }
    };

    /**
     * MVp 获取accountPresenter 对象 通过 getPresenter（）获取
     */
    @NonNull
    @Override
    public AccountPresenter createPresenter() {
        return new AccountPresenter();
    }


    /**
     * AccountView 实现
     **/
    private ProgressDialog progressDialog;

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this, "更新用户头像", "正在更新，请稍等。。。");
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void updatePhoto(String url) {
        ImageLoader.getInstance().displayImage(url,ivUserIcon);
    }


    @Override
    protected void onDestroy() {
        if(cropHandler.getCropParams()!=null){
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
        }
        super.onDestroy();
        ButterKnife.unbind(this);

    }
}
