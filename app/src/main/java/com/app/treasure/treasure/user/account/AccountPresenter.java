package com.app.treasure.treasure.user.account;

import com.app.treasure.treasure.net.NetClient;
import com.app.treasure.treasure.user.UseApi;
import com.app.treasure.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 个人信息页面业务处理
 * 头像更新业务：头像上传业务 ——--》头像更新业务
 * Created by ruifeng on 2016/7/15.
 */
public class AccountPresenter extends MvpNullObjectBasePresenter<AccountView> {
    private Call<UploadResult> uploadCall;
    private Call<UpdateResult> updateCall;

    public void uploadPhoto(File file){
        getView().showProgress();
        UseApi useApi = NetClient.getInstance().getUseApi();
        RequestBody requestBody = RequestBody.create(null, file);
        MultipartBody.Part part=MultipartBody.Part.createFormData("headpic","headpic.png",requestBody);
        if(uploadCall!=null){
            uploadCall.cancel();
        }
        uploadCall=useApi.upload(part);
        uploadCall.enqueue(uploadResultCallback);
    }
    /**
     *上传头像 callback
     *
     */
    private Callback<UploadResult> uploadResultCallback=new Callback<UploadResult>() {
        @Override
        public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
            getView().hideProgress();
        if(response!=null&& response.isSuccessful()){
            UploadResult uploadResult = response.body();
            if(uploadResult==null){
                getView().showMessage("unknow error!");
                return;
            }
            getView().showMessage(uploadResult.getErrcode());
            if(uploadResult.getUrlcount()!=1){
                return;
            }
            String imgUrl = uploadResult.getSmallImgUrl();
            UserPrefs.getInstance().setPhoto(NetClient.BASE_URL+imgUrl);
            getView().updatePhoto(NetClient.BASE_URL+imgUrl);
            //获取用户头像文件名 和tokenid
            String photoName=imgUrl.substring(imgUrl.lastIndexOf("/")+1,imgUrl.length());
            String tokenId=UserPrefs.getInstance().getTokenId();

            UseApi useApi = NetClient.getInstance().getUseApi();
              if(updateCall!=null){
                  updateCall.cancel();
              }
            updateCall=useApi.update(new Update(tokenId,photoName));
            updateCall.enqueue(updateResultCallback);
        }
        }
        @Override
        public void onFailure(Call<UploadResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
        }
    };

        /**
         * 更新头像
         * */

        private Callback<UpdateResult> updateResultCallback=new Callback<UpdateResult>() {
            @Override
            public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {
             if(response!=null&&response.isSuccessful()){
                 UpdateResult updateResult = response.body();
                 if(updateResult==null){
                     getView().showMessage("unknow error!");
                 }
                 getView().showMessage(updateResult.getErrmsg());
                 if(updateResult.getErrcode()!=1){
                     return;
                 }
             }
            }

            @Override
            public void onFailure(Call<UpdateResult> call, Throwable t) {
               getView().hideProgress();
                getView().showMessage(t.getMessage());
            }
        };


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if(uploadCall!=null)uploadCall.cancel();
        if(updateCall!=null)updateCall.cancel();
    }
}
