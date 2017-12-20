package com.yxl.shishile.shishile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.adapter.ForecastAdapter;
import com.yxl.shishile.shishile.adapter.MyPrizeAdapter;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.model.Forecast;
import com.yxl.shishile.shishile.model.ForecastListModel;
import com.yxl.shishile.shishile.model.Lottery;
import com.yxl.shishile.shishile.model.LotteryListModel;
import com.yxl.shishile.shishile.widgets.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class M_ForecastActivity extends SwipeBackActivity implements View.OnClickListener,BGARefreshLayout
        .BGARefreshLayoutDelegate {
    private RecyclerView mRecyclerView;
    private ForecastAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private List<Forecast> mLotteryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forecast);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        ImageView imgBack = findViewById(R.id.main_forecast_back);
        imgBack.setOnClickListener(this);
        //创建默认的线性LayoutManager
        mRefreshLayout = (BGARefreshLayout)findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager
                .HORIZONTAL));
        mAdapter = new ForecastAdapter(this, mLotteryList);
        mRecyclerView.setAdapter(mAdapter);
        /*mAdapter.setOnItemClickListener(new ForecastAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(M_ForecastActivity.this, "这是" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(M_ForecastActivity.this,ForecastActivity.class);
                startActivity(intent);
            }
        });*/


    }

    private void loadPrizeListData() {
        Call<ForecastListModel> call = ApiManager.getInstance().create(ApiServer.class).getForecastListModel();

        call.enqueue(new Callback<ForecastListModel>() {

            @Override
            public void onResponse(Call<ForecastListModel> call, Response<ForecastListModel> response) {
                mRefreshLayout.endRefreshing();
                ForecastListModel body = response.body();
                Log.d("OpenPrizeFragment", "" + response.toString());
                if (response.isSuccessful() && response.body() != null && body.data != null &&

                        body.data.size() > 0) {
                    mLotteryList.clear();
                    mLotteryList.addAll(body.data);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.e("OpenPrizeFragment", "unsuccess");
                }
            }

            @Override
            public void onFailure(Call<ForecastListModel> call, Throwable t) {
                Log.e("OpenPrizeFragment", "" + t.getMessage());
                mRefreshLayout.endRefreshing();
            }
        });
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
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadPrizeListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
