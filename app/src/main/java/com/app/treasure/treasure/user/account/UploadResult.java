package com.app.treasure.treasure.user.account;

/**
 * Created by ruifeng on 2016/7/18.
 */
public class UploadResult {
    //    {
//        errcode : '文件系统上传成功！',                  //返回信息
//        urlcount: 1,                                   //返回值
//            imgUrl:
//        '/UpLoad/HeadPic/f683f88dc9d14b648ad5fcba6c6bc840_0.png',
//                smallImgUrl:
//        '/UpLoad/HeadPic/f683f88dc9d14b648ad5fcba6c6bc840_0_1.png'  //头像地址
//    }
    private String errcode;
    private  int urlcount;
    private String smallImgUrl;

    public String getErrcode() {
        return errcode;
    }

    public int getUrlcount() {
        return urlcount;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }
}
