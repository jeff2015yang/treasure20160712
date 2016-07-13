package com.app.treasure.treasure.user.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.treasure.treasure.BaseActivity;
import com.app.treasure.treasure.MainActivity;
import com.app.treasure.treasure.R;
import com.app.treasure.treasure.commons.ActivityUtils;
import com.app.treasure.treasure.commons.RegexUtils;
import com.app.treasure.treasure.home.HomeAcitvity;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ruifeng on 2016/7/12.
 */
public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView {
    private ActivityUtils activityUtils;
    @Bind(R.id.toolbar_login)
    Toolbar toolbar;
    @Bind(R.id.et_Username)
    EditText etUsername;
    @Bind(R.id.et_Userpassword)
    EditText etUserpassword;
    @Bind(R.id.tv_forgetPassword)
    TextView tvForgetPassword;
    @Bind(R.id.btn_Login)
    Button btnLogin;


    private String userName;
    private String passWord;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.login);
        }
        etUsername.addTextChangedListener(mtextWathcer);
        etUserpassword.addTextChangedListener(mtextWathcer);
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    public final TextWatcher mtextWathcer = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            userName = etUsername.getText().toString();
            passWord = etUserpassword.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord));
            btnLogin.setEnabled(canLogin);

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_Login)
    public void click(View v) {
        if (RegexUtils.verifyUsername(userName) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.username_rules);
            return;
        }
        if (RegexUtils.verifyPassword(passWord) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.password_rules);
        }
        //业务处理
    }

    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
        progressDialog = ProgressDialog.show(this, "", "登录中，请稍等...");
    }

    @Override
    public void hideProgree() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
       activityUtils.showToast(msg);
    }

    @Override
    public void navigateToHome() {
    activityUtils.startActivity(HomeAcitvity.class);
        finish();
        //发送本地广播
        Intent intent = new Intent(MainActivity.ACTION_ENTER_HOME);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
