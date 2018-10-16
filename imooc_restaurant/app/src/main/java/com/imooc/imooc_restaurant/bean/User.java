package com.imooc.imooc_restaurant.bean;

import java.io.Serializable;

/**
 * 用户密码信息类
 */
public class User implements Serializable{
    private int id;
    private String password;
    private String username;
    private int icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
