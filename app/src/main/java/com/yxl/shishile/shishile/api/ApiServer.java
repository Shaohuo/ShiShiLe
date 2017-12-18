package com.yxl.shishile.shishile.api;

import com.yxl.shishile.shishile.model.CountDownModel;
import com.yxl.shishile.shishile.model.LotteryList;
import com.yxl.shishile.shishile.model.LotteryListModel;
import com.yxl.shishile.shishile.model.LotteryModel;

import retrofit2.Call;
import retrofit2.http.GET;
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

    @GET("http://192.168.1.127/")
    Call<CountDownModel> getLotteryCountDown(@Query("a") String a, @Query("type") String type);

    @GET("/lottery/all")
    Call<LotteryListModel> getLotteryListModel();

}
