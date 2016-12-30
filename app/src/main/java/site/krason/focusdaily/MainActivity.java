package site.krason.focusdaily;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.activities.WebViewActivity;
import site.krason.focusdaily.fragments.ImageFragment;
import site.krason.focusdaily.fragments.JokeFragment;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.fragments.VideoFragment;
import site.krason.focusdaily.utils.ViewHolderManage;

public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private int mCurPosition;

    private LinearLayout mTemperature;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ViewHolderManage.create().createVideoPlay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mTemperature = (LinearLayout) findViewById(R.id.llayout_temperature);
        mTemperature.setOnClickListener(this);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);
        KFragmentPagerAdapter kFragmentPagerAdapter = new KFragmentPagerAdapter(getSupportFragmentManager());
        kFragmentPagerAdapter.setFragments(getFragments());
        kFragmentPagerAdapter.setTitles(getTitles());
        mViewPager.setAdapter(kFragmentPagerAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(RecommendedFragment.instance("focus"));
        fragments.add(new VideoFragment());
        fragments.add(RecommendedFragment.instance("world"));
        fragments.add(RecommendedFragment.instance("social"));
        fragments.add(RecommendedFragment.instance("digital"));
        fragments.add(RecommendedFragment.instance("nba"));
        fragments.add(RecommendedFragment.instance("movie"));
        fragments.add(RecommendedFragment.instance("game"));
        fragments.add(JokeFragment.instance("joke"));
        fragments.add(JokeFragment.instance("yulu"));
        fragments.add(new ImageFragment());
        return fragments;
    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("焦点");
        titles.add("视频");
        titles.add("国际");
        titles.add("社会");
        titles.add("数码");
        titles.add("NBA");
        titles.add("电影");
        titles.add("游戏");
        titles.add("笑话");
        titles.add("语录");
        titles.add("图片");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llayout_temperature:
                WebViewActivity.actionStart(this, "https://e.newweather.com.cn/");
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
