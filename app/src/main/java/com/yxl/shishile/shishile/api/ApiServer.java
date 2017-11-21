package com.yxl.shishile.shishile.api;

import com.yxl.shishile.shishile.openprize.Lottery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public interface ApiServer {
    @GET("index/{id}")
    Call<Lottery> getLottery(@Path("id") int id, @Query("key") String key);
}
