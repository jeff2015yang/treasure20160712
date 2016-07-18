package com.app.treasure.treasure.user.account;

/**
 * Created by ruifeng on 2016/7/18.
 */
public class UpdateResult {
    //    {
//        "errcode":1,             //状态值
//            "errmsg":"修改成功!"     //返回信息
//    }
    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }
}
