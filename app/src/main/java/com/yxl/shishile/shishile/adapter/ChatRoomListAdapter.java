package com.yxl.shishile.shishile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.easeui.EaseConstant;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.activity.ChatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListAdapter.ViewHolder> {

    public List<EMChatRoom> mEMChatRoomList = new ArrayList<>();
    private Context mContext;

    public ChatRoomListAdapter(List<EMChatRoom> list) {
        if (list != null) {
            mEMChatRoomList.clear();
            mEMChatRoomList.addAll(list);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_room,
                parent, false);
        ChatRoomListAdapter.ViewHolder vh = new ChatRoomListAdapter.ViewHolder(view);
        mContext = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mEMChatRoomList != null && mEMChatRoomList.size() > position) {
            final EMChatRoom chatRoom = mEMChatRoomList.get(position);
            holder.mTvPrizeName.setText(chatRoom.getName());
//            holder.mIvPrize.setImageResource(mImgs[position]);
            holder.mTvGrounpNum.setText(chatRoom.getMemberCount() + "人");
            holder.mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, "" + chatRoom.getId());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mEMChatRoomList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvPrize;
        public TextView mTvPrizeName;
        public TextView mTvGrounpNum;
        public View mRootView;

        public ViewHolder(View view) {
            super(view);
            mRootView = view;
            mIvPrize = (ImageView) view.findViewById(R.id.ivPrize);
            mTvPrizeName = (TextView) view.findViewById(R.id.tvPrizename);
            mTvGrounpNum = (TextView) view.findViewById(R.id.tvGrounpNum);
        }
    }
}
