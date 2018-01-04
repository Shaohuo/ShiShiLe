package com.yxl.shishile.shishile.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.app.Constant;
import com.yxl.shishile.shishile.model.MessageEvent;
import com.yxl.shishile.shishile.model.UserModel;
import com.yxl.shishile.shishile.util.UserSaveUtil;
import com.yxl.shishile.shishile.widgets.Util;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends SwipeBackActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1106575688";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private com.tencent.connect.UserInfo mUserInfo;
    //    public static ImageView userlogo;
    Bitmap bitmap = null;
    public static String nicknameTextView;
    private TextView zhuce;
    private EditText mEtUsername;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View back_img = findViewById(R.id.back_img);
        zhuce = findViewById(R.id.zhuce);
        zhuce.setOnClickListener(this);
        back_img.setOnClickListener(this);
        findViewById(R.id.denglu).setOnClickListener(this);
        mEtUsername = findViewById(R.id.et01);
        mEtPassword = findViewById(R.id.et02);
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID, LoginActivity.this.getApplicationContext());
    }

    public void buttonLogin(View v) {
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(LoginActivity.this, "all", mIUiListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.denglu:
                String username = mEtUsername.getText().toString();
                if ("".equals(username)) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = mEtPassword.getText().toString();
                if ("".equals(password)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Call<UserModel> call = ApiManager.getInstance().create(ApiServer.class)
                        .login(username, password);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel>
                            response) {
                        Toast.makeText(LoginActivity.this, "" + response.body().message,
                                Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful() && response.body() != null) {
                            if ("登录成功".equals(response.body().message)) {
                                UserModel.UserInfo data = response.body().getData();
                                UserSaveUtil.saveObject(LoginActivity.this, data);
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.back_img:
                finish();
                break;
            case R.id.zhuce:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, Constant.REQUEST_CODE_REGISTER);
        }
    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {

            //Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new com.tencent.connect.UserInfo(getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(final Object response) {
                        Log.e(TAG, "登录成功" + response.toString());
                        Message msg = new Message();
                        msg.obj = response;
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                        Log.e(TAG, "-----111---" + response.toString());
                        /**由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接
                         * 在mHandler里进行操作
                         *
                         */
                        new Thread() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                JSONObject json = (JSONObject) response;
                                try {
                                    bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);

                                MessageEvent messageEvent = new MessageEvent();
                                messageEvent.setUserName(nicknameTextView);
                                messageEvent.setBitmap(bitmap);
                                EventBus.getDefault().post(messageEvent);
                            }
                        }.start();
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG, "登录失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

            finish();
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            intent.putExtra("nicknameTextView",nicknameTextView);
//            intent.putExtra("bitmap",bitmap);
//            startActivity(intent);
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        nicknameTextView = response.getString("nickname");


                        Log.e(TAG, "--" + nicknameTextView);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 1) {
                bitmap = (Bitmap) msg.obj;
//                userlogo.setImageBitmap(bitmap);

            }
        }

    };

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }

        if (requestCode == Constant.REQUEST_CODE_REGISTER) {
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
