package com.app.treasure.treasure.user.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.treasure.treasure.MainActivity;
import com.app.treasure.treasure.R;
import com.app.treasure.treasure.commons.ActivityUtils;
import com.app.treasure.treasure.commons.RegexUtils;
import com.app.treasure.treasure.home.HomeAcitvity;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ruifeng on 2016/7/12.
 */
public class RegisterActivity extends MvpActivity<RegisterView,RegisterPresenter> implements RegisterView {
    private ActivityUtils activityUtils;
    @Bind(R.id.toolbar_register)
    Toolbar toolbar;
    @Bind(R.id.et_Username)
    EditText etUsername;
    @Bind(R.id.et_Userpassword)
    EditText etUserpassword;
    @Bind(R.id.et_Confirm)
    EditText etConfirm;
    @Bind(R.id.btn_Register)
    Button btnRegister;

    private String userName;
    private String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.register);
        }
        etUsername.addTextChangedListener(mTextWatcher);
        etUserpassword.addTextChangedListener(mTextWatcher);
        etConfirm.addTextChangedListener(mTextWatcher);
    }

    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
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
            String confirm = etConfirm.getText().toString();
            boolean canRegister = (!(TextUtils.isEmpty(userName)) && !(TextUtils.isEmpty(passWord)) && passWord.equals(confirm));
            btnRegister.setEnabled(canRegister);
        }
    };
      @OnClick(R.id.btn_Register)
      public void click(View v){
          if(RegexUtils.verifyUsername(userName)!=RegexUtils.VERIFY_SUCCESS){
              activityUtils.showToast(R.string.username_rules);
              return;
          }
          if(RegexUtils.verifyPassword(passWord)!=RegexUtils.VERIFY_SUCCESS){
          activityUtils.showToast(R.string.password_rules);
          }

          //业务处理
      }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

   private ProgressDialog mProgressDialog;
    @Override
    public void showProgress() {
            activityUtils.hideSoftKeyboard();
            mProgressDialog=ProgressDialog.show(this,"","注册中，请稍后。。。");
    }

    @Override
    public void hideProgress() {
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
    activityUtils.showToast(msg);
    }

    @Override
    public void navigateToHome() {
     activityUtils.startActivity(HomeAcitvity.class);
        finish();
        Intent intent = new Intent(MainActivity.ACTION_ENTER_HOME);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}