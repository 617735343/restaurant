package com.imooc.imooc_restaurant.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.imooc.imooc_restaurant.R;

public class BaseActivity extends AppCompatActivity{

    //设置Dialog
    private ProgressDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //android5.0以上就显示黑色 5.0以下就显示默认的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(0xff000000);//设置状态栏颜色
        }

        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setMessage("加载中...");
    }

    protected void stopLoadingProgress() {
        if(mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }

    protected void startLoadingProgress() {
        mLoadingDialog.show();
    }

    protected void setUpToolbar() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLoadingProgress();
        mLoadingDialog = null;
    }

    //当用户未登录时执行该方法
    protected void toLonginActivity() {
        Intent intent=new Intent(this,LoginActivity.class);
        //如果执行startActivity方法，如果栈中已经存在LoginActivity,我们将清除LoginActivity栈顶的所有的一下东西
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
