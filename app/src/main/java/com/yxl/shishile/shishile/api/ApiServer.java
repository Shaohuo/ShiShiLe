package com.yxl.shishile.shishile.api;

import com.yxl.shishile.shishile.model.CountDownModel;
import com.yxl.shishile.shishile.model.LotteryList;
import com.yxl.shishile.shishile.model.LotteryModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public interface ApiServer {
    @GET("index/{id}")
    Call<LotteryList> getLotteryList(@Path("id") int id, @Query("key") String key);

    @GET("http://192.168.1.127/")
    Call<LotteryModel> getLottery(@Query("a") String a, @Query("type") String type);

    @GET("http://192.168.1.127/")
    Call<CountDownModel> getLotteryCountDown(@Query("a") String a, @Query("type") String type);

}
