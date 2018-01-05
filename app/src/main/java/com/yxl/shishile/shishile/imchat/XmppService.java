package com.yxl.shishile.shishile.imchat;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;


import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaExtensionFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.roster.packet.RosterPacket;
import org.jxmpp.util.XmppStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/25 2:19.
 */

public class XmppService extends Service {

    private MyStanzaListener myStanzaListener;
    private MyStanzaFilter myStanzaFilter;
    private MyRosterListener mRosterListener;
    private MyConnectionListener mConnectionListener;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myStanzaListener = new MyStanzaListener();
        myStanzaFilter = new MyStanzaFilter();
        mRosterListener = new MyRosterListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            String account = intent.getStringExtra("account");
            String password = intent.getStringExtra("password");

            if (XmppAction.ACTION_LOGIN.equals(action)) {//登录
                try {
                    XmppUtils.getInstance().destory();
                    XmppUtils.getInstance().init(myStanzaListener, myStanzaFilter,
                            mRosterListener, mConnectionListener);
                    new LoginThread(account, password).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction
                            .ACTION_SERVICE_ERROR, null);
                }

            } else if (XmppAction.ACTION_REGISTER.equals(action)) {//注册
                try {
                    XmppUtils.getInstance().destory();
                    XmppUtils.getInstance().init(myStanzaListener, myStanzaFilter,
                            mRosterListener, mConnectionListener);
                    new RegisterThread(account, password).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction
                            .ACTION_SERVICE_ERROR, null);
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }


    class RegisterThread extends Thread {

        private String account;
        private String password;

        public RegisterThread(String account, String password) {
            this.account = account;
            this.password = password;
        }

        @Override
        public void run() {
            String action = XmppUtils.getInstance().registerXmpp(account, password);
            JacenUtils.intentLocalBroadcastReceiver(XmppService.this, action, null);
        }
    }

    class LoginThread extends Thread {
        private String account;
        private String password;

        public LoginThread(String account, String password) {
            this.account = account;
            this.password = password;
        }

        @Override
        public void run() {
            String action = XmppUtils.getInstance().loginXmpp(account, password);
            JacenUtils.intentLocalBroadcastReceiver(XmppService.this, action, null);
        }
    }

    class MyStanzaListener implements StanzaListener {

        @Override
        public void processPacket(Stanza packet) throws SmackException.NotConnectedException {
            ExtensionElement delayExtension = packet.getExtension("delay", "urn:xmpp:delay");
            Log.i("XmppService", "packet = " + packet.toString());
            if (packet instanceof Message) {
                Message msg = (Message) packet;
                if (Message.Type.chat.equals(msg.getType())) {//单聊

                } else if (Message.Type.groupchat.equals(msg.getType())) {//群聊
                    if (TextUtils.isEmpty(msg.getBody()))
                        return;
                    if (delayExtension != null) {//不接受延时消息
                        return;
                    }
                    ChatMessageVo chatMessageVo = new ChatMessageVo();
                    chatMessageVo.parseMessage(msg);
                    chatMessageVo.setChatType(ChatType.text.getId());
                    chatMessageVo.setFrom(msg.getFrom());
                    chatMessageVo.setShowTime(ChatMessageDataBase.getInstance().isShowTime
                            (chatMessageVo.getChatJid(), chatMessageVo.getSendTime()));
                    chatMessageVo.setUnRead(1);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("chat", chatMessageVo);
                    String action_chatting = XmppAction.ACTION_MESSAGE + "_" + chatMessageVo
                            .getChatJid();
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction
                            .ACTION_MESSAGE, bundle);
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this, action_chatting,
                            bundle);
//                    ChatMessageDataBase.getInstance().saveChatMessage(chatMessageVo);
                }
            } else if (packet instanceof Presence) {//在线状态
                Presence presence = (Presence) packet;
            }

        }
    }

    class MyRosterListener implements RosterListener {

        //有新好友请求 自动同意
        @Override
        public void entriesAdded(Collection<String> addresses) {
            Log.i("XmppService", "entriesAdded = " + addresses);
            for (String jid : addresses) {
                try {
                    Roster roster = Roster.getInstanceFor(XmppUtils.getInstance().getConnection());
                    RosterEntry entry = roster.getEntry(jid);
                    if (entry != null && entry.getType() == RosterPacket.ItemType.to) {
                        Presence presence1 = new Presence(Presence.Type.subscribed);
                        presence1.setTo(jid);
                        XmppUtils.getInstance().getConnection().sendStanza(presence1);
                        return;
                    }
                    addEntry(jid, XmppStringUtils.parseLocalpart(jid), "Friends");
                    Presence presence1 = new Presence(Presence.Type.subscribed);
                    presence1.setTo(jid);
                    XmppUtils.getInstance().getConnection().sendStanza(presence1);
                    Presence presence2 = new Presence(Presence.Type.subscribe);
                    presence2.setTo(jid);
                    XmppUtils.getInstance().getConnection().sendStanza(presence2);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                }
            }
        }

        //好友信息更新了 vcard
        @Override
        public void entriesUpdated(Collection<String> addresses) {
            Log.i("XmppService", "entriesUpdated = " + addresses);

        }

        //删除好友
        @Override
        public void entriesDeleted(Collection<String> addresses) {
            Log.i("XmppService", "entriesDeleted = " + addresses);

        }

        //好友在线状态改变
        @Override
        public void presenceChanged(Presence presence) {
            Log.i("XmppService", "presenceChanged = " + presence.toString());

        }
    }

    class MyConnectionListener implements ConnectionListener {

        //链接成功
        @Override
        public void connected(XMPPConnection connection) {
            Log.i("XmppService", "connected");
        }

        //登录成功
        @Override
        public void authenticated(XMPPConnection connection, boolean resumed) {
            Log.i("XmppService", "authenticated" + resumed);

        }

        //连接关系
        @Override
        public void connectionClosed() {
            Log.i("XmppService", "connectionClosed");

        }

        //连接报错
        @Override
        public void connectionClosedOnError(Exception e) {
            Log.i("XmppService", "connectionClosedOnError " + e.toString());

        }

        //重连成功
        @Override
        public void reconnectionSuccessful() {
            Log.i("XmppService", "reconnectionSuccessful");

        }

        //重连时间
        @Override
        public void reconnectingIn(int seconds) {
            Log.i("XmppService", "reconnectingIn = " + seconds);

        }

        //重连失败
        @Override
        public void reconnectionFailed(Exception e) {
            Log.i("XmppService", "reconnectionFailed = " + e.toString());

        }
    }

    class MyStanzaFilter implements StanzaFilter {

        @Override
        public boolean accept(Stanza stanza) {
            if (stanza instanceof Message)//拦截Message
                return true;
            return false;
        }
    }

    /**
     * Adds a new entry to the users Roster.
     *
     * @param jid      the jid.
     * @param nickname the nickname.
     * @param group    the contact group.
     * @return the new RosterEntry.
     */
    public void addEntry(String jid, String nickname, String group) {
        String[] groups = {group};
        try {
            Roster roster = Roster.getInstanceFor(XmppUtils.getInstance().getConnection());
            RosterEntry userEntry = roster.getEntry(jid);

            boolean isSubscribed = true;
            if (userEntry != null) {
                isSubscribed = userEntry.getGroups().size() == 0;
            }

            if (isSubscribed) {
                try {
                    roster.createEntry(jid, nickname, new String[]{group});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            RosterGroup rosterGroup = roster.getGroup(group);
            if (rosterGroup == null) {
                rosterGroup = roster.createGroup(group);
            }

            if (userEntry == null) {
                roster.createEntry(jid, nickname, groups);
                userEntry = roster.getEntry(jid);
            } else {
                userEntry.setName(nickname);
                rosterGroup.addEntry(userEntry);
            }

            userEntry = roster.getEntry(jid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
