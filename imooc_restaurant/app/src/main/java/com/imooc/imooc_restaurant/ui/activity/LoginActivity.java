package com.imooc.imooc_restaurant.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.imooc.imooc_restaurant.R;
import com.imooc.imooc_restaurant.UserInfoHolder;
import com.imooc.imooc_restaurant.bean.User;
import com.imooc.imooc_restaurant.biz.UserBiz;
import com.imooc.imooc_restaurant.net.CommonCallback;
import com.imooc.imooc_restaurant.utils.T;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

import okhttp3.Call;
import okhttp3.CookieJar;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity {

    private UserBiz mUserBiz = new UserBiz();

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mTvResqister;

    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_PASSWORD = "key_password";


    @Override
    protected void onResume() {
        super.onResume();
        CookieJarImpl cookieJar = (CookieJarImpl) OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        cookieJar.getCookieStore().removeAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //android5.0以上就显示黑色 5.0以下就显示默认的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(0xff000000);//设置状态栏颜色
        }

        initView();

        initEvent();

        initIntent(getIntent());
    }

    private void initEvent() {

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 检查登录成功？
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();

                //校验
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    T.showToast("账号或者密码不能为空");
                    return;
                }

                startLoadingProgress();

                mUserBiz.login(username, password, new CommonCallback<User>() {

                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        T.showToast("登录成功");
                        //保存用户的信息
                        UserInfoHolder.getInstance().setUser(response);
                        //转跳到主页
                        toOrderActivity();
                    }
                });
            }
        });

        mTvResqister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //转跳到注册页面
                toRegisterActivity();
            }
        });
    }



    private void toRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        initIntent(intent);
    }

    private void initIntent(Intent intent) {
        if (intent == null){
            return;
        }
        String username = intent.getStringExtra(KEY_USERNAME);
        String password = intent.getStringExtra(KEY_PASSWORD);

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }

        mEtUsername.setText(username);
        mEtPassword.setText(password);
    }

    private void toOrderActivity() {
        Intent intent = new Intent(this,OrderActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        mEtUsername=(EditText)findViewById(R.id.id_et_username);
        mEtPassword=(EditText)findViewById(R.id.id_et_password);

        mBtnLogin=(Button) findViewById(R.id.id_btn_login);
        mTvResqister=(TextView) findViewById(R.id.id_tv_register);

    }

    public static void launch(Context context, String username, String password) {
        Intent intent=new Intent(context,LoginActivity.class);
        //如果执行startActivity方法，如果栈中已经存在LoginActivity,我们将清除LoginActivity栈顶的所有的一下东西
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(KEY_USERNAME,username);
        intent.putExtra(KEY_PASSWORD,password);
        context.startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserBiz.onDestory();
    }
}
