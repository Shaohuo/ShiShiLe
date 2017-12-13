package com.yxl.shishile.shishile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yxl.shishile.shishile.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageView back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        Button zhuce = findViewById(R.id.zhuce);
        zhuce.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.zhuce:
                break;
        }
    }
}
