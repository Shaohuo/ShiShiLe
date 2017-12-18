package com.yxl.shishile.shishile.api;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${Jianpan} on 2017/1/11.
 */

public class ApiManager {

    private static final String TAG = ApiManager.class.getSimpleName();

    private static final int DEFAULT_TIMEOUT = 10000;
    private static final int CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB
    public static String BASE_URL = "http://192.168.1.127/";//= BuildConfig.baseUrl;
    private static ApiManager sApiManager;
    private static Context sContext;

    private Retrofit mRetrofit;
    private OkHttpClient mClient;

    private ApiManager() {
        initClient();
        initRetrofit();
    }

    public static ApiManager getInstance() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        return sApiManager;
    }

    public static void resetBaseUrl(String url) {
        BASE_URL = url;
        sApiManager = null;
    }

    public static ApiManager getInstance(Context context) {
        if (context != null) {
            sContext = context;
        }
        return getInstance();
    }

    public static void setBaseUrl(String url) {
        BASE_URL = url;
    }

    private void initClient() {
        File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), "/net_cache");
        Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        if (mInterceptors != null && !mInterceptors.isEmpty()) {
            for (Interceptor interceptor : mInterceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        mClient = builder.cache(cache)
                .build();
    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return mRetrofit.create(service);
    }

    private static List<Interceptor> mInterceptors = new LinkedList<>();

    public static void insertInterceptor(Interceptor... interceptors) {
        for (Interceptor interceptor : interceptors) {
            mInterceptors.add(interceptor);
        }
    }
}