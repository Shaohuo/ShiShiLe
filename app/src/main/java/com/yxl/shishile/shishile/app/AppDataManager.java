package com.yxl.shishile.shishile.app;

import com.hyphenate.chat.EMChatRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class AppDataManager {
    private static final AppDataManager ourInstance = new AppDataManager();

    public static AppDataManager getInstance() {
        return ourInstance;
    }

    public List<EMChatRoom> mEMChatRoomList = new ArrayList<>();

    private AppDataManager() {

    }

    public void setEMChatRoomList(List<EMChatRoom> list) {
        mEMChatRoomList = list;
    }

    public List<EMChatRoom> getEMChatRoomList() {
        return mEMChatRoomList;
    }
}
