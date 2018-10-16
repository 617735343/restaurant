package com.imooc.imooc_restaurant.biz;

import com.imooc.imooc_restaurant.bean.User;
import com.imooc.imooc_restaurant.config.Config;
import com.imooc.imooc_restaurant.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * 用户相关的业务逻辑类
 */
public class UserBiz {

    //登录业务的方法
    public void login(String username, String password, CommonCallback<User> commonCallback){
        OkHttpUtils.post().url(Config.baseUrl+"user_login")
                .tag(this)//开锁这个网页的请求
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallback);
    }

    //注册业务的方法
    public void register(String username, String password, CommonCallback<User> commonCallback){
        OkHttpUtils.post().url(Config.baseUrl+"user_register")
                .tag(this)//开锁这个网页的请求
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallback);
    }

    public void onDestory(){
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
