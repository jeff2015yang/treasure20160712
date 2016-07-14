package com.app.treasure.treasure.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ruifeng on 2016/7/13.
 */
public class User {

    private String UserName;


    private String Password;

    public User(String userName, String password) {
        UserName = userName;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
