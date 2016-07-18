package com.app.treasure.treasure;

import android.app.Application;

import com.app.treasure.treasure.user.UserPrefs;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by ruifeng on 2016/7/12.
 */
public class TreasureApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UserPrefs.init(this);
        initImageLoader();
        SDKInitializer.initialize(getApplicationContext());
    }

    public void initImageLoader(){
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.user_icon)
                .showImageForEmptyUri(R.drawable.user_icon)
                .showImageOnFail(R.drawable.user_icon)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(5*1024*1024)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
