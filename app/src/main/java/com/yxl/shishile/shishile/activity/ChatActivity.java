package com.yxl.shishile.shishile.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMPageResult;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EasyUtils;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.fragment.ChatFragment;

public class ChatActivity extends FragmentActivity {

    public static ChatActivity activityInstance;
    String toChatUsername;
    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        //get user id or group id
        ChatFragment chatFragment = new ChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_CHATROOM);
        args.putString(EaseConstant.EXTRA_USER_ID, "" + getIntent().getStringExtra(EaseConstant
                .EXTRA_USER_ID));
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
//        String username = intent.getStringExtra("userId");
//        if (toChatUsername.equals(username))
//            super.onNewIntent(intent);
//        else {
//            finish();
//            startActivity(intent);
//        }

    }


    public String getToChatUsername() {
        return toChatUsername;
    }

}
