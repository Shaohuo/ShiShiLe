package com.yxl.shishile.shishile.api;

import com.yxl.shishile.shishile.bean.historyBean;
import com.yxl.shishile.shishile.model.Lottery;
import com.yxl.shishile.shishile.model.LotteryList;

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
}
