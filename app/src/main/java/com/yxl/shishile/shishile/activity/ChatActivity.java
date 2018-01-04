package com.yxl.shishile.shishile.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.platform.comapi.map.M;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMPageResult;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EasyUtils;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.app.AppDataManager;
import com.yxl.shishile.shishile.fragment.ChatFragment;
import com.yxl.shishile.shishile.imchat.XmppUtils;
import com.yxl.shishile.shishile.model.UserModel;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.DefaultParticipantStatusListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ChatActivity extends FragmentActivity {

    public static ChatActivity activityInstance;
    String toChatUsername;
    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
//        ChatFragment chatFragment = new ChatFragment();
//        //传入参数
//        Bundle args = new Bundle();
//        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_CHATROOM);
//        args.putInt("lotteryId", getIntent().getIntExtra("lotteryId", -1));
//        args.putString(EaseConstant.EXTRA_USER_ID, "" + getIntent().getStringExtra(EaseConstant
//                .EXTRA_USER_ID));
//        chatFragment.setArguments(args);
        String chatRoomId = getIntent().getStringExtra("chatRoomId");
        UserModel.UserInfo userInfo = AppDataManager.getInstance().getUser();
        if (userInfo != null) {
//            try {
//                MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(XmppUtils
//                        .getInstance().getConnection());
//                MultiUserChat multiUserChat = manager.getMultiUserChat(chatRoomId +
// "@conference" +
//                        ".localhost");
//                multiUserChat.create("" + userInfo.getUsername());
//                Form form = multiUserChat.getConfigurationForm();
//                Form answerForm = form.createAnswerForm();
//                answerForm.setAnswer("muc#roomconfig_persistentroom", true);
//                multiUserChat.sendConfigurationForm(answerForm);
//                multiUserChat.sendMessage("mnopog");
//            } catch (SmackException e) {
//                e.printStackTrace();
//            } catch (XMPPException.XMPPErrorException e) {
//                e.printStackTrace();
//            } catch (XMPPException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            MultiUserChat multiUserChat = XmppUtils.getInstance().joinChatRoom("" + chatRoomId,
                    userInfo.getUsername());
            try {
                multiUserChat.sendMessage("haaa");
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
//        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
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
