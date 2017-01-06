package site.krason.focusdaily;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.activities.ChannelEditorActivity;
import site.krason.focusdaily.bean.ChannelBean;
import site.krason.focusdaily.database.KDataBaseHelper;
import site.krason.focusdaily.fragments.ImageFragment;
import site.krason.focusdaily.fragments.JokeFragment;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.utils.ViewHolderManage;

public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private int mCurPosition;

    private LinearLayout mTemperature;

    private ImageView mImageViewAdd;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ViewHolderManage.create().stopVideoPlay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();


        mImageViewAdd = (ImageView) findViewById(R.id.img_add);
        mImageViewAdd.setOnClickListener(this);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);
        KFragmentPagerAdapter kFragmentPagerAdapter = new KFragmentPagerAdapter(getSupportFragmentManager());
        kFragmentPagerAdapter.setFragments(getFragments());
        kFragmentPagerAdapter.setTitles(getTitles());
        mViewPager.setAdapter(kFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        List<ChannelBean> channelBeanList = KDataBaseHelper.form().getAllChannel(new String[]{"1"});
        for (ChannelBean channelBean : channelBeanList) {
            String channelName = channelBean.getChannelName();
            if (channelName != null) {
                if (channelName.equals("头条")) {
                    fragments.add(RecommendedFragment.instance("focus"));
                } else if (channelName.equals("推荐")) {
                    fragments.add(RecommendedFragment.instance("recommend"));
                } else if (channelName.equals("社会")) {
                    fragments.add(RecommendedFragment.instance("social"));
                } else if (channelName.equals("数码")) {
                    fragments.add(RecommendedFragment.instance("digital"));
                } else if (channelName.equals("国际")) {
                    fragments.add(RecommendedFragment.instance("world"));
                } else if (channelName.equals("语录")) {
                    fragments.add(JokeFragment.instance("yulu"));
                } else if (channelName.equals("段子")) {
                    fragments.add(JokeFragment.instance("joke"));
                } else if (channelName.equals("游戏")) {
                    fragments.add(RecommendedFragment.instance("game"));
                } else if (channelName.equals("电影")) {
                    fragments.add(RecommendedFragment.instance("movie"));
                } else if (channelName.equals("NBA")) {
                    fragments.add(RecommendedFragment.instance("nba"));
                } else if (channelName.equals("图片")) {
                    fragments.add(new ImageFragment());
                } else {
                    fragments.add(RecommendedFragment.instance(channelName));
                }
            }
        }
        return fragments;
    }

    private List<String> getTitles() {
        List<ChannelBean> channelBeanList = KDataBaseHelper.form().getAllChannel(new String[]{"1"});
        List<String> titles = new ArrayList<>();
        for (ChannelBean channelBean : channelBeanList) {
            titles.add(channelBean.getChannelName());
        }
        return titles;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.mCurPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private String channelNames[] = new String[]{
            "头条", "推荐", "科技", "娱乐", "财经", "国际", "社会", "美女", "军事",
            "图片", "体育", "汽车", "时尚", "房产", "段子", "萌物", "旅游", "数码",
    };

    private String channelNames2[] = new String[]{
            "国学", "台湾", "彩票", "文化", "时政", "未来", "星座", "读书",
            "游戏", "青年", "健康", "暖新闻", "公益", "教育", "电影",
            "港澳", "家居", "语录", "直播", "评论", "佛教", "小说",
            "政能量", "跑步", "智库", "酒业", "历史", "NBA"
    };

    private void setData() {
        if (KDataBaseHelper.form().getCount() <= 0) {
            for (int i = 0; i < channelNames.length; i++) {
                ChannelBean channelBean = new ChannelBean();
                channelBean.setIsRecommend(1);
                channelBean.setChannelName(channelNames[i]);
                KDataBaseHelper.form().insertData(channelBean);
            }

            for (int i = 0; i < channelNames2.length; i++) {
                ChannelBean channelBean = new ChannelBean();
                channelBean.setIsRecommend(0);
                channelBean.setChannelName(channelNames2[i]);
                KDataBaseHelper.form().insertData(channelBean);
            }
        }
    }


    @Override

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_add:
                ChannelEditorActivity.actionStart(this);
                break;
        }
    }


    public final class KFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<String> mTitles;

        private List<Fragment> mFragments;

        public KFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setTitles(List<String> titles) {
            this.mTitles = titles;
        }

        public void setFragments(List<Fragment> fragments) {
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments != null) {
                return mFragments.get(position);
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTitles != null) {
                return mTitles.get(position);
            }
            return super.getPageTitle(position);
        }

        @Override
        public int getCount() {
            if (mFragments != null) {
                return mFragments.size();
            }
            return 0;
        }
    }
}
