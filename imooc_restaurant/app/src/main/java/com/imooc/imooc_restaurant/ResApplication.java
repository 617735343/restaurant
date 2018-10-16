package com.imooc.imooc_restaurant;

import android.app.Application;

import com.imooc.imooc_restaurant.utils.SPUtils;
import com.imooc.imooc_restaurant.utils.T;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ResApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        T.init(this);
        SPUtils.init(this,"sp_user.pref");

        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
