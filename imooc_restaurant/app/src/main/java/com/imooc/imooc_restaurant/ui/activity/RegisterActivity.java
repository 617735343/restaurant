package com.imooc.imooc_restaurant.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.imooc.imooc_restaurant.R;
import com.imooc.imooc_restaurant.UserInfoHolder;
import com.imooc.imooc_restaurant.bean.User;
import com.imooc.imooc_restaurant.biz.UserBiz;
import com.imooc.imooc_restaurant.net.CommonCallback;
import com.imooc.imooc_restaurant.utils.T;

/**
 * 注册页
 */
public class RegisterActivity extends BaseActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtRePassword;
    private Button mBtnRegister;

    private UserBiz mUserBiz = new UserBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setUpToolbar();

        initView();
        initEvent();

        setTitle("注册");
    }



    private void initEvent() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 检查登录成功？
                final String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                String repassword = mEtRePassword.getText().toString();

                //校验
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    T.showToast("账号或者密码不能为空");
                    return;
                }

                if(!password.equals(repassword)){
                    T.showToast("两次输入密码不一致");
                    return;
                }

                startLoadingProgress();

                mUserBiz.register(username, password, new CommonCallback<User>() {

                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        T.showToast("注册成功，用户名为："+response.getUsername());

                        LoginActivity.launch(RegisterActivity.this,response.getUsername(),response.getPassword());
                        finish();
                    }
                });
            }
        });
    }

    private void initView() {
        mEtUsername = (EditText)findViewById(R.id.id_et_username);
        mEtPassword = (EditText)findViewById(R.id.id_et_password);
        mEtRePassword = (EditText)findViewById(R.id.id_et_repassword);

        mBtnRegister = (Button)findViewById(R.id.id_btn_register);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserBiz.onDestory();
    }
}
