package com.app.treasure.treasure.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ruifeng on 2016/7/13.
 */
public class User {
    @SerializedName("UserName")
    private String userName;

     @SerializedName("Password")
    private String password;

    public User(String userName, String password) {
        userName = userName;
        password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
