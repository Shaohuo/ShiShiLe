package com.yxl.shishile.shishile.model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class MessageEvent {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap bitmap;
}
