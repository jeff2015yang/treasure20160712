package com.app.treasure.treasure.user.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ruifeng on 2016/7/14.
 */
public class RegisterResult {
    @SerializedName("errcode")
    private int code;

    @SerializedName("errmsg")
    private String message;

    @SerializedName("tokenid")
    private String tokenid;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTokenid() {
        return tokenid;
    }


}
