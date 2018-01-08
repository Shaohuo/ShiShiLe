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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.activity.RegisterActivity;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.app.AppDataManager;
import com.yxl.shishile.shishile.event.XmppGrounpChatMessageEvent;
import com.yxl.shishile.shishile.event.XmppLoginEvent;
import com.yxl.shishile.shishile.model.CountDownModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private CountdownView mCvCountdownView;
    private TextView mTvLotteryNum;
    private TextView mTvOpenPrize;
    int[] mTvDataIds = new int[]{R.id.six_num_01, R.id.six_num_02, R.id.six_num_03, R.id
            .six_num_04, R.id.six_num_05, R.id.six_num_06, R.id.six_num_07, R.id.six_num_08, R.id
            .six_num_09, R.id.six_num_10};
    private TextView mTvNextLotteryNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_chatting);
        EventBus.getDefault().register(this);
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

        mCvCountdownView = findViewById(R.id.countdownView);
        mTvOpenPrize = findViewById(R.id.tvOpenPrize);
        mTvLotteryNum = findViewById(R.id.tvLotteryNum);
        mTvNextLotteryNum = findViewById(R.id.tvNextLotteryNum);
        mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                mCvCountdownView.restart();
                loadLotteryCountDown();
            }
        });
        for (int i = 0; i < mTvDataIds.length; i++) {
            TextView mTvData = findViewById(mTvDataIds[i]);
            mTvData.setVisibility(View.INVISIBLE);
        }
        setListener();
        initData();
        loadChatRoom();
        loadLotteryCountDown();
    }

    private void loadChatRoom() {
        ChatMessageDataBase.getInstance().deleteChatMessageByChatJid(chatRoomJid);
        mMultiUserChat = XmppUtils.getInstance().joinChatRoom("" + chatRoomId,
                mUsername);

        if (mMultiUserChat == null) {
            Toast.makeText(ChattingUI.this, "进入聊天室失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadLotteryCountDown() {
        int lotteryId = getIntent().getIntExtra("lotteryId", -1);
        Call<CountDownModel> call = ApiManager.getInstance().create(ApiServer.class)
                .getLotteryCountDown(lotteryId);
        call.enqueue(new Callback<CountDownModel>() {
            @Override
            public void onResponse(Call<CountDownModel> call, Response<CountDownModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().data !=
                        null) {
                    CountDownModel.CountDown countDownData = response.body().data;
                    if (countDownData.countdown >= 0) {
                        mCvCountdownView.setVisibility(View.VISIBLE);
                        mTvOpenPrize.setVisibility(View.INVISIBLE);
                        mCvCountdownView.start(countDownData.countdown * 1000);
                        if (countDownData.number != null) {
                            try {
                                long number = Long.valueOf("" + countDownData.number);
                                mTvLotteryNum.setText("第" + number + "期");
                                mTvNextLotteryNum.setText("距第" + (number + 1) + "期开奖");
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            String[] split = countDownData.data.split("\\,|\\+");
                            for (int i = 0; i < mTvDataIds.length; i++) {
                                if (isFinishing()) {
                                    return;
                                }
                                TextView mTvData = findViewById(mTvDataIds[i]);
                                if (i < split.length) {
                                    mTvData.setText("" + split[i]);
                                    mTvData.setVisibility(View.VISIBLE);
                                } else {
                                    mTvData.setVisibility(View.GONE);
                                }
                            }
                        }

                    } else {
                        mCvCountdownView.setVisibility(View.INVISIBLE);
                        mTvOpenPrize.setVisibility(View.VISIBLE);
                        loadLotteryCountDown();
                    }
                }
            }

            @Override
            public void onFailure(Call<CountDownModel> call, Throwable t) {

            }
        });
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
        mAdapter = new ChatAdapter(this, mMsgList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);


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


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onXmppGrounpChatMessageEvent(XmppGrounpChatMessageEvent event) {
        if (event != null && event.getChatMessageVo() != null) {
            ChatMessageVo vo = event.getChatMessageVo();
            if (vo.getContent() == null) {
                List<ChatMessageVo> msgList = ChatMessageDataBase.getInstance()
                        .getChatMessageListByChatJid(chatRoomJid);
                mMsgList.clear();
                mMsgList.addAll(msgList);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }

            if (vo.getUnRead() == 1) {
                mAdapter.addChatMessage(vo);
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        }
    }

}
