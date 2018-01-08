package com.yxl.shishile.shishile.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.app.AppDataManager;
import com.yxl.shishile.shishile.imchat.ChattingUI;
import com.yxl.shishile.shishile.imchat.JacenUtils;
import com.yxl.shishile.shishile.imchat.XmppAction;
import com.yxl.shishile.shishile.imchat.XmppService;
import com.yxl.shishile.shishile.imchat.XmppUtils;
import com.yxl.shishile.shishile.model.ChatRoomModel;
import com.yxl.shishile.shishile.model.UserModel;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListAdapter.ViewHolder> {

    public List<ChatRoomModel> mChatRoomList = new ArrayList<>();
    private Context mContext;
    public int[] mImgIds = new int[]{R.mipmap.ic_lottery_1, R.mipmap
            .ic_lottery_7, R.mipmap.ic_lottery_10, R.mipmap.ic_lottery_2, R
            .mipmap.ic_lottery_8, R.mipmap.ic_lottery_5, R.mipmap
            .ic_lottery_6, R.mipmap.ic_lottery_9, R.mipmap
            .ic_lottery_4, R.mipmap.ic_lottery_11, R.mipmap
            .ic_lottery_3};

    public ChatRoomListAdapter(List<ChatRoomModel> list) {
        if (list != null) {
            mChatRoomList.clear();
            mChatRoomList.addAll(list);
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
        if (mChatRoomList != null && mChatRoomList.size() > position) {
            final ChatRoomModel chatRoom = mChatRoomList.get(position);
            holder.mTvPrizeName.setText(chatRoom.chatRoomName);
            holder.mIvPrize.setImageResource(mImgIds[position]);
            Random random = new Random();
            int num = random.nextInt(200);
            holder.mTvGrounpNum.setText((num + 100) + "人");
            holder.mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserModel.UserInfo userInfo = AppDataManager.getInstance().getUser();
                    if (userInfo != null) {
                        enterChatRoomUI(chatRoom);
                    } else {
                        Toast.makeText(mContext, "请先登录账号", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void enterChatRoomUI(ChatRoomModel chatRoom) {
        Intent intent = new Intent(mContext, ChattingUI.class);
        intent.putExtra("chatRoomId", "" + chatRoom.chatId);
        intent.putExtra("chatRoomName", chatRoom.chatRoomName);
        intent.putExtra("lotteryId", chatRoom.lotteryId);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mChatRoomList.size();
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
