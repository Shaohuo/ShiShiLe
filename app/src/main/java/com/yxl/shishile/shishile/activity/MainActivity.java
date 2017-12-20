package com.yxl.shishile.shishile.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
//import com.umeng.message.PushAgent;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.fragment.ChatFragment;
import com.yxl.shishile.shishile.fragment.ChatRoomFragment;
import com.yxl.shishile.shishile.fragment.OpenPrizeFragment;
import com.yxl.shishile.shishile.fragment.PersonFragment;
import com.yxl.shishile.shishile.widgets.MZModeBannerFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private AlphaTabsIndicator alphaTabsIndicator;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setSessionContinueMillis(1000);
//        PushAgent.getInstance(this).onAppStart();

        ViewPager mViewPger = (ViewPager) findViewById(R.id.mViewPager);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPger.setAdapter(mainAdapter);
        mViewPger.addOnPageChangeListener(mainAdapter);
        mViewPger.setOffscreenPageLimit(5);

        username = getIntent().getStringExtra("nicknameTextView");
//        FragmentManager fm = getSupportFragmentManager();  //使用FragmentManager，管理Activity中的fragment
//        Bundle bundle = new Bundle();
//        bundle.putString("userName",username);
//        PersonFragment personFragment = new PersonFragment();
//        personFragment.setArguments(bundle);                                     //通过setArguments传值
//        fm.beginTransaction().replace(R.id.person_fragment,personFragment ).commit();        //通过add()将布局加入fragment容器中

        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        alphaTabsIndicator.setViewPager(mViewPger);


//        alphaTabsIndicator.getTabView(0).showNumber(6);
//        alphaTabsIndicator.getTabView(1).showNumber(888);
//        alphaTabsIndicator.getTabView(2).showNumber(88);
//        alphaTabsIndicator.getTabView(3).showPoint();
    }


    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new MZModeBannerFragment());
            fragments.add(new OpenPrizeFragment());
            fragments.add(new ChatRoomFragment());
            fragments.add(new PersonFragment());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onResume(){
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause(){
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
