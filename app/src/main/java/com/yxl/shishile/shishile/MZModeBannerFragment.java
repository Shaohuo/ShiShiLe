package com.yxl.shishile.shishile;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.yxl.shishile.shishile.R;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;



public class MZModeBannerFragment extends ListFragment {
    public static final String TAG = "MZModeBannerFragment";
    public static final int []BANNER = new int[]{R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3,R.mipmap.banner4,R.mipmap.banner5};
    private MZBannerView mMZBanner;
    //    private ListView topicListView;
//    private TopicListViewAdapter topicListViewAdapter;
//    private List<TopicListViewItem.MenuitemBean> menuitemBeans = new ArrayList<>();
    private boolean isViewPrepared=false;//是否初始化完成



    public static MZModeBannerFragment newInstance(){
        return new MZModeBannerFragment();
    }

    private void initView(View view) {

        mMZBanner = (MZBannerView) view.findViewById(R.id.banner);
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getContext(),"click page:"+position,Toast.LENGTH_LONG).show();
            }
        });
        mMZBanner.addPageChangeLisnter(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG,"----->addPageChangeLisnter:"+position + "positionOffset:"+positionOffset+ "positionOffsetPixels:"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG,"addPageChangeLisnter:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<Integer> bannerList = new ArrayList<>();
        for(int i=0;i<BANNER.length;i++){
            bannerList.add(BANNER[i]);
        }
        mMZBanner.setIndicatorVisible(true);
        mMZBanner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

//        mNormalBanner = (MZBannerView) view.findViewById(R.id.banner_normal);
//        mNormalBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
//            @Override
//            public BannerViewHolder createViewHolder() {
//                return new BannerViewHolder();
//            }
//        });

//        topicListView=view.findViewById(android.R.id.list);
//        topicListViewAdapter = new TopicListViewAdapter(getContext(), menuitemBeans);

    }

//    private void initData() {
//        // topicListView = getListView();
//        // topicListView.setTextFilterEnabled(true);
//        // topicListView = view.findViewById(R.id.topic_listview);
////添加数据到listview中
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.url).addConverterFactory(GsonConverterFactory.create()).build();
//        ApiService apiService = retrofit.create(ApiService.class);
//        final Call<TopicListViewItem> list = apiService.getTopic_json();
//        list.enqueue(new Callback<TopicListViewItem>() {
//            @Override
//            public void onResponse(Call<TopicListViewItem> call, Response<TopicListViewItem> response) {
//                menuitemBeans.addAll(response.body().getMenuitem());
//                topicListView.setAdapter(topicListViewAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<TopicListViewItem> call, Throwable t) {
//                Toast.makeText(getContext(), "aaaaaa", Toast.LENGTH_LONG).show();
//            }
//
//        });
//    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mzmode_banner_fragment,null);

        initView(view);
        if (!isViewPrepared&&getUserVisibleHint()) {//尚未初始化view,不能执行initData()方法[会报空指针]
//
//            initData();
//            topicListViewAdapter.notifyDataSetChanged();

        }
        isViewPrepared=true;//isViewPrepared判断和赋值位置不能变,考虑setUserVisibleHint更新数据
        return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        // 判断该Fragment时候已经正在前台显示，就可以知道什么时候去加载数据了
        if (isVisibleToUser && isViewPrepared)
        {
//            menuitemBeans.clear();
//            initData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();
        //mNormalBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
        // mNormalBanner.start();
    }
}

