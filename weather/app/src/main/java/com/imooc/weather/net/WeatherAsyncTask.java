package com.imooc.weather.net;

import android.app.job.JobInfo;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.weather.MainActivity;
import com.imooc.weather.bean.Forecast;
import com.imooc.weather.bean.Order;
import com.imooc.weather.bean.Yesterday;
import com.imooc.weather.config.Config;
import com.imooc.weather.dao.DbDao;
import com.imooc.weather.db.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 网络操作类
 */
public abstract class WeatherAsyncTask extends AsyncTask<String,Void,String>{

    private String name;
    Context context;

    public WeatherAsyncTask(Context context, String name){
        this.name=name;
        this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url=new URL(Config.baseUrl + name);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(6000);

            if (conn.getResponseCode()==200){
                InputStream in=conn.getInputStream();
                byte[] b=new byte[1024*512];
                int len=0;
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                while ((len=in.read(b))>-1){
                    baos.write(b,0,len);
                }
                String result=baos.toString();
                Log.e("TAG",result);
                return result;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            int status = new JSONObject(result).getInt("status");
            if (status == 1000) {
                String data = new JSONObject(result).getString("data");
                //gson解析数据
                Gson gson = new Gson();
                Order orders = gson.fromJson(data, Order.class);
                Yesterday yesterday = orders.getYesterday();
                String city = orders.getCity();
                int aqi = orders.getAqi();
                String ganmao = orders.getGanmao();
                Collection<Forecast> forecast = orders.getForecast();
                String wendu = orders.getWendu();
                DbDao dbDao = new DbDao(context);
                //查询数据库
                List<Order> orderList=dbDao.testQuery(city,null,null);
                List<Yesterday> yesterdayList=dbDao.testQuery(null,yesterday.getDate(),null);
                List<Order> wenduList = dbDao.testQuery(null, null, wendu);

                //当查询到的集合大小为0时，添加数据到数据库，否则不添加
                if (orderList.size()==0 || yesterdayList.size()==0 || wenduList.size()==0){
                    dbDao.del();
                    dbDao.testInsert(yesterday, city, aqi, forecast, ganmao, wendu);
                }

                onSuessExecute(status);

            }else {
                Toast.makeText(context,"搜索不到城市",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,"网络异常或数据异常",Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract void onSuessExecute(int status);
}
