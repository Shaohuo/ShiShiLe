package com.yxl.shishile.shishile.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.fragment.ChatFragment;
import com.yxl.shishile.shishile.fragment.ChatRoomFragment;
import com.yxl.shishile.shishile.fragment.OpenPrizeFragment;
import com.yxl.shishile.shishile.widgets.MZModeBannerFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private AlphaTabsIndicator alphaTabsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager mViewPger = (ViewPager) findViewById(R.id.mViewPager);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPger.setAdapter(mainAdapter);
        mViewPger.addOnPageChangeListener(mainAdapter);
        mViewPger.setOffscreenPageLimit(5);

        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        alphaTabsIndicator.setViewPager(mViewPger);

//        alphaTabsIndicator.getTabView(0).showNumber(6);
//        alphaTabsIndicator.getTabView(1).showNumber(888);
//        alphaTabsIndicator.getTabView(2).showNumber(88);
//        alphaTabsIndicator.getTabView(3).showPoint();
    }


    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();
//        private String[] titles = {"首页", "开奖", "预测"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new MZModeBannerFragment());
            fragments.add(new OpenPrizeFragment());
            fragments.add(new ChatRoomFragment());
            fragments.add(new OpenPrizeFragment());
//            fragments.add(new MZModeBannerFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            if (0 == position) {
//                alphaTabsIndicator.getTabView(0).showNumber(alphaTabsIndicator.getTabView(0).getBadgeNumber() - 1);
//            } else if (1 == position) {
//                alphaTabsIndicator.getCurrentItemView().removeShow();
//            } else if (2 == position) {
//                alphaTabsIndicator.removeAllBadge();
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
