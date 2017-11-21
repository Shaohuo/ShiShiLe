package com.yxl.shishile.shishile.openprize;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class MyPrizeAdapter extends RecyclerView.Adapter<MyPrizeAdapter.ViewHolder> {

    private String[] mNames = new String[]{"重庆时时彩", "湖北快3", "六合彩", "广东11选5", "福彩3D", "排列3", "新疆时时彩", "江苏快3", "江西11选5", "北京PK10", "山东11选5"};
    private int[] mImgs = new int[]{R.mipmap.ic_lottery_1, R.mipmap.ic_lottery_2, R.mipmap.ic_lottery_3, R.mipmap.ic_lottery_4, R.mipmap.ic_lottery_5, R.mipmap.ic_lottery_6, R.mipmap.ic_lottery_7, R.mipmap.ic_lottery_8, R.mipmap.ic_lottery_9, R.mipmap.ic_lottery_10, R.mipmap.ic_lottery_11};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_prize, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTvPrizeName.setText("" + mNames[position]);
        viewHolder.mIvPrize.setImageResource(mImgs[position]);
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvPrize;
        public TextView mTvPrizeName;
        public TextView mTvPrizeStage;
        public TextView mTvOpenTime;

        public ViewHolder(View view) {
            super(view);
            mIvPrize = (ImageView) view.findViewById(R.id.ivPrize);
            mTvPrizeName = (TextView) view.findViewById(R.id.tvPrizename);
            mTvPrizeStage = (TextView) view.findViewById(R.id.tvPrizeStage);
            mTvOpenTime = (TextView) view.findViewById(R.id.tvOpenTime);
        }
    }
}
