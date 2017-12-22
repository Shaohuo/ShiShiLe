package com.yxl.shishile.shishile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.model.Lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final Context mContext;

    // private String[] mNames = new String[]{"重庆时时彩", "湖北快3", "六合彩", "广东11选5", "福彩3D", "排列3", "新疆时时彩", "江苏快3", "江西11选5", "北京PK10", "山东11选5"};
    //private int[] mImgs = new int[]{R.mipmap.ic_lottery_1, R.mipmap.ic_lottery_2, R.mipmap.ic_lottery_3, R.mipmap.ic_lottery_4, R.mipmap.ic_lottery_5, R.mipmap.ic_lottery_6, R.mipmap.ic_lottery_7, R.mipmap.ic_lottery_8, R.mipmap.ic_lottery_9, R.mipmap.ic_lottery_10, R.mipmap.ic_lottery_11};

    List<Lottery> mLotteryList = new ArrayList<>();


    public HistoryAdapter(Context context, List<Lottery> list) {
        mContext = context;
        mLotteryList = list;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false);
        HistoryAdapter.ViewHolder vh = new HistoryAdapter.ViewHolder(view);
        return vh;
    }


    int[] mTvDataIds = new int[]{R.id.six_num_01, R.id.six_num_02, R.id.six_num_03, R.id.six_num_04, R.id.six_num_05, R.id.six_num_06, R.id.six_num_07, R.id.six_num_08, R.id.six_num_09, R.id.six_num_10};

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder viewHolder, int position) {
        //viewHolder.mTvPrizeName.setText("" + mNames[position]);
        // viewHolder.mIvPrize.setImageResource(mImgs[position]);
        Lottery lottery = mLotteryList.get(position);
        if (lottery != null) {
            //viewHolder.mTvOpenTime.setText("开奖时间:" + lottery.time);
            viewHolder.mTvLotteryNum.setText("第" + lottery.number + "期");
            viewHolder.mTvLotteryTime.setText(lottery.opentime + "开奖");
            String[] split = lottery.data.split("\\,|\\+");
            for (int i = 0; i < mTvDataIds.length; i++) {
                TextView mTvData = viewHolder.mRootView.findViewById(mTvDataIds[i]);
                if (i < split.length) {
                    mTvData.setText("" + split[i]);
                    mTvData.setBackground(mContext.getResources().getDrawable(R.drawable.shape_textview_red));
                    mTvData.setVisibility(View.VISIBLE);
                } else {
                    mTvData.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mLotteryList.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvLotteryNum;
        public TextView mTvLotteryTime;
        public View mRootView;

        public ViewHolder(View view) {
            super(view);
            mTvLotteryNum = (TextView) view.findViewById(R.id.tvLotteryNum);
            mTvLotteryTime = (TextView) view.findViewById(R.id.tvLotteryTime);
            mRootView = view;
        }
    }
}
