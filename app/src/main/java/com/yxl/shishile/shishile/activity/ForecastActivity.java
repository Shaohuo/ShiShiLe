package com.yxl.shishile.shishile.activity;

import android.os.Bundle;

import com.yxl.shishile.shishile.R;

public class ForecastActivity extends BaseActivity {

//    int[] mTvDataIds = new int[]{R.id.six_num_01, R.id.six_num_02, R.id.six_num_03, R.id.six_num_04, R.id.six_num_05, R.id.six_num_06, R.id.six_num_07};
//    List<historyBean.MenuitemBean> menuitemBeans = new ArrayList<>();
//    private ListView listView;
//    private TextView qishu;
//    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

//        qishu = findViewById(R.id.title_text);
//        ImageView logoimg = findViewById(R.id.logo_image);
//        listView = findViewById(R.id.history_list);
//        LoadHistoryData();
    }

//    private void LoadHistoryData() {
//        Call<Lottery> list = ApiManager.getInstance().create(ApiServer.class).getLottery(1, "qzcx72trd7ax5w90");
//        list.enqueue(new Callback<Lottery>() {
//
//            @Override
//            public void onResponse(Call<Lottery> call, Response<Lottery> response) {
//                Lottery body = response.body();
//                //发送倒计时事件
//                OpenCountDownEvent countDownEvent = new OpenCountDownEvent();
//                countDownEvent.setLottery(body);
//                EventBus.getDefault().post(countDownEvent);
//                String[] split = body.data.split(",");
//                String number = body.number;
//                qishu.setText("第" + number + "期");
//                String time = body.time;
//                for (int i = 0; i < 7; i++) {
//                    TextView mTvData = findViewById(mTvDataIds[i]);
//                    if (i < split.length) {
//                        mTvData.setText("" + split[i]);
//                        mTvData.setVisibility(View.VISIBLE);
//                    } else {
//                        mTvData.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Lottery> call, Throwable t) {
//
//            }
//        });
//    }
}
