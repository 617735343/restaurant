package com.imooc.imooc_restaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.imooc.imooc_restaurant.R;

public class SplashActivity extends AppCompatActivity {

    private Button mBtnSkip;
    private Handler mHandler = new Handler();

    private Runnable mRunnableToLogin = new Runnable() {
        @Override
        public void run() {
            toLoginActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initEvent();

        //3秒后打开主页
        mHandler.postDelayed(mRunnableToLogin,3000);
    }

    private void initEvent() {
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //防止handler3秒后再次打开主页
                mHandler.removeCallbacks(mRunnableToLogin);
                toLoginActivity();
            }
        });
    }

    private void initView() {
        mBtnSkip = (Button)findViewById(R.id.id_btn_skip);
    }

    private void toLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //避免造成内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnableToLogin);
    }
}
