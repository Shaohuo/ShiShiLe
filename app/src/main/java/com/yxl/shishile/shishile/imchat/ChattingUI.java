package com.yxl.shishile.shishile.imchat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.app.AppDataManager;

import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.List;

/**
 * Created by Jacen on 2017/10/19 18:02.
 * jacen@iswsc.com
 */

public class ChattingUI extends Activity implements OnItemClickListener, View.OnClickListener {

    private TextView mSendBtn;
    private EditText mConetnt;

    //    private ContactVo mContactVo;
    private String chatRoomId;
    private String chatRoomName;

    private ChatAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private ChatBroadcastReceiver mChatBroadcastReceiver;
    private MultiUserChat mMultiUserChat;
    private TextView mTitle;
    private ImageView mBack;
    private String chatRoomJid;
    private String mUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_chatting);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mTitle = findViewById(R.id.app_title);
        mBack = findViewById(R.id.app_back);
        mConetnt = (EditText) findViewById(R.id.content);
        mSendBtn = (TextView) findViewById(R.id.send);
        setListener();
        initData();
    }

    private void setListener() {
        mSendBtn.setOnClickListener(this);
        mBack.setOnClickListener(this);

        mConetnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mSendBtn.setEnabled(false);
                } else {
                    mSendBtn.setEnabled(true);
                }
            }
        });
    }

    private void initData() {
        chatRoomName = getIntent().getStringExtra("chatRoomName");
        chatRoomId = getIntent().getStringExtra("chatRoomId");
        chatRoomJid = chatRoomId + "@conference." + XmppUtils.SERVICENAME;
        mTitle.setText(chatRoomName);
        mUsername = AppDataManager.getInstance().getUser().getUsername();
        mMultiUserChat = XmppUtils.getInstance().joinChatRoom("" + chatRoomId,
                mUsername);

        mChatBroadcastReceiver = new ChatBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XmppAction.ACTION_MESSAGE + "_" + chatRoomJid);
        JacenUtils.registerLocalBroadcastReceiver(this, mChatBroadcastReceiver, intentFilter);
        ChatMessageDataBase.getInstance().clearUnReadByJid(chatRoomJid);
        List<ChatMessageVo> mList = ChatMessageDataBase.getInstance().getChatMessageListByChatJid
                (chatRoomJid);
        if (mList.size() > 10) {
            mList = mList.subList(mList.size() - 10, mList.size());
        }
        mAdapter = new ChatAdapter(this, mList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JacenUtils.unRegisterLocalBroadcastReceiver(this, mChatBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send://发送信息
                String content = mConetnt.getText().toString().trim();
                ChatMessageVo vo = XmppUtils.getInstance().sendChatRoomMessage(chatRoomId,
                        content);
                if (vo != null) {
                    mConetnt.setText("");
                    mAdapter.addChatMessage(vo);
                    mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
                    ChatMessageDataBase.getInstance().saveChatMessage(vo);
                }
                break;
            case R.id.app_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    class ChatBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ChatMessageVo vo = (ChatMessageVo) intent.getSerializableExtra("chat");
            String from = vo.getFrom();
            String meJid = chatRoomJid + "/" + mUsername;
            if (!meJid.equals(from)) {
                mAdapter.addChatMessage(vo);
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
                ChatMessageDataBase.getInstance().saveChatMessage(vo);
            }

        }
    }
}
