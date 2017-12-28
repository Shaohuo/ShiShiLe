package com.yxl.shishile.shishile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.yxl.shishile.shishile.R;

public class InformationDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        ImageView backimg =findViewById(R.id.information_back);
        backimg.setOnClickListener(this);

        url = getIntent().getStringExtra("info");
        WebView infoweb = findViewById(R.id.info_web);
        infoweb.getSettings().setJavaScriptEnabled(true);
        infoweb.setWebViewClient(new WebViewClient());
        infoweb.loadUrl(url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.information_back:
                finish();
                break;
        }
    }
}
