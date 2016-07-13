package com.app.treasure.treasure;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by ruifeng on 2016/7/12.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
