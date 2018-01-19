package com.yxl.shishile.shishile.util;

import com.yxl.shishile.shishile.app.AppContext;
import com.yxl.shishile.shishile.imchat.ChatType;

import java.io.File;


/**
 * 应用相关文件帮助类
 * @author: laohu on 2017/1/18
 * @site: http://ittiger.cn
 */
public class AppFileHelper {

    public static String getAppRoot() {

        return AppContext.getInstance().getExternalCacheDir().getAbsolutePath();
    }

    public static String getAppImageCacheDir() {

        return getAppRoot() + "/image";
    }

    public static String getAppDBDir() {

        return getAppRoot() + "/db";
    }

    public static String getAppCrashDir() {

        return getAppRoot() + "/crash";
    }

    public static String getAppChatDir() {

        return getAppRoot() + "/chat";
    }

    public static File getAppChatMessageDir(int type) {

        String root = getAppChatDir();
        String child = "";
        if(type == ChatType.image.getId()) {
            child = "recv_image";
        } else if(type == ChatType.audio.getId()) {
            child = "recv_voice";
        }
        File file;
        if(ValueUtil.isEmpty(child)) {
            file = new File(root);
        } else {
            file = new File(root, child);
        }
        if(!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
