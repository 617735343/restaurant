package com.imooc.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imooc.weather.bean.Order;
import com.imooc.weather.config.Config;
import com.imooc.weather.dao.DbDao;
import com.imooc.weather.net.WeatherAsyncTask;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mEtFind;
    private Button mBtnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    //添加视图
    private void initView() {
        mEtFind=(EditText)findViewById(R.id.id_et_find);
        mBtnFind=(Button)findViewById(R.id.id_btn_find);

        mBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取用户输入的信息
                String s = mEtFind.getText().toString();
                if (s != null && !s.equals("")) {
                    //启动网络请求
                    WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(MainActivity.this, s) {
                        @Override
                        public void onSuessExecute(int status) {
                            mBtnFind.setEnabled(false);
                            if (status==1000){
                                String s = mEtFind.getText().toString();
                                WeatherActivity.launch(MainActivity.this, s);
                                mBtnFind.setEnabled(true);
                            }
                        }
                    };
                    weatherAsyncTask.execute(Config.baseUrl+s);
//                    DbDao dbDao=new DbDao(MainActivity.this);
//                    try {
//                        List<Order> orderList=dbDao.testQuery(s,null,null);
//                        if (orderList.size()!=0) {
//                            //转跳到天气页面
//                            WeatherActivity.launch(MainActivity.this, s);
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
                }else {
                    Toast.makeText(MainActivity.this,"还未输入要搜索的城市",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
