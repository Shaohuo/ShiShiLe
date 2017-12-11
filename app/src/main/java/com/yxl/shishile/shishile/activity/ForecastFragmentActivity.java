package com.yxl.shishile.shishile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.fragment.BeijingPkshiFragment;

public class ForecastFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_fragment);
        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.id_content,new BeijingPkshiFragment())
                    .commit();
        }
    }
}
