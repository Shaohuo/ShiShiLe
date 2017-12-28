package com.yxl.shishile.shishile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yxl.shishile.shishile.R;

public class InformationDetailActivity extends AppCompatActivity {

    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        url = getIntent().getStringExtra("info");
        WebView infoweb = findViewById(R.id.info_web);
        infoweb.getSettings().setJavaScriptEnabled(true);
        infoweb.setWebViewClient(new WebViewClient());
        infoweb.loadUrl(url);
    }
}
