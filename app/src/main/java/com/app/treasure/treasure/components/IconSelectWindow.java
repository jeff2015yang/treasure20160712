package com.app.treasure.treasure.components;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.app.treasure.treasure.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户头像更改弹出式窗口(从相册，从相机，取消)
 * Created by ruifeng on 2016/7/15.
 */
public class IconSelectWindow extends PopupWindow {

    private final Listener listener;
    private final Activity activity;


    public interface Listener {
        void toGallery();

        void toCamera();
    }

    public IconSelectWindow(@NonNull Activity activity, @NonNull Listener listener) {
        super(activity.getLayoutInflater().inflate(R.layout.window_select_icon, null), ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, getContentView());
        this.activity=activity;
        this.listener=listener;
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
    }

    public void show(){
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
    @OnClick({R.id.btn_gallery,R.id.btn_camera,R.id.btn_cancel})
    public void click(View v){
        switch (v.getId()){
            case R.id.btn_gallery:
                listener.toGallery();
                break;
            case R.id.btn_camera:
                listener.toCamera();
                break;
            case R.id.btn_cancel:
                break;
        }
        dismiss();
    }
}
