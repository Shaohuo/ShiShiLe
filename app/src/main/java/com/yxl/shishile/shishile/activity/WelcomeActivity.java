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
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.app.AppDataManager;
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                    }
                }, 2000);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                    }
                }, 2000);
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
        String imei = telephonyManager.getDeviceId();
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
                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                        Toast.makeText(WelcomeActivity.this, "登录聊天室失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<PostEaseUserModel> call, Throwable t) {
                Toast.makeText(WelcomeActivity.this, "登录聊天室失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

