package com.yxl.shishile.shishile.api;

import com.yxl.shishile.shishile.model.CountDownModel;
import com.yxl.shishile.shishile.model.ForecastListModel;
import com.yxl.shishile.shishile.model.ForecastModel;
import com.yxl.shishile.shishile.model.InformationModel;
import com.yxl.shishile.shishile.model.LotteryList;
import com.yxl.shishile.shishile.model.LotteryListModel;
import com.yxl.shishile.shishile.model.LotteryModel;
import com.yxl.shishile.shishile.model.PostEaseUserModel;
import com.yxl.shishile.shishile.model.PostRegisterUserModel;
import com.yxl.shishile.shishile.model.PostUserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public interface ApiServer {
    @GET("/lottery/history/{id}")
    Call<LotteryList> getLotteryHistoryList(@Path("id") int id, @Query("pagesize") String pagesize);

    @GET("http://192.168.1.127/")
    Call<LotteryModel> getLottery(@Query("a") String a, @Query("type") String type);

    @GET("/lottery/countdown/{id}")
    Call<CountDownModel> getLotteryCountDown(@Path("id") int id);

    @GET("lottery/forecast_rand")
    Call<ForecastModel> getForecast();

    @GET("/lottery/all")
    Call<LotteryListModel> getLotteryListModel();

    @GET("/lottery/forecast_all")
    Call<ForecastListModel> getForecastListModel();

    @POST("/user/tourist")
    @FormUrlEncoded
    Call<PostEaseUserModel> getEaseUser(@Field("imei") String imei);

    @POST("/user")
    @FormUrlEncoded
    Call<PostRegisterUserModel> register(@Field("username") String username, @Field("password")
            String password);

    @POST("/user/login")
    @FormUrlEncoded
    Call<PostUserModel> login(@Field("username") String username, @Field("password") String
            password);

    @GET("article?page=1&pagesize=5")
    Call<InformationModel> getInformation();

    @GET("article?page=1&pagesize=15")
    Call<InformationModel> getInfor();

    @PUT("/user/{user_id}")
    Call<PostUserModel> changePassword(@Path("user_id") int user_id, @Query("oldpwd") String
            oldpwd, @Query("newpwd") String newpwd);
}
