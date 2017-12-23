package com.yxl.shishile.shishile.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.model.ForecastModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huangqianwen on 2017/12/18.
 */

public class SelfDialog extends Dialog implements View.OnClickListener {
    private Button yes;
    private Button no;
    int[] mTvDataIds = new int[]{R.id.six_num_01, R.id.six_num_02, R.id.six_num_03, R.id.six_num_04, R.id.six_num_05, R.id.six_num_06, R.id.six_num_07, R.id.six_num_08, R.id.six_num_09, R.id.six_num_10};
    private int position;

    private ApiServer apiServer;

    public SelfDialog(@NonNull Context context) {
        super(context);
    }

    public SelfDialog(@NonNull Context context, int position) {
        super(context);
        this.position = position;
    }

    public SelfDialog(@NonNull Context context, int position, int theme) {
        super(context, theme);
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
        yes = (Button) findViewById(R.id.yes);//重新摇奖
        no = (Button) findViewById(R.id.no);//取消摇奖
        no.setOnClickListener(this);
        yes.setOnClickListener(this);
        setCanceledOnTouchOutside(false);//按空白处不能取消动画
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://103.242.1.48:81").addConverterFactory(GsonConverterFactory.create()).build();
        apiServer = retrofit.create(ApiServer.class);
        Call<ForecastModel> forecast = apiServer.getForecast();
        forecast.enqueue(new Callback<ForecastModel>() {
            @Override
            public void onResponse(Call<ForecastModel> call, Response<ForecastModel> response) {
                List<String> result = response.body().getData().getResult();
                String[] split = result.get(position).split(",");
                for (int i = 0; i < mTvDataIds.length; i++) {
                    TextView mTvData = findViewById(mTvDataIds[i]);
                    if (i < split.length) {
                        mTvData.setText("" + split[i]);
                        mTvData.setVisibility(View.VISIBLE);
                    } else {
                        mTvData.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ForecastModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no:
                cancel();
                break;
            case R.id.yes:
                Call<ForecastModel> forecast = apiServer.getForecast();
                forecast.enqueue(new Callback<ForecastModel>() {
                    @Override
                    public void onResponse(Call<ForecastModel> call, Response<ForecastModel> response) {
                        List<String> result = response.body().getData().getResult();
                        String[] split = result.get(position).split(",");
                        for (int i = 0; i < mTvDataIds.length; i++) {
                            TextView mTvData = findViewById(mTvDataIds[i]);
                            if (i < split.length) {
                                mTvData.setText("" + split[i]);
                                mTvData.setVisibility(View.VISIBLE);
                            } else {
                                mTvData.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastModel> call, Throwable t) {

                    }
                });
                break;
        }
    }
}
