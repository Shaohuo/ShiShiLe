package com.yxl.shishile.shishile.event;

import com.yxl.shishile.shishile.model.UserModel;

/**
 * Created by Administrator on 2018/1/6 0006.
 */

public class XmppLoginEvent {

    public String mAction = "";

    public UserModel.UserInfo userInfo;

    public XmppLoginEvent(String action) {
        this.mAction = action;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        this.mAction = action;
    }

    public UserModel.UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserModel.UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
