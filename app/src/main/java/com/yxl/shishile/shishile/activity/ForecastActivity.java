package com.yxl.shishile.shishile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.adapter.ForecastAdapter;
import com.yxl.shishile.shishile.widgets.RecycleViewDivider;
public class ForecastActivity extends BaseActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_n);
        RecyclerView mRecyclerView = findViewById(R.id.firecast_list);
        Button send_forecast = findViewById(R.id.send_forecast);
        send_forecast.setOnClickListener(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(new ForecastAdapter());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_forecast:
                Intent intent = new Intent(ForecastActivity.this,ForecastFragmentActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                break;
        }
    }
}