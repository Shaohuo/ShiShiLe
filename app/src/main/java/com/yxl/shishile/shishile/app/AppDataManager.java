package com.yxl.shishile.shishile.app;

import com.yxl.shishile.shishile.model.ChatRoomModel;

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

    public List<ChatRoomModel> mChatRoomList = new ArrayList<>();

    private AppDataManager() {

    }

    public void setChatRoomList(List<ChatRoomModel> list) {
        mChatRoomList = list;
    }

    public List<ChatRoomModel> getChatRoomList() {
        return mChatRoomList;
    }
}
