package com.imooc.weather.bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "tb_order")
public class Order {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Yesterday yesterday;
    @DatabaseField
    private String city;
    @DatabaseField
    private int aqi;

    @ForeignCollectionField
    private Collection<Forecast> forecast;
    @DatabaseField
    private String ganmao;
    @DatabaseField
    private String wendu;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Forecast forecasts;

    public Order(Yesterday yesterday, String city, int aqi,Collection<Forecast> forecast, String ganmao, String wendu) {
        this.yesterday = yesterday;
        this.city = city;
        this.aqi = aqi;
        this.ganmao = ganmao;
        this.wendu = wendu;
        this.forecast=forecast;
    }

    public Order(){

    }


    public Yesterday getYesterday() {
        return yesterday;
    }

    public void setYesterday(Yesterday yesterday) {
        this.yesterday = yesterday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public Collection<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(Collection<Forecast> forecast) {
        this.forecast = forecast;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }
}
