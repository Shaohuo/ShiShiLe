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


import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.app.AppDataManager;

import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacen on 2017/10/19 18:02.
 * jacen@iswsc.com
 */

public class ChattingUI extends Activity implements OnItemClickListener, View.OnClickListener,
        View.OnLayoutChangeListener {

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
    List<ChatMessageVo> mMsgList = new ArrayList<>();

    //Activity最外层的Layout视图
    private View mActivityRootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_chatting);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mTitle = findViewById(R.id.app_title);
        mConetnt = findViewById(R.id.content);
        mBack = findViewById(R.id.app_back);
        mSendBtn = (TextView) findViewById(R.id.send);
        mActivityRootView = findViewById(R.id.root_layout);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
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
        ChatMessageDataBase.getInstance().deleteChatMessageByChatJid(chatRoomJid);
        mMultiUserChat = XmppUtils.getInstance().joinChatRoom("" + chatRoomId,
                mUsername);
        mChatBroadcastReceiver = new ChatBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XmppAction.ACTION_MESSAGE + "_" + chatRoomJid);
        JacenUtils.registerLocalBroadcastReceiver(this, mChatBroadcastReceiver, intentFilter);

        mAdapter = new ChatAdapter(this, mMsgList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        JacenDialogUtils.showDialog(ChattingUI.this, "登入中...");
        mRecyclerView.setVisibility(View.INVISIBLE);

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
                XmppUtils.getInstance().sendChatRoomMessage(chatRoomId, content);
                mConetnt.setText("");
                break;
            case R.id.app_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //添加layout大小发生改变监听器
        mActivityRootView.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值

        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

//            Toast.makeText(ChattingUI.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
            mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {

//            Toast.makeText(ChattingUI.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

        }

    }

    class ChatBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ChatMessageVo vo = (ChatMessageVo) intent.getSerializableExtra("chat");
            if (vo.getContent() == null) {
                List<ChatMessageVo> msgList = ChatMessageDataBase.getInstance()
                        .getChatMessageListByChatJid(chatRoomJid);
                mMsgList.clear();
                mMsgList.addAll(msgList);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
                JacenDialogUtils.dismissDialog();
                mRecyclerView.setVisibility(View.VISIBLE);
            }
            if (vo.getUnRead() == 1) {
                mAdapter.addChatMessage(vo);
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        }
    }
}
