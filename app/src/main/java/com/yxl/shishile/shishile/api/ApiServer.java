package com.yxl.shishile.shishile.api;

import com.yxl.shishile.shishile.openprize.Lottery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public interface ApiServer {
    @GET("index/{id}")
    Call<Lottery> getLottery(@Path("id") int id);
}
