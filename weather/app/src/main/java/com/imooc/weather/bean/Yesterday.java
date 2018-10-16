package com.imooc.weather.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_yesterday")
public class Yesterday {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String date;
    @DatabaseField
    private String high;
    @DatabaseField
    private String low;
    @DatabaseField
    private String fl;
    @DatabaseField
    private String type;

    public Yesterday(String date, String high, String low, String fl, String type) {
        this.date = date;
        this.high = high;
        this.low = low;
        this.fl = fl;
        this.type = type;
    }

    public Yesterday() {
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

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
