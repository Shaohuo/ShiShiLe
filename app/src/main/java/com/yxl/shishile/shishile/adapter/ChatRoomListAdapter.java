package com.yxl.shishile.shishile.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.activity.ChatActivity;

import java.util.Random;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListAdapter.ViewHolder> {


    public String[] mNames = new String[]{
            "综合聊天室", "重庆时时彩", "北京PK10", "江苏快三"
    };
    public int[] mImgs = new int[]{
            R.mipmap.ic_group, R.mipmap.ic_lottery_1, R.mipmap.ic_lottery_10, R.mipmap
            .ic_lottery_2
    };

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_room,
                parent, false);
        ChatRoomListAdapter.ViewHolder vh = new ChatRoomListAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.getContext().startActivity(new Intent(parent.getContext(), ChatActivity
                        .class));
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvPrizeName.setText(mNames[position]);
        holder.mIvPrize.setImageResource(mImgs[position]);
        Random random = new Random();
        int grounpNum = random.nextInt(200) + 100;
        holder.mTvGrounpNum.setText(grounpNum + "人");
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvPrize;
        public TextView mTvPrizeName;
        public TextView mTvGrounpNum;

        public ViewHolder(View view) {
            super(view);
            mIvPrize = (ImageView) view.findViewById(R.id.ivPrize);
            mTvPrizeName = (TextView) view.findViewById(R.id.tvPrizename);
            mTvGrounpNum = (TextView) view.findViewById(R.id.tvGrounpNum);
        }
    }
}
