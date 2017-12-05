package com.yxl.shishile.shishile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;

public class BPActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView money_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);
        RadioButton bp_r1 = findViewById(R.id.bp_radio_1);
        RadioButton bp_r2 = findViewById(R.id.bp_radio_2);
        RadioButton bp_r3 = findViewById(R.id.bp_radio_3);
        Button weixin_pay = findViewById(R.id.weixin_pay);
        Button zhifu_pay = findViewById(R.id.zhifu_pay);
        money_num = findViewById(R.id.money_num);
        money_num.setText("");
        weixin_pay.setOnClickListener(this);
        zhifu_pay.setOnClickListener(this);
        bp_r1.setOnClickListener(this);
        bp_r2.setOnClickListener(this);
        bp_r3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
        case R.id.bp_radio_1:
            money_num.setText("10元");
                break;
            case R.id.bp_radio_2:
                money_num.setText("30元");
                break;
            case R.id.bp_radio_3:
                money_num.setText("50元");
                break;
            case R.id.weixin_pay:
                if (money_num.getText()=="")
                {
                    Toast.makeText(this, "请选择充值金额", Toast.LENGTH_SHORT).show();
                }else//微信支付金额不为空
                    {
                        Toast.makeText(this, "微信支付~马上开放"+money_num.getText(), Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.zhifu_pay:
                if (money_num.getText()=="")
                {
                    Toast.makeText(this, "请选择充值金额", Toast.LENGTH_SHORT).show();
                }else//支付宝支付金额不为空
                    {
                        Toast.makeText(this, "支付宝支付~马上开放"+money_num.getText(), Toast.LENGTH_SHORT).show();
                    }
                break;
        }


    }
}
