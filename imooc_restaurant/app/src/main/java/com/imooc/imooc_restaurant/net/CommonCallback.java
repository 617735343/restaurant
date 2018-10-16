package com.imooc.imooc_restaurant.net;

import com.google.gson.Gson;
import com.imooc.imooc_restaurant.utils.GsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

public abstract class CommonCallback<T> extends StringCallback{

    Type mType;

    public CommonCallback(){
        Class<? extends CommonCallback> clazz = getClass();
        Type genericSuperclass = clazz.getGenericSuperclass();

        if(genericSuperclass instanceof Class){
            throw new RuntimeException("Miss Type params");
        }

        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        mType = parameterizedType.getActualTypeArguments()[0];//获取到泛型的类型 返回的是一个数组

    }

    @Override
    public void onError(Call call, Exception e, int i) {
        onError(e);
    }

    @Override
    public void onResponse(String s, int i) {
        try {
            JSONObject resp = new JSONObject(s);
            int resultCode = resp.getInt("resultCode");

            if(resultCode == 1){//请求成功
                String data = resp.getString("data");
                onSuccess((T) GsonUtil.getGson().fromJson(data,mType));
            }else {
                onError(new RuntimeException(resp.getString("resultMessage")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onError(e);
        }
    }


    public abstract void onError(Exception e);

    public abstract void onSuccess(T response);
}
