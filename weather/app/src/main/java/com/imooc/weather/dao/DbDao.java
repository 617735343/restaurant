package com.imooc.weather.dao;

import android.content.Context;

import com.imooc.weather.bean.Forecast;
import com.imooc.weather.bean.Order;
import com.imooc.weather.bean.Yesterday;
import com.imooc.weather.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * 数据库操作类
 */
public class DbDao {

    Context context;
    public DbDao(Context context){
        this.context=context;
}

    public DatabaseHelper getHelper(){
        return DatabaseHelper.getInstance(context);
    }

    public Dao<Order,Integer> getOrderDao() throws SQLException{
        return getHelper().getDao(Order.class);
    }

    public Dao<Yesterday,Integer> getYesterdayDao() throws SQLException {
        return getHelper().getDao(Yesterday.class);
    }

    public Dao<Forecast,Integer> getForecastDao() throws SQLException {
        return getHelper().getDao(Forecast.class);
    }


    //添加方法
    public void testInsert(Yesterday yesterday, String city, int aqi, Collection<Forecast> forecast, String ganmao, String wendu) throws SQLException {
        Dao<Order,Integer> orderDao=getOrderDao();
        Order order=new Order(yesterday,city,aqi,forecast,ganmao,wendu);
        orderDao.create(order);
        Dao<Yesterday,Integer> yesterdayDao=getYesterdayDao();
        yesterdayDao.create(yesterday);
        Dao<Forecast,Integer> forecastsDao=getForecastDao();
        for (Forecast forecast1:order.getForecast()){
            forecastsDao.create(forecast1);
        }
    }

    //查询方法
    public List testQuery(String city,String date,String wendu) throws SQLException {
        Dao<Order,Integer> orderDao=getOrderDao();
        Dao<Yesterday,Integer> yesterdayDao=getYesterdayDao();
        Dao<Forecast,Integer> forecastsDao=getForecastDao();
        if (city != null) {
            List<Order> orderList = orderDao.queryForEq("city", city);
            return orderList;
        }
        if (date != null) {
            List<Yesterday> yesterdays = yesterdayDao.queryForEq("date", date);
            return yesterdays;
        }
        if (wendu != null){
            List<Order> orders = orderDao.queryForEq("wendu", wendu);
            return orders;
        }
        List<Forecast> forecasts = forecastsDao.queryForAll();

        return forecasts;
    }

    //删除方法
    public void del() throws SQLException {
        Dao<Forecast, Integer> forecastsDao = getForecastDao();
        forecastsDao.queryRaw("delete from tb_forecast");
        Dao<Order,Integer> ordersDao=getOrderDao();
        ordersDao.queryRaw("delete from tb_order");
    }
}
