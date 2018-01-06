package com.yxl.shishile.shishile.event;

import com.yxl.shishile.shishile.imchat.ChatMessageVo;

/**
 * Created by Administrator on 2018/1/6 0006.
 */

public class XmppGrounpChatMessageEvent {
    public ChatMessageVo chatMessageVo;

    public ChatMessageVo getChatMessageVo() {
        return chatMessageVo;
    }

    public void setChatMessageVo(ChatMessageVo chatMessageVo) {
        this.chatMessageVo = chatMessageVo;
    }
}
