package com.yxl.shishile.shishile.event;

/**
 * Created by Administrator on 2018/1/6 0006.
 */

public class XmppRegisterEvent {
    public String mAction = "";

    public XmppRegisterEvent(String action) {
        this.mAction = action;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        this.mAction = action;
    }
}
