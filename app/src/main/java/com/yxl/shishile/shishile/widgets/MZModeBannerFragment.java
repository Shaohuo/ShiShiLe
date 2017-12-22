package com.yxl.shishile.shishile.widgets;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.yxl.shishile.shishile.activity.LotteryActivity;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.activity.M_ForecastActivity;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class
MZModeBannerFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "MZModeBannerFragment";
    public static final int[] BANNER = new int[]{R.mipmap.banner_1, R.mipmap.banner_2,R.mipmap.banner_3};
    private MZBannerView mMZBanner;
    private final List<String> datas = Arrays.asList("恭喜用户：建东 预测成功！奖励积分100", "恭喜用户：阿志 预测成功！奖励积分100", "恭喜用户：阿彪 预测成功！奖励积分100", "恭喜用户：小郭 预测成功！奖励积分100");
    private SimpleMarqueeView marqueeView;
    private boolean isViewPrepared=false;//是否初始化完成
    NestedScrollView mNestedScrollView;
    TextView mainText;

    public static MZModeBannerFragment newInstance() {
        return new MZModeBannerFragment();
    }

    private void initView(View view) {

        marqueeView = view.findViewById(R.id.marqueeView);
        LinearLayout chongqingLiner = view.findViewById(R.id.chongqing);
        LinearLayout hubeiLiner = view.findViewById(R.id.hubei);
        LinearLayout liuhecaiLiner = view.findViewById(R.id.liuhecai);
        LinearLayout guangdongLiner = view.findViewById(R.id.guangdong);
        LinearLayout fucaiLiner = view.findViewById(R.id.fucai);
        LinearLayout pailieLiner = view.findViewById(R.id.pailie);
        LinearLayout xinjiangLiner = view.findViewById(R.id.xinjiang);
        LinearLayout jiangsuLiner = view.findViewById(R.id.jiangsu);
        LinearLayout jiangxiLiner = view.findViewById(R.id.jiangxi);
        LinearLayout beijingLiner = view.findViewById(R.id.beijing);
        LinearLayout shandongLiner = view.findViewById(R.id.shandong);
        ScrollDisabledListView infor_list = view.findViewById(R.id.information_list);
        View main_forecast = view.findViewById(R.id.main_forecast);

        /**设置下拉隐藏标题栏*/
        mainText = view.findViewById(R.id.main_title);
        mNestedScrollView = (NestedScrollView)view.findViewById(R.id.nested_scroll_view);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int headerHeight = mMZBanner.getHeight();
                int scrollDistance = Math.min(scrollY, headerHeight);
                int statusAlpha = (int) ((float) scrollDistance / (float) headerHeight * 255F);
                setAnyBarAlpha(statusAlpha);
            }
        });
        setAnyBarAlpha(0);

        chongqingLiner.setOnClickListener(this);
        hubeiLiner.setOnClickListener(this);
        liuhecaiLiner.setOnClickListener(this);
        guangdongLiner.setOnClickListener(this);
        fucaiLiner.setOnClickListener(this);
        pailieLiner.setOnClickListener(this);
        xinjiangLiner.setOnClickListener(this);
        jiangsuLiner.setOnClickListener(this);
        jiangxiLiner.setOnClickListener(this);
        beijingLiner.setOnClickListener(this);
        shandongLiner.setOnClickListener(this);
        main_forecast.setOnClickListener(this);

        initMarqueeView();

        SimpleAdapter adapter = new SimpleAdapter(getContext(),getData(),R.layout.item_information,
                new String[]{"title","info"},
                new int[]{R.id.title,R.id.info});
        infor_list.setAdapter(adapter);
        fixListViewHeight(infor_list);


        mMZBanner = (MZBannerView) view.findViewById(R.id.banner);
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                if (position == 0) {
                    Toast.makeText(getContext(), "点击跳转a", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getContext(), "点击跳转b", Toast.LENGTH_SHORT).show();
                } else if (position == 2) {
                    Toast.makeText(getContext(), "点击跳转c", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {
                    Toast.makeText(getContext(), "点击跳转d", Toast.LENGTH_SHORT).show();
                } else if (position == 4) {
                    Toast.makeText(getContext(), "点击跳转e", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mMZBanner.addPageChangeLisnter(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "----->addPageChangeLisnter:" + position + "positionOffset:" + positionOffset + "positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "addPageChangeLisnter:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<Integer> bannerList = new ArrayList<>();
        for (int i = 0; i < BANNER.length; i++) {
            bannerList.add(BANNER[i]);
        }
        mMZBanner.setIndicatorVisible(true);
        mMZBanner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    public void fixListViewHeight(ListView infor_list) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        int i = -1;
        ListAdapter listAdapter = infor_list.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); i < len; i++) {
            View listViewItem = listAdapter.getView(index , null, infor_list);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = infor_list.getLayoutParams();
//        infor_list.getDividerHeight();//获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight+ (infor_list.getDividerHeight() * (listAdapter.getCount() + 1));
        infor_list.setLayoutParams(params);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("title", "时时彩投注技巧之观冷定胆");
        map1.put("info", "时时彩共设“星彩玩法”和“大小单双玩法”，玩法简单");
        list.add(map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("title", "小伙2元即中720万大奖");
        map2.put("info", "他的方法竟如此简单，100%中奖");
        list.add(map2);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("title", "福彩3D选和值把握好这两点");
        map3.put("info", "一千个彩民就有一千种选号方法，但是总会有几种方法是");
        list.add(map3);

        return list;
    }

    private void setAnyBarAlpha(int alpha) {
        if (alpha > 0 && alpha < 100){
            mainText.setText("");
        }else if (alpha >= 100 && alpha <255){
            mainText.setText("快赢彩票");
        }
        mainText.getBackground().mutate().setAlpha(alpha);
    }


/**
     * 跑马灯数据初始化
     */
    private void initMarqueeView() {
        SimpleMF<String> marqueeFactory = new SimpleMF(getContext());
        marqueeFactory.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
                Toast.makeText(getContext(), holder.data, Toast.LENGTH_SHORT).show();
//                Intent intent1 = new Intent(getContext(), LotteryActivity.class);
//                startActivity(intent1);
            }
        });
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), LotteryActivity.class);
        switch (view.getId()) {
            case R.id.chongqing:
                intent.putExtra("index", 1);
                startActivity(intent);
                break;
            case R.id.hubei:
                intent.putExtra("index", 2);
                startActivity(intent);
                break;
            case R.id.liuhecai:
                intent.putExtra("index", 3);
                startActivity(intent);
                break;
            case R.id.guangdong:
                intent.putExtra("index", 4);
                startActivity(intent);
                break;
            case R.id.fucai:
                intent.putExtra("index", 5);
                startActivity(intent);
                break;
            case R.id.pailie:
                intent.putExtra("index", 6);
                startActivity(intent);
                break;
            case R.id.xinjiang:
                intent.putExtra("index", 7);
                startActivity(intent);
                break;
            case R.id.jiangsu:
                intent.putExtra("index", 8);
                startActivity(intent);
                break;
            case R.id.jiangxi:
                intent.putExtra("index", 9);
                startActivity(intent);
                break;
            case R.id.beijing:
                intent.putExtra("index", 10);
                startActivity(intent);
                break;
            case R.id.shandong:
                intent.putExtra("index", 11);
                startActivity(intent);
                break;
            case R.id.main_forecast:
             Intent intent1 = new Intent(view.getContext(), M_ForecastActivity.class);
                startActivity(intent1);
                break;
        }

    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
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
        View view = inflater.inflate(R.layout.activity_mzmode_banner_fragment, null);

        initView(view);
        if (!isViewPrepared && getUserVisibleHint()) {//尚未初始化view,不能执行initData()方法[会报空指针]
//
//            initData();
//            topicListViewAdapter.notifyDataSetChanged();

        }
        isViewPrepared = true;//isViewPrepared判断和赋值位置不能变,考虑setUserVisibleHint更新数据
        return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        // 判断该Fragment时候已经正在前台显示，就可以知道什么时候去加载数据了
        if (isVisibleToUser && isViewPrepared) {
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

