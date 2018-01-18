package com.yxl.shishile.shishile.widgets;

import android.view.View;

import com.zhouwei.mzbanner.MZBannerView;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public abstract class NoMultiClickListener implements MZBannerView.BannerPageClickListener {


    public static final int MIN_CLICK_DELAY_TIME = 1000;//这里设置不能超过多长时间
    private long lastClickTime = 0;

    protected abstract void onNoMultiClick(View v,int position);
    @Override
    public void onPageClick(View v,int position) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoMultiClick(v,position);
        }
    }
}