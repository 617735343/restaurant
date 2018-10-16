package com.imooc.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.imooc.weather.bean.Forecast;
import com.imooc.weather.bean.Order;
import com.imooc.weather.bean.Yesterday;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * 数据库帮助类
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{
    public DatabaseHelper(Context context) {
        super(context, "weather.db", null, 20);
    }

    //单例模式
    private static DatabaseHelper sHelper = null;

    public static synchronized DatabaseHelper getInstance(Context context){
        if (sHelper == null){
            sHelper=new DatabaseHelper(context);
        }
        return sHelper;
    }

    //创建数据库表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Order.class);
            TableUtils.createTable(connectionSource, Yesterday.class);
            TableUtils.createTable(connectionSource, Forecast.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更新数据库执行的方法
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource,Order.class,true);
            TableUtils.dropTable(connectionSource,Yesterday.class,true);
            TableUtils.dropTable(connectionSource,Forecast.class,true);
            onCreate(sqLiteDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
