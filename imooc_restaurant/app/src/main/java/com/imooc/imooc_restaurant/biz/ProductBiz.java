package com.imooc.imooc_restaurant.biz;

import com.imooc.imooc_restaurant.bean.Product;
import com.imooc.imooc_restaurant.config.Config;
import com.imooc.imooc_restaurant.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

public class ProductBiz {

    public void listByPage(int currentPage, CommonCallback<List<Product>> commonCallback){
        OkHttpUtils.post()
                .url(Config.baseUrl+"product_find")
                .addParams("currentPage",currentPage+"")
                .tag(this)
                .build()
                .execute(commonCallback);
    }

    public void onDestroy(){
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
