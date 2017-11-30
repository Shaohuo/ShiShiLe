package com.yxl.shishile.shishile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.activity.LotteryActivity;
import com.yxl.shishile.shishile.adapter.MyPrizeAdapter;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.model.Lottery;
import com.yxl.shishile.shishile.model.LotteryList;
import com.yxl.shishile.shishile.widgets.RecycleViewDivider;

import java.util.HashMap;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OpenPrizeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  OpenPrizeFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MyPrizeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;

    public OpenPrizeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenPrizeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpenPrizeFragment newInstance(String param1, String param2) {
        OpenPrizeFragment fragment = new OpenPrizeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_open_prize, container, false);


        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        mAdapter = new MyPrizeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyPrizeAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                Intent intent = new Intent(view.getContext(), LotteryActivity.class);
                intent.putExtra("index", position + 1);
                startActivity(intent);
            }
        });
        return view;
    }

    HashMap<Integer, Lottery> mLotteryMaps = new HashMap<>();

    public static int LOTTERY_LIST_COUNT = 11;

    @Override
    public void onResume() {
        super.onResume();
        mRefreshLayout.beginRefreshing();
    }

    private void loadPrizeListData() {
        mLotteryMaps.clear();
        for (int i = 0; i < 11; i++) {
            final int index = i + 1;
            Call<LotteryList> call = ApiManager.getInstance().create(ApiServer.class).getLotteryList(index, "qzcx72trd7ax5w90");
            call.enqueue(new Callback<LotteryList>() {
                @Override
                public void onResponse(Call<LotteryList> call, Response<LotteryList> response) {
                    mRefreshLayout.endRefreshing();
                    if (response.isSuccessful()) {
                        LotteryList body = response.body();
                        Log.e("OpenPrizeFragment", "" + index + " " + body);
//                        mLotteryMaps.put(index, body);
//                        if (mLotteryMaps.size() == LOTTERY_LIST_COUNT) {
//                            mRecyclerView.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mAdapter.setData(mLotteryMaps);
//                                    mAdapter.notifyDataSetChanged();
//                                    Log.e("OpenPrizeFragment", "" + mLotteryMaps);
//                                }
//                            });
//                        }
                    } else {
                        Log.e("OpenPrizeFragment", "unsuccess");
                    }
                }

                @Override
                public void onFailure(Call<LotteryList> call, Throwable t) {
                    Log.e("OpenPrizeFragment", "" + t.getMessage());
                    mRefreshLayout.endRefreshing();
                }
            });
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadPrizeListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

//    @Override
//    public void onClick(View view) {
//
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
