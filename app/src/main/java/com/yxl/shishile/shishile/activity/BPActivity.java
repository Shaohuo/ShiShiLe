package com.yxl.shishile.shishile.activity;

import android.graphics.ImageFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;

public class BPActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView money_num;
    private EditText moeny_pay;
    private ImageButton bp_r1;
    private ImageButton bp_r2;
    private ImageButton bp_r3;
    private ImageButton other_btn;
    private TextView fuhao;
    private ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);
        bp_r1 = findViewById(R.id.bp_radio_1);
        bp_r2 = findViewById(R.id.bp_radio_2);
        bp_r3 = findViewById(R.id.bp_radio_3);
        fuhao = findViewById(R.id.fuhao);
        back_img = findViewById(R.id.back_img);
        other_btn  = findViewById(R.id.other_money);
        Button weixin_pay = findViewById(R.id.weixin_pay);
        Button zhifu_pay = findViewById(R.id.zhifu_pay);
        money_num = findViewById(R.id.money_num);
        moeny_pay = findViewById(R.id.money_pay);
        moeny_pay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (b)
                {
                    bp_r1.setSelected(false);
                    bp_r2.setSelected(false);
                    bp_r3.setSelected(false);
                }
            }
        });
        moeny_pay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                    money_num.setText(charSequence+"元");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        money_num.setText("");
        weixin_pay.setOnClickListener(this);
        zhifu_pay.setOnClickListener(this);
        bp_r1.setOnClickListener(this);
        bp_r2.setOnClickListener(this);
        bp_r3.setOnClickListener(this);
        other_btn.setOnClickListener(this);//其他金额
        back_img.setOnClickListener(this);//finsh()

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
        case R.id.bp_radio_1:
            fuhao.setVisibility(View.GONE);
            moeny_pay.setVisibility(View.GONE);
            bp_r1.setSelected(true);
            bp_r2.setSelected(false);
            bp_r3.setSelected(false);
            other_btn.setSelected(false);
            moeny_pay.setText("100");
                break;
            case R.id.bp_radio_2:
                fuhao.setVisibility(View.GONE);
                moeny_pay.setVisibility(View.GONE);
                bp_r1.setSelected(false);
                bp_r2.setSelected(true);
                bp_r3.setSelected(false);
                other_btn.setSelected(false);
                moeny_pay.setText("300");
                break;
            case R.id.bp_radio_3:
                fuhao.setVisibility(View.GONE);
                moeny_pay.setVisibility(View.GONE);
                bp_r1.setSelected(false);
                bp_r2.setSelected(false);
                bp_r3.setSelected(true);
                other_btn.setSelected(false);
                moeny_pay.setText("500");
                break;
            case R.id.weixin_pay:
                        Toast.makeText(this, "微信支付~马上开放"+money_num.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.zhifu_pay:
                        Toast.makeText(this, "支付宝支付~马上开放"+money_num.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.other_money:
                bp_r1.setSelected(false);
                bp_r2.setSelected(false);
                bp_r3.setSelected(false);
                other_btn.setSelected(true);
                fuhao.setVisibility(View.VISIBLE);
                moeny_pay.setVisibility(View.VISIBLE);
                break;
            case R.id.back_img:
                finish();
                break;
        }


    }
}
