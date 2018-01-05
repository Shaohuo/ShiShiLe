package com.yxl.shishile.shishile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.adapter.InforAdapter;
import com.yxl.shishile.shishile.adapter.InformationAdapter;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.model.InformationModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {

    private ApiServer apiServer;
    private List<InformationModel.DataBean> informationList=new ArrayList<>();
    private ListView infor_list;
    private InforAdapter inforAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        infor_list =findViewById(R.id.infor_list);
        ImageView backImg = findViewById(R.id.back_img);
        backImg.setOnClickListener(this);
        inforAdapter = new InforAdapter(this, informationList);
        infor_list.setAdapter(inforAdapter);
        loadInformationData();
        infor_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(view.getContext(), InformationDetailActivity.class);
                intent1.putExtra("info", "http://103.242.1.48:81/article/detail/" + informationList.get(i).getId());
                startActivity(intent1);
            }
         });
    }

    /**
     * 热门资讯
     */
    private void loadInformationData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://103.242.1.48:81").addConverterFactory(GsonConverterFactory.create()).build();
        apiServer = retrofit.create(ApiServer.class);
        final Call<InformationModel> list = apiServer.getInfor();
        list.enqueue(new Callback<InformationModel>() {
            @Override
            public void onResponse(Call<InformationModel> call, Response<InformationModel> response) {
                InformationModel body = response.body();
                if (response.isSuccessful() && response.body() != null && body.getData() != null &&
                        body.getData().size() > 0) {
                    informationList.clear();
                    informationList.addAll(body.getData());
                    inforAdapter.notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<InformationModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_img:
                finish();
                break;
        }

    }
}
