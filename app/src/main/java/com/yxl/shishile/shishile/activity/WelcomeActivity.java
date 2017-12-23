package com.yxl.shishile.shishile.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.model.PostEaseUserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);

        AndPermission.with(WelcomeActivity.this)
                .requestCode(100)
                .permission(Permission.STORAGE, Permission.CAMERA, Permission.PHONE)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale
                            rationale) {
                        AndPermission.rationaleDialog(WelcomeActivity.this, rationale).show();
                    }


                }).callback(new PermissionListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                getEaseUerInfo();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                    }
                }, 500);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                getEaseUerInfo();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                    }
                }, 500);
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getEaseUerInfo() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission
                .READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context
                .TELEPHONY_SERVICE);
        String imei = telephonyManager.getImei();
        Call<PostEaseUserModel> call = ApiManager.getInstance().create(ApiServer
                .class).getEaseUser(imei);
        call.enqueue(new Callback<PostEaseUserModel>() {
            @Override
            public void onResponse(Call<PostEaseUserModel> call,
                                   Response<PostEaseUserModel> response) {
                Log.e("WelcomeAct", "" + response.body().getData().toString());
                if (response.isSuccessful() && response.body() != null && response.body().getData
                        () != null) {
                    String username = response.body().getData().getUsername();
                    String password = response.body().getData().getHx_password();
                    EMClient.getInstance().login("" + username, "" + password, new EMCallBack()
                    {//回调
                        @Override
                        public void onSuccess() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                    Log.d("main", "登录聊天服务器成功！");
                                }
                            });
                        }

                        @Override
                        public void onProgress(int progress, String status) {
                            Toast.makeText(WelcomeActivity.this, "加载聊天室服务器"+progress+"%", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(int code, String message) {
                            Toast.makeText(WelcomeActivity.this, "登录聊天室失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<PostEaseUserModel> call, Throwable t) {
                Toast.makeText(WelcomeActivity.this, "登录聊天室失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

