package com.yxl.shishile.shishile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.dialog.SelfDialog;
import com.yxl.shishile.shishile.model.Lottery;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> implements View.OnClickListener
{

    private String[] mNames = new String[]{"重庆时时彩", "湖北快3", "六合彩", "广东11选5", "福彩3D", "排列3", "新疆时时彩", "江苏快3", "江西11选5", "北京PK10", "山东11选5"};
    private int[] mImgs = new int[]{R.mipmap.ic_lottery_1, R.mipmap.ic_lottery_2, R.mipmap.ic_lottery_3, R.mipmap.ic_lottery_4, R.mipmap.ic_lottery_5, R.mipmap.ic_lottery_6, R.mipmap.ic_lottery_7, R.mipmap.ic_lottery_8, R.mipmap.ic_lottery_9, R.mipmap.ic_lottery_10, R.mipmap.ic_lottery_11};
    private int[] mDataCounts = new int[]{5,3,7,5,3, 3, 5, 3, 5, 10, 5};
    private  View view;
    HashMap<Integer, Lottery> mLotteryMaps = new HashMap<>();

    public void setData(HashMap<Integer, Lottery> maps) {
        mLotteryMaps.clear();
        mLotteryMaps.putAll(maps);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
         view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_forecast, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }
    private OnItemClickListener mOnItemClickListener = null;
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }



    int[] mTvDataIds = new int[]{R.id.six_num_01, R.id.six_num_02, R.id.six_num_03, R.id.six_num_04, R.id.six_num_05, R.id.six_num_06, R.id.six_num_07, R.id.six_num_08, R.id.six_num_09, R.id.six_num_10};

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position)
    {
        viewHolder.mTvPrizeName.setText("" + mNames[position]);
        viewHolder.mIvPrize.setImageResource(mImgs[position]);
        viewHolder.itemView.setTag(position);
        viewHolder.changeBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //viewHolder.changeBall.setSelected(true);
                SelfDialog selfDialog = new SelfDialog(view.getContext(), position,R.style.MyDialog);
                selfDialog.show();
            }
        });
        for (int i = 0; i < mTvDataIds.length; i++) {
            TextView mTvData = viewHolder.mLlData.findViewById(mTvDataIds[i]);
            if (i < mDataCounts[position]) {
                mTvData.setText("");
                mTvData.setVisibility(View.VISIBLE);
            } else {
                mTvData.setVisibility(View.GONE);
            }
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvPrize;
        public TextView mTvPrizeName;
        public TextView mTvPrizeNum;
        public TextView mTvOpenTime;
        public LinearLayout mLlData;
        public Button changeBall;

        public ViewHolder(View view) {
            super(view);
            mIvPrize = (ImageView) view.findViewById(R.id.ivPrize);
            mTvPrizeName = (TextView) view.findViewById(R.id.tvPrizename);
            mTvPrizeNum = (TextView) view.findViewById(R.id.tvPrizeNum);
            mTvOpenTime = (TextView) view.findViewById(R.id.tvOpenTime);
            mLlData = (LinearLayout) view.findViewById(R.id.llData);
           changeBall = view.findViewById(R.id.changeBall);

        }
    }
}
