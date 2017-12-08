package com.yxl.shishile.shishile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.adapter.ForecastAdapter;
import com.yxl.shishile.shishile.widgets.RecycleViewDivider;

public class M_ForecastActivity extends SwipeBackActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ForecastAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forecast);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        ImageView imgBack = findViewById(R.id.main_forecast_back);

        imgBack.setOnClickListener(this);
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mAdapter = new ForecastAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ForecastAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(M_ForecastActivity.this, "这是" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(M_ForecastActivity.this,ForecastActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
