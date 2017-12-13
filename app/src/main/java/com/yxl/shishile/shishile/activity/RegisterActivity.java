package com.yxl.shishile.shishile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
         et02 = findViewById(R.id.et02);//邮箱
         et03 = findViewById(R.id.et03);//密码1
         et04 = findViewById(R.id.et04);//密码2
        zhuce.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.zhuce:

              if (isEmail(et02.getText().toString())==false)
              {
                  Toast.makeText(RegisterActivity.this, "请输入正确的Email地址", Toast.LENGTH_SHORT).show();
              }
              else if (et03.getText().toString().equals(et04.getText().toString())==false)
              {
                  Toast.makeText(RegisterActivity.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
              }
              else if (et01.getText().toString().equals(""))
              {
                  Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();

              }
              else if (et03.getText().toString().equals(""))
              {
                  Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
              }
              else
              {
                  Toast.makeText(RegisterActivity.this, "提交", Toast.LENGTH_SHORT).show();
              }





                //提交
                break;
        }
    }
    //正则表达式，判断是否为邮箱
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
}}