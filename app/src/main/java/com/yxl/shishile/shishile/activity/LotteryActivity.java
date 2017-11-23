package com.yxl.shishile.shishile.activity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.model.Lottery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LotteryActivity extends Activity
{
    int index=1;
    HashMap<Integer, Lottery> mLotteryMaps = new HashMap<>();
    int[] mTvDataIds = new int[]{R.id.six_num_01, R.id.six_num_02, R.id.six_num_03, R.id.six_num_04, R.id.six_num_05, R.id.six_num_06,R.id.six_num_07};
    private TextView cycleId;
    private   long diff;
    private CountdownView mCvCountdownView;
    private TextView kj;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        mCvCountdownView = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
        cycleId = findViewById(R.id.cycle_id);
        kj = findViewById(R.id.KaiJiang);

        init();//获得时间差







    }
    public void init()
    {
        Call<Lottery>  call = ApiManager.getInstance().create(ApiServer.class).getLottery(index, "qzcx72trd7ax5w90");
        call.enqueue(new Callback<Lottery>()
        {

            @Override
            public void onResponse(Call<Lottery> call, Response<Lottery> response)
            {
                Lottery body = response.body();
                String[] split = body.data.split(",");
                String number = body.number;
                String time = body.time;
                //获取系统时间
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = sDateFormat.format(new java.util.Date());
                //获取北京时间
                SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
                String ee = dff.format(new Date());
                //计算时间差
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try
                {
                    Date  d1 = df.parse(ee);
                    Date  d2 = df.parse(time);
                    //Date   d2 = new   Date(System.currentTimeMillis());//你也可以获取当前时间
                    diff = d1.getTime() - d2.getTime();//这样得到的差值是毫秒级别
                }
                catch (Exception e)
                {
                }
                cycleId.setText("第"+number+"期");
                for (int i = 0; i < 7; i++) {
                    TextView mTvData = findViewById(mTvDataIds[i]);
                    if (i < split.length) {
                        mTvData.setText("" + split[i]);
                        mTvData.setVisibility(View.VISIBLE);
                    }else{
                        mTvData.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(Call<Lottery> call, Throwable t) {

            }
        });
    }



}
