package com.itkloud.ssremote.dto;

import java.io.Serializable;

/**
 * Created by andressh on 20/12/14.
 */
public class Account implements Serializable {

    private String app;
    private String user;
    private String password;

    public Account(String app, String user, String password) {
        this.app = app;
        this.user = user;
        this.password = password;
    }

    public Account(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
