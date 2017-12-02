package com.yxl.shishile.shishile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.adapter.HistoryAdapter;
import com.yxl.shishile.shishile.adapter.MyPrizeAdapter;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.event.OpenCountDownEvent;
import com.yxl.shishile.shishile.model.Lottery;
import com.yxl.shishile.shishile.model.LotteryList;
import com.yxl.shishile.shishile.widgets.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LotteryActivity extends SwipeBackActivity implements CountdownView.OnCountdownEndListener,
        BGARefreshLayout.BGARefreshLayoutDelegate {
    int index = 1;
    HashMap<Integer, Lottery> mLotteryMaps = new HashMap<>();
    int[] mTvDataIds = new int[]{R.id.six_num_01, R.id.six_num_02, R.id.six_num_03, R.id
            .six_num_04, R.id.six_num_05, R.id.six_num_06, R.id.six_num_07};
    private CountdownView mCvCountdownView;
    private TextView mTvOpenPrize;
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private List<Lottery> mLotteryList = new ArrayList<>();
    private int[] mImgs = new int[]{R.mipmap.bg_countdown_chongqing, R.mipmap.bg_countdown_hubei,
            R.mipmap.bg_countdown_liuhecai, R.mipmap.bg_countdown_guangdong, R.mipmap
            .bg_countdown_fucai, R.mipmap
            .bg_countdown_paileisan, R.mipmap.bg_countdown_xinjiang, R.mipmap
            .bg_countdown_jiangsu, R.mipmap.bg_countdown_jiangxi, R
            .mipmap.bg_countdown_beijing, R.mipmap.bg_countdown_shandong};
    private String[] mNames = new String[]{"重庆时时彩", "湖北快3", "六合彩", "广东11选5", "福彩3D", "排列3",
            "新疆时时彩", "江苏快3", "江西11选5", "北京PK10", "山东11选5"};
    private int mIndex;
    private TextView mTvLotteryName;
    private ImageView mIvLotteryBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mIndex = getIntent().getIntExtra("index", -1);
        setContentView(R.layout.activity_lottery);
        mCvCountdownView = (CountdownView) findViewById(R.id.cv_countdownViewTest1);
        mTvLotteryName = findViewById(R.id.tvLotteryName);
        mIvLotteryBar = findViewById(R.id.ivLotteryBar);
        mTvOpenPrize = findViewById(R.id.tvOpenPrize);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTvLotteryName.setText(mNames[mIndex - 1]);
        mIvLotteryBar.setImageResource(mImgs[mIndex - 1]);
        mCvCountdownView.setOnCountdownEndListener(this);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager
                .HORIZONTAL));
        mAdapter = new HistoryAdapter(this, mLotteryList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.beginRefreshing();
            }
        }, 500);
    }


    @Override
    protected void onDestroy()
    {
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
        Call<LotteryList> call = ApiManager.getInstance().create(ApiServer.class).getLotteryList
                (mIndex, "qzcx72trd7ax5w90");
        call.enqueue(new Callback<LotteryList>() {

            @Override
            public void onResponse(Call<LotteryList> call, Response<LotteryList> response) {
                mRefreshLayout.endRefreshing();
                LotteryList body = response.body();
                Log.d("LotteryActivity", "" + response.toString());
                if (body.data != null && body.data.size() > 0) {
                    mLotteryList.clear();
                    mLotteryList.addAll(body.data);
                    mAdapter.notifyDataSetChanged();
                } else {

                }
                //发送倒计时事件
//                OpenCountDownEvent countDownEvent = new OpenCountDownEvent();
//                countDownEvent.setLottery(body);
//                EventBus.getDefault().post(countDownEvent);
//                String[] split = body.data.split(",");
//                String number = body.number;
//                String time = body.time;
//                for (int i = 0; i < 7; i++) {
//                    TextView mTvData = findViewById(mTvDataIds[i]);
//                    if (i < split.length) {
//                        mTvData.setText("" + split[i]);
//                        mTvData.setVisibility(View.VISIBLE);
//                    } else {
//                        mTvData.setVisibility(View.GONE);
//                    }
//                }
            }

            @Override
            public void onFailure(Call<LotteryList> call, Throwable t) {
                Log.d("LotteryActivity", "onFailure");
                mRefreshLayout.endRefreshing();
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

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadLotteryData();//获得时间差
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
