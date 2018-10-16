package com.imooc.weather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imooc.weather.bean.Forecast;
import com.imooc.weather.bean.Order;
import com.imooc.weather.config.Config;
import com.imooc.weather.dao.DbDao;
import com.imooc.weather.net.WeatherAsyncTask;

import java.sql.SQLException;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    public static final String KEY_NAME = "key_name";

    private TextView mTvCity,mTvGanmao,mTvWendu,mTvHigh,mTvLow,mTvHigh1,mTvLow1,mTvHigh2,mTvLow2,mTvHigh3,mTvLow3,mTvHigh4,mTvLow4;
    private TextView mTvDate,mTvDate1,mTvDate2,mTvDate3,mTvDate4;
    private ImageView mIvage,mIvage1,mIvage2,mIvage3,mIvage4;
    private RelativeLayout bgn;
    private String name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();


    }

    //添加视图
    private void initView() {
        mTvCity = (TextView) findViewById(R.id.id_tv_city);
        mTvGanmao = (TextView) findViewById(R.id.id_tv_ganmao);
        mTvWendu = (TextView) findViewById(R.id.id_tv_wendu);
        mTvHigh = (TextView) findViewById(R.id.id_tv_high);
        mTvLow = (TextView) findViewById(R.id.id_tv_low);
        mTvHigh1 = (TextView) findViewById(R.id.id_tv_high1);
        mTvLow1 = (TextView) findViewById(R.id.id_tv_low1);
        mTvHigh2 = (TextView) findViewById(R.id.id_tv_high2);
        mTvLow2 = (TextView) findViewById(R.id.id_tv_low2);
        mTvHigh3 = (TextView) findViewById(R.id.id_tv_high3);
        mTvLow3 = (TextView) findViewById(R.id.id_tv_low3);
        mTvHigh4 = (TextView) findViewById(R.id.id_tv_high4);
        mTvLow4 = (TextView) findViewById(R.id.id_tv_low4);

        mIvage = (ImageView) findViewById(R.id.id_iv_image);
        mIvage1 = (ImageView) findViewById(R.id.id_iv_image1);
        mIvage2 = (ImageView) findViewById(R.id.id_iv_image2);
        mIvage3 = (ImageView) findViewById(R.id.id_iv_image3);
        mIvage4 = (ImageView) findViewById(R.id.id_iv_image4);

        mTvDate = (TextView) findViewById(R.id.id_tv_date);
        mTvDate1 = (TextView) findViewById(R.id.id_tv_date1);
        mTvDate2 = (TextView) findViewById(R.id.id_tv_date2);
        mTvDate3 = (TextView) findViewById(R.id.id_tv_date3);
        mTvDate4 = (TextView) findViewById(R.id.id_tv_date4);

        bgn = (RelativeLayout) findViewById(R.id.bgn);


        //获取传来的数据
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra(KEY_NAME);
        }

        //启动网络请求
        WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(WeatherActivity.this, name) {

            @Override
            public void onSuessExecute(int status) {
                DbDao dbDao=new DbDao(WeatherActivity.this);
                try {
                    List<Order> orderList=dbDao.testQuery(name,null,null);
                    for (Order order : orderList){
                        mTvCity.setText(order.getCity());
                        mTvGanmao.setText(order.getGanmao());
                        mTvWendu.setText(order.getWendu()+"℃");
                    }
                    List<Forecast> forecastList = dbDao.testQuery(null, null,null);
                    for (int i=0;i<forecastList.size();i++){
                        //天数视图
                        mTvDate.setText(forecastList.get(0).getDate());
                        mTvDate1.setText(forecastList.get(1).getDate());
                        mTvDate2.setText(forecastList.get(2).getDate());
                        mTvDate3.setText(forecastList.get(3).getDate());
                        mTvDate4.setText(forecastList.get(4).getDate());

                        //高低温视图
                        mTvHigh.setText(forecastList.get(0).getHigh());
                        mTvHigh1.setText(forecastList.get(1).getHigh());
                        mTvHigh2.setText(forecastList.get(2).getHigh());
                        mTvHigh3.setText(forecastList.get(3).getHigh());
                        mTvHigh4.setText(forecastList.get(4).getHigh());
                        mTvLow.setText(forecastList.get(0).getLow());
                        mTvLow1.setText(forecastList.get(1).getLow());
                        mTvLow2.setText(forecastList.get(2).getLow());
                        mTvLow3.setText(forecastList.get(3).getLow());
                        mTvLow4.setText(forecastList.get(4).getLow());

                        //图片视图
                        if (forecastList.get(0).getType().equals("阵雨")){
                            mIvage.setImageResource(R.mipmap.rain);
                            bgn.setBackgroundResource(R.mipmap.rain_background);
                        }else if (forecastList.get(0).getType().equals("多云")){
                            mIvage.setImageResource(R.mipmap.cloudy);
                            bgn.setBackgroundResource(R.mipmap.rain_background);
                        }else if (forecastList.get(0).getType().equals("晴")){
                            mIvage.setImageResource(R.mipmap.sunny);
                            bgn.setBackgroundResource(R.mipmap.weather);
                        }else if (forecastList.get(0).getType().equals("阴")){
                            mIvage.setImageResource(R.mipmap.overcast_sky);
                            bgn.setBackgroundResource(R.mipmap.rain_background);
                        }else if (forecastList.get(0).getType().equals("雷阵雨")){
                            mIvage.setImageResource(R.mipmap.b);
                            bgn.setBackgroundResource(R.mipmap.rain_background);
                        }else if (forecastList.get(0).getType().equals("中雨")){
                            mIvage.setImageResource(R.mipmap.b);
                            bgn.setBackgroundResource(R.mipmap.rain_background);
                        }
                        if (forecastList.get(1).getType().equals("阵雨")){
                            mIvage1.setImageResource(R.mipmap.rain);
                        }else if (forecastList.get(1).getType().equals("多云")){
                            mIvage1.setImageResource(R.mipmap.cloudy);
                        }else if (forecastList.get(1).getType().equals("晴")){
                            mIvage1.setImageResource(R.mipmap.sunny);
                        }else if (forecastList.get(1).getType().equals("阴")){
                            mIvage1.setImageResource(R.mipmap.overcast_sky);
                        }else if (forecastList.get(1).getType().equals("雷阵雨")){
                            mIvage1.setImageResource(R.mipmap.b);
                        }else if (forecastList.get(1).getType().equals("中雨")){
                            mIvage1.setImageResource(R.mipmap.b);
                        }
                        if (forecastList.get(2).getType().equals("阵雨")){
                            mIvage2.setImageResource(R.mipmap.rain);
                        }else if (forecastList.get(2).getType().equals("多云")){
                            mIvage2.setImageResource(R.mipmap.cloudy);
                        }else if (forecastList.get(2).getType().equals("晴")){
                            mIvage2.setImageResource(R.mipmap.sunny);
                        }else if (forecastList.get(2).getType().equals("阴")){
                            mIvage2.setImageResource(R.mipmap.overcast_sky);
                        }else if (forecastList.get(2).getType().equals("雷阵雨")){
                            mIvage2.setImageResource(R.mipmap.b);
                        }else if (forecastList.get(2).getType().equals("中雨")){
                            mIvage2.setImageResource(R.mipmap.b);
                        }
                        if (forecastList.get(3).getType().equals("阵雨")){
                            mIvage3.setImageResource(R.mipmap.rain);
                        }else if (forecastList.get(3).getType().equals("多云")){
                            mIvage3.setImageResource(R.mipmap.cloudy);
                        }else if (forecastList.get(3).getType().equals("晴")){
                            mIvage3.setImageResource(R.mipmap.sunny);
                        }else if (forecastList.get(3).getType().equals("阴")){
                            mIvage3.setImageResource(R.mipmap.overcast_sky);
                        }else if (forecastList.get(3).getType().equals("雷阵雨")){
                            mIvage3.setImageResource(R.mipmap.b);
                        }else if (forecastList.get(3).getType().equals("中雨")){
                            mIvage3.setImageResource(R.mipmap.b);
                        }
                        if (forecastList.get(4).getType().equals("阵雨")){
                            mIvage4.setImageResource(R.mipmap.rain);
                        }else if (forecastList.get(4).getType().equals("多云")){
                            mIvage4.setImageResource(R.mipmap.cloudy);
                        }else if (forecastList.get(4).getType().equals("晴")){
                            mIvage4.setImageResource(R.mipmap.sunny);
                        }else if (forecastList.get(4).getType().equals("阴")){
                            mIvage4.setImageResource(R.mipmap.overcast_sky);
                        }else if (forecastList.get(4).getType().equals("雷阵雨")){
                            mIvage4.setImageResource(R.mipmap.b);
                        }else if (forecastList.get(4).getType().equals("中雨")){
                            mIvage4.setImageResource(R.mipmap.b);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        weatherAsyncTask.execute(Config.baseUrl + name);

    }

    //用于转跳界面及传数据
    public static void launch(Context context,String name){
        Intent intent=new Intent(context,WeatherActivity.class);
        intent.putExtra(KEY_NAME,name);
        context.startActivity(intent);
    }
}
