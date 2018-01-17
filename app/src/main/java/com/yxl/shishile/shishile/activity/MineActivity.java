package com.yxl.shishile.shishile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.util.PackageUtil;

public class MineActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ImageView backImg = findViewById(R.id.back_img);
        backImg.setOnClickListener(this);
        TextView tvVersion = findViewById(R.id.version);
        tvVersion.setText("当前版本号：v" + PackageUtil.getLocalVersionName(MineActivity.this));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
