package com.yxl.shishile.shishile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.adapter.MyPrizeAdapter;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        //通过获取服务器传递过来的 我的收藏列表，对应我收藏的彩种开奖信息
        //显示方式与开奖界面相同
        RecyclerView collectListview = findViewById(R.id.collectlistview);
        MyPrizeAdapter myPrizeAdapter = new MyPrizeAdapter();
        collectListview.setAdapter(myPrizeAdapter);



    }
}
