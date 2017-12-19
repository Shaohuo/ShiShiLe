package com.yxl.shishile.shishile.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

/**
 * Created by Administrator on 2017/12/4 0004.
 * 因为集成了Bugly热修复，MyApplication废弃转而使用SampleApplication
 *
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

}