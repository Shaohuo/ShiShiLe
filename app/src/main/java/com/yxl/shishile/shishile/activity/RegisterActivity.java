package com.yxl.shishile.shishile.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.imchat.JacenDialogUtils;
import com.yxl.shishile.shishile.imchat.JacenUtils;
import com.yxl.shishile.shishile.imchat.XmppAction;
import com.yxl.shishile.shishile.imchat.XmppService;
import com.yxl.shishile.shishile.imchat.XmppUtils;
import com.yxl.shishile.shishile.model.PostRegisterUserModel;
import com.yxl.shishile.shishile.model.UserModel;
import com.yxl.shishile.shishile.util.UserSaveUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et01;
    private EditText et02;
    private EditText et03;
    private EditText et04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageView back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        Button zhuce = findViewById(R.id.zhuce);
        et01 = findViewById(R.id.et01);//用户名
        //  et02 = findViewById(R.id.et02);//邮箱
        et03 = findViewById(R.id.et03);//密码1
        et04 = findViewById(R.id.et04);//密码2
        final   View user_view = findViewById(R.id.view_user);
        final   View view_password_1 = findViewById(R.id.view_password_1);
        final   View view_password_2 = findViewById(R.id.view_password_2);
        zhuce.setOnClickListener(this);
        et01.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    user_view.setBackgroundResource(R.drawable.border_liner);

                } else {
                    // 此处为失去焦点时的处理内容
                    user_view.setBackgroundResource(R.drawable.border_noliner);
                }
            }
        });
        et03.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    view_password_1.setBackgroundResource(R.drawable.border_liner);

                } else {
                    // 此处为失去焦点时的处理内容
                    view_password_1.setBackgroundResource(R.drawable.border_noliner);
                }
            }
        });
        et04.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    view_password_2.setBackgroundResource(R.drawable.border_liner);

                } else {
                    // 此处为失去焦点时的处理内容
                    view_password_2.setBackgroundResource(R.drawable.border_noliner);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.zhuce:

                final String username = et01.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String password = et03.getText().toString();
                if (password.equals("")) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!et03.getText().toString().equals(et04.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
                    return;
                }
                JacenDialogUtils.showDialog(this, "注册中...");
                Bundle bundle = new Bundle();
                bundle.putString("account", username);
                bundle.putString("password", password);
                JacenUtils.intentService(this, XmppService.class, XmppAction.ACTION_REGISTER,
                        bundle);
                IntentFilter mIntentFilter = new IntentFilter();
                mIntentFilter.addAction(XmppAction.ACTION_REGISTER_SUCCESS);
                mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR);
                mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR_CONFLICT);
                mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR_FORBIDDEN);
                mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR_JID_MALFORMED);
                mIntentFilter.addAction(XmppAction.ACTION_SERVICE_ERROR);
                JacenUtils.registerLocalBroadcastReceiver(this, new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent != null && XmppAction.ACTION_REGISTER_SUCCESS.equals(intent
                                .getAction())) {
                            register(username, password);
                        } else if (XmppAction.ACTION_REGISTER_ERROR_CONFLICT.equals(intent
                                .getAction())) {
                            JacenDialogUtils.dismissDialog();
                            Toast.makeText(RegisterActivity.this, "该用户已存在", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            JacenDialogUtils.dismissDialog();
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }, mIntentFilter);

                break;
        }
    }

    private void register(final String username, final String password) {
        Call<PostRegisterUserModel> call = ApiManager.getInstance().create(ApiServer
                .class).register(username, password);
        call.enqueue(new Callback<PostRegisterUserModel>() {
            @Override
            public void onResponse(Call<PostRegisterUserModel> call,
                                   Response<PostRegisterUserModel>
                                           response) {
                JacenDialogUtils.dismissDialog();
                if (response.isSuccessful()) {
                    String message = response.body().message;
                    Toast.makeText(RegisterActivity.this, "" + message, Toast
                            .LENGTH_SHORT).show();
                    if ("注册成功".equals(message)) {
                        login(username, password);
                    }
                }
            }

            @Override
            public void onFailure(Call<PostRegisterUserModel> call, Throwable t) {
                JacenDialogUtils.dismissDialog();
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void login(String username, String password) {
        Call<UserModel> call = ApiManager.getInstance().create(ApiServer.class)
                .login(username, password);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel>
                    response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    UserModel.UserInfo data = response.body().getData();
                    UserSaveUtil.saveObject(RegisterActivity.this, data);
                    Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //正则表达式，判断是否为邮箱
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}