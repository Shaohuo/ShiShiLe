package com.yxl.shishile.shishile.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.activity.BPActivity;
import com.yxl.shishile.shishile.activity.MyWalletActivity;
import com.yxl.shishile.shishile.widgets.ImageViewPlus;

public class PersonFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_person, null);
        View  setting = view.findViewById(R.id.setting);
        View  kf = view.findViewById(R.id.kefu);
        View viewById = view.findViewById(R.id.chongzhi_button);
//        ImageViewPlus viewById = view.findViewById(R.id.imgplus);
//        viewById.setImageResource(R.mipmap.person2);
        View  bp = view.findViewById(R.id.bp);
        View  my = view.findViewById(R.id.my);
        View  sf = view.findViewById(R.id.sf);
        viewById.setOnClickListener(this);
        bp.setOnClickListener(this);
        my.setOnClickListener(this);
        sf.setOnClickListener(this);
        return view;
    }


    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.bp:
                //积分明细
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
                Intent intent1 = new Intent(view.getContext(), BPActivity.class);
                startActivity(intent1);
                break;
        }

    }
}
