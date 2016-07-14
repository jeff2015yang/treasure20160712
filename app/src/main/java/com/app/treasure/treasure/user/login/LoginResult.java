package com.app.treasure.treasure.user.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ruifeng on 2016/7/13.
 */
public class LoginResult {
    @SerializedName("errcode")
    private int code;
    @SerializedName("headpic")
    private String iconUrl;
    @SerializedName("errmsg")
    private String mes;
    @SerializedName("tokenid")
    private String tokenId;

    public int getCode() {
        return code;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getMes() {
        return mes;
    }

    public String getTokenId() {
        return tokenId;
    }
}
