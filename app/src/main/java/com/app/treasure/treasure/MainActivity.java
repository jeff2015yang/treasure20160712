package com.app.treasure.treasure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.treasure.treasure.commons.ActivityUtils;
import com.app.treasure.treasure.user.login.LoginActivity;
import com.app.treasure.treasure.user.register.RegisterActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
   private ActivityUtils activityUtils;
    @Bind(R.id.btn_Login)Button login;
    @Bind(R.id.btn_Register)Button register;

    public static final String ACTION_ENTER_HOME="action_enter_home";

    private final BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter(ACTION_ENTER_HOME);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,intentFilter);
    }

    @OnClick({R.id.btn_Login,R.id.btn_Register})
    public void click(View v){
        switch (v.getId()){
            case R.id.btn_Login:
                activityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.btn_Register:
                activityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }

}
