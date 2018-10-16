package com.imooc.weather.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_forecast")
public class Forecast {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String date;
    @DatabaseField
    private String high;
    @DatabaseField
    private String fengli;
    @DatabaseField
    private String low;
    @DatabaseField
    private String fengxiang;
    @DatabaseField
    private String type;
    @DatabaseField(columnName = "order_id",foreign = true,foreignAutoRefresh = true)
    private Order order;

    public Forecast(String date, String high, String fengli, String low, String fengxiang, String type,Order order) {
        this.date = date;
        this.high = high;
        this.fengli = fengli;
        this.low = low;
        this.fengxiang = fengxiang;
        this.type = type;
        this.order=order;
    }

    public Forecast(){

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
