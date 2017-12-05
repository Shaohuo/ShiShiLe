package com.yxl.shishile.shishile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yxl.shishile.shishile.R;

import cn.iwgang.countdownview.CountdownView;

public class MyWalletActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        View walat = findViewById(R.id.walat);
        walat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWalletActivity.this, BPActivity.class);
                startActivity(intent);
            }
        });
    }

}
