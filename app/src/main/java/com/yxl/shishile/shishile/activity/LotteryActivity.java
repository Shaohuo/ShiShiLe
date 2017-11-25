package com.yxl.shishile.shishile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.event.OpenCountDownEvent;
import com.yxl.shishile.shishile.model.Lottery;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LotteryActivity extends Activity implements CountdownView.OnCountdownEndListener {
    int index = 1;
    HashMap<Integer, Lottery> mLotteryMaps = new HashMap<>();
    int[] mTvDataIds = new int[]{R.id.six_num_01, R.id.six_num_02, R.id.six_num_03, R.id.six_num_04, R.id.six_num_05, R.id.six_num_06, R.id.six_num_07};
    private CountdownView mCvCountdownView;
    private TextView mTvOpenPrize;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_lottery);
        mCvCountdownView = (CountdownView) findViewById(R.id.cv_countdownViewTest1);
        mTvOpenPrize = findViewById(R.id.tvOpenPrize);
        mCvCountdownView.setOnCountdownEndListener(this);
        loadLotteryData();//获得时间差
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOpenCountDownEvent(OpenCountDownEvent event) {
        /* Do something */
        if (event.getLottery() != null) {
            Lottery lottery = event.getLottery();
            String openTime = lottery.time;
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date openDate = sDateFormat.parse(openTime);
                Date currentDate = new Date();
                //得出剩余时间
                long remainTime = openDate.getTime() - currentDate.getTime();
                mCvCountdownView.setVisibility(View.VISIBLE);
                mTvOpenPrize.setVisibility(View.INVISIBLE);
                mCvCountdownView.start(5000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadLotteryData() {
        Call<Lottery> call = ApiManager.getInstance().create(ApiServer.class).getLottery(6, "qzcx72trd7ax5w90");
        call.enqueue(new Callback<Lottery>() {

            @Override
            public void onResponse(Call<Lottery> call, Response<Lottery> response) {
                Lottery body = response.body();
                //发送倒计时事件
                OpenCountDownEvent countDownEvent = new OpenCountDownEvent();
                countDownEvent.setLottery(body);
                EventBus.getDefault().post(countDownEvent);
                String[] split = body.data.split(",");
                String number = body.number;
                String time = body.time;
                for (int i = 0; i < 7; i++) {
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
            public void onFailure(Call<Lottery> call, Throwable t) {

            }
        });
    }

    /**
     * 倒计时结束监听
     *
     * @param cv
     */
    @Override
    public void onEnd(CountdownView cv) {
        mCvCountdownView.setVisibility(View.INVISIBLE);
        mTvOpenPrize.setVisibility(View.VISIBLE);
        mCvCountdownView.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadLotteryData();
            }
        }, 1000);
    }
}
