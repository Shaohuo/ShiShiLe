package com.yxl.shishile.shishile.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.activity.ForecastActivity;
import com.yxl.shishile.shishile.activity.BPActivity;
import com.yxl.shishile.shishile.activity.IntegraldetailActivity;
import com.yxl.shishile.shishile.activity.LoginActivity;
import com.yxl.shishile.shishile.activity.MainActivity;
import com.yxl.shishile.shishile.activity.MyWalletActivity;
import com.yxl.shishile.shishile.app.Constant;
import com.yxl.shishile.shishile.model.MessageEvent;
import com.yxl.shishile.shishile.model.PostUserModel;
import com.yxl.shishile.shishile.util.ObjectSaveUtil;
import com.yxl.shishile.shishile.widgets.ImageViewPlus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class PersonFragment extends Fragment implements View.OnClickListener {

    private TextView nicknameTextView;
    private ImageViewPlus userlogo;
    private TextView mTvLogout;
    private  View view;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_person, null);
        View setting = view.findViewById(R.id.setting);
        View kf = view.findViewById(R.id.kefu);
        View chongzhi_button = view.findViewById(R.id.chongzhi_button);
        nicknameTextView = view.findViewById(R.id.nicknameTextView);
        userlogo = view.findViewById(R.id.userlogo);
        userlogo.setImageResource(R.mipmap.person1);
        View bp = view.findViewById(R.id.bp);
        View my = view.findViewById(R.id.my);
        View sf = view.findViewById(R.id.sf);
        View jifen_duihuan = view.findViewById(R.id.jifen_duihuan);
        chongzhi_button.setOnClickListener(this);
        jifen_duihuan.setOnClickListener(this);
        bp.setOnClickListener(this);
        my.setOnClickListener(this);
        sf.setOnClickListener(this);
        kf.setOnClickListener(this);
        setting.setOnClickListener(this);
        view.findViewById(R.id.ll_user).setOnClickListener(this);
//        if (userlogo.getId() == R.mipmap.person1) {
//            nicknameTextView.setText("登录 / 注册");
//        }
        mTvLogout = view.findViewById(R.id.tvLogout);
        mTvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nicknameTextView.setText("登录 / 注册");
                showNormalDialog();

            }
        });

//        Bundle bundle = getArguments();             //getArguments()获取Activity通过setArguments传递的值
//        if (bundle != null) {
//            String text = bundle.getString("userName");
//            nicknameTextView.setText(text);                   //将内容显示在textview上，完成传值
//        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        PostUserModel.DataBean userModel = (PostUserModel.DataBean) ObjectSaveUtil.readObject
                (getContext());
        if (userModel != null) {
            nicknameTextView.setText("" + userModel.getNickname());
            mTvLogout.setVisibility(View.VISIBLE);
        } else {
            mTvLogout.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        nicknameTextView.setText(event.getUserName());
        userlogo.setImageBitmap(event.getBitmap());
        /* Do something */
    }

    ;

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bp:
                //积分明细
                Toast.makeText(getContext(), "功能即将开放！尽请期待", Toast.LENGTH_SHORT).show();
                /*Intent intent2 = new Intent(getContext(), IntegraldetailActivity.class);
                startActivity(intent2);*/
                break;
            case R.id.my:
                //我的钱包
                Toast.makeText(getContext(), "功能即将开放！尽请期待", Toast.LENGTH_SHORT).show();
              /*  Intent intent = new Intent(view.getContext(), MyWalletActivity.class);
                startActivity(intent);*/
                break;
            case R.id.sf:
                //我的收藏
                Toast.makeText(getContext(), "功能即将开放！尽请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kefu:
                //客服
                Toast.makeText(getContext(), "功能即将开放！尽请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                //设置
                Toast.makeText(getContext(), "功能即将开放！尽请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.chongzhi_button:
                //充值
                Toast.makeText(getContext(), "功能即将开放！尽请期待", Toast.LENGTH_SHORT).show();
             /*   Intent intent1 = new Intent(view.getContext(), BPActivity.class);
                startActivity(intent1);*/
                break;
            case R.id.jifen_duihuan:
                //充值
                Toast.makeText(getContext(), "功能即将开放！尽请期待", Toast.LENGTH_SHORT).show();
             /*   Intent intent1 = new Intent(view.getContext(), BPActivity.class);
                startActivity(intent1);*/
                break;
            case R.id.ll_user:
                PostUserModel.DataBean userModel = (PostUserModel.DataBean) ObjectSaveUtil
                        .readObject
                                (getContext());
                if (userModel == null) {
                    Intent intent3 = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent3);
                }
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(view.getContext());
        //normalDialog.setIcon(R.drawable.icon_002);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否退出当前账号");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ObjectSaveUtil.saveObject(getContext(), null);
                        mTvLogout.setVisibility(View.INVISIBLE);
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}

