package com.yxl.shishile.shishile.imchat;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 显示对话框
 * <p>Title: DialogUtils.java</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>E-mail: jacen@wscnydx.com</p>
 *
 * @author wsc
 * @version 1.0
 * @date 2015-3-18 下午10:12:19
 */
public class JacenDialogUtils {

    private static ProgressDialog mDialog;

    public static void showDialog(Context mContext, String msg) {
        dismissDialog();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage(msg);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public static boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        }
        return false;
    }

    public static void dismissDialog() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
