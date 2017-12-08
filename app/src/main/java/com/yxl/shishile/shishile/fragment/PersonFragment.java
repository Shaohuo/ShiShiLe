package com.yxl.shishile.shishile.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.activity.ForecastActivity;
import com.yxl.shishile.shishile.activity.BPActivity;
import com.yxl.shishile.shishile.activity.IntegraldetailActivity;
import com.yxl.shishile.shishile.activity.LoginActivity;
import com.yxl.shishile.shishile.activity.MyWalletActivity;
import com.yxl.shishile.shishile.model.MessageEvent;
import com.yxl.shishile.shishile.widgets.ImageViewPlus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class PersonFragment extends Fragment implements View.OnClickListener {

    private TextView nicknameTextView;
    private ImageViewPlus userlogo;
    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_person, null);
        View setting = view.findViewById(R.id.setting);
        View kf = view.findViewById(R.id.kefu);
        View chongzhi_button = view.findViewById(R.id.chongzhi_button);
        nicknameTextView = view.findViewById(R.id.nicknameTextView);
        userlogo = view.findViewById(R.id.userlogo);
        userlogo.setImageResource(R.mipmap.person1);
        View bp = view.findViewById(R.id.bp);
        View my = view.findViewById(R.id.my);
        View sf = view.findViewById(R.id.sf);
        chongzhi_button.setOnClickListener(this);
        bp.setOnClickListener(this);
        my.setOnClickListener(this);
        sf.setOnClickListener(this);
        kf.setOnClickListener(this);
        setting.setOnClickListener(this);
        nicknameTextView.setOnClickListener(this);
//        if (userlogo.getId() == R.mipmap.person1) {
//            nicknameTextView.setText("登录 / 注册");
//        }



//        Bundle bundle = getArguments();             //getArguments()获取Activity通过setArguments传递的值
//        if (bundle != null) {
//            String text = bundle.getString("userName");
//            nicknameTextView.setText(text);                   //将内容显示在textview上，完成传值
//        }

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        nicknameTextView.setText(event.getUserName());
        userlogo.setImageBitmap(event.getBitmap());
        /* Do something */};

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bp:
                //积分明细
                Intent intent2 = new Intent(getContext(), IntegraldetailActivity.class);
                startActivity(intent2);
                Toast.makeText(getContext(),"功能即将开放！尽请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.my:
                //我的钱包
                Intent intent = new Intent(view.getContext(), MyWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.sf:
                //我的收藏
                Toast.makeText(getContext(),"功能即将开放！尽请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.kefu:
                //客服
                Toast.makeText(getContext(),"功能即将开放！尽请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                //设置
                Toast.makeText(getContext(),"功能即将开放！尽请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.chongzhi_button:
                //充值
                Intent intent1 = new Intent(view.getContext(), BPActivity.class);
                startActivity(intent1);
                break;
            case R.id.nicknameTextView:
                Intent intent3 = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent3);
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
}
