package site.krason.focusdaily;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import site.krason.focusdaily.activities.ChannelEditorActivity;
import site.krason.focusdaily.bean.ChannelBean;
import site.krason.focusdaily.common.Constants;
import site.krason.focusdaily.events.NotifyChannelRefresh;
import site.krason.focusdaily.fragments.ImageFragment;
import site.krason.focusdaily.fragments.JokeOrQuotationsFragment;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.sortdrag.IDragEntity;
import site.krason.focusdaily.utils.ACache;
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


    private KFragmentPagerAdapter kFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        setData();


        mImageViewAdd = (ImageView) findViewById(R.id.img_add);
        mImageViewAdd.setOnClickListener(this);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);
        kFragmentPagerAdapter = new KFragmentPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(kFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


        kFragmentPagerAdapter.setFragments(getFragments());
        kFragmentPagerAdapter.setTitles(getTitles());
        kFragmentPagerAdapter.notifyDataSetChanged();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshChannelList(NotifyChannelRefresh notifyChannelRefresh) {
        if (notifyChannelRefresh.getEventCode() == Constants.EVENT_CODE_SUCCESS && notifyChannelRefresh.getData().equals("LONGCLICK")) {
            List<String> stringList = getTitles();
            kFragmentPagerAdapter.setTitles(stringList);
            kFragmentPagerAdapter.setFragments(getFragments());
            kFragmentPagerAdapter.notifyDataSetChanged();
        } else {
            mViewPager.setCurrentItem(Integer.parseInt(notifyChannelRefresh.getData()));
        }
    }


    private List<Fragment> getFragments() {
        List<Fragment> mFragmentList = new ArrayList<>();
        List<IDragEntity> channelBeanList = new ArrayList<>();
        channelBeanList.addAll(JSON.parseArray(ACache.get(this).getAsString(Constants.MY_CHANNEL_LIST), ChannelBean.class));
        for (IDragEntity iDragEntity : channelBeanList) {
            String channelName = ((ChannelBean) iDragEntity).getChannelName();
            if (channelName != null) {
                if (channelName.equals("头条")) {
                    mFragmentList.add(RecommendedFragment.instance("头条"));
                } else if (channelName.equals("推荐")) {
                    mFragmentList.add(RecommendedFragment.instance("推荐"));
                } else if (channelName.equals("社会")) {
                    mFragmentList.add(RecommendedFragment.instance("社会"));
                } else if (channelName.equals("数码")) {
                    mFragmentList.add(RecommendedFragment.instance("数码"));
                } else if (channelName.equals("国际")) {
                    mFragmentList.add(RecommendedFragment.instance("国际"));
                } else if (channelName.equals("游戏")) {
                    mFragmentList.add(RecommendedFragment.instance("游戏"));
                } else if (channelName.equals("电影")) {
                    mFragmentList.add(RecommendedFragment.instance("电影"));
                } else if (channelName.equals("NBA")) {
                    mFragmentList.add(RecommendedFragment.instance("NBA"));
                } else if (channelName.equals("科技")) {
                    mFragmentList.add(RecommendedFragment.instance("科技"));
                } else if (channelName.equals("娱乐")) {
                    mFragmentList.add(RecommendedFragment.instance("娱乐"));
                } else if (channelName.equals("军事")) {
                    mFragmentList.add(RecommendedFragment.instance("军事"));
                } else if (channelName.equals("历史")) {
                    mFragmentList.add(RecommendedFragment.instance("历史"));
                } else if (channelName.equals("时尚")) {
                    mFragmentList.add(RecommendedFragment.instance("时尚"));
                } else if (channelName.equals("暖新闻")) {
                    mFragmentList.add(RecommendedFragment.instance("暖新闻"));
                } else if (channelName.equals("教育")) {
                    mFragmentList.add(RecommendedFragment.instance("教育"));
                } else if (channelName.equals("港澳")) {
                    mFragmentList.add(RecommendedFragment.instance("港澳"));
                } else if (channelName.equals("家居")) {
                    mFragmentList.add(RecommendedFragment.instance("家居"));
                } else if (channelName.equals("台湾")) {
                    mFragmentList.add(RecommendedFragment.instance("台湾"));
                } else if (channelName.equals("时政")) {
                    mFragmentList.add(RecommendedFragment.instance("时政"));
                } else if (channelName.equals("文化")) {
                    mFragmentList.add(RecommendedFragment.instance("文化"));
                } else if (channelName.equals("跑步")) {
                    mFragmentList.add(RecommendedFragment.instance("跑步"));
                } else if (channelName.equals("星座")) {
                    mFragmentList.add(RecommendedFragment.instance("星座"));
                } else if (channelName.equals("读书")) {
                    mFragmentList.add(RecommendedFragment.instance("读书"));
                } else if (channelName.equals("健康")) {
                    mFragmentList.add(RecommendedFragment.instance("健康"));
                } else if (channelName.equals("青年")) {
                    mFragmentList.add(RecommendedFragment.instance("青年"));
                } else if (channelName.equals("识装")) {
                    mFragmentList.add(RecommendedFragment.instance("识装"));
                } else if (channelName.equals("智库")) {
                    mFragmentList.add(RecommendedFragment.instance("智库"));
                } else if (channelName.equals("公益")) {
                    mFragmentList.add(RecommendedFragment.instance("公益"));
                } else if (channelName.equals("美女")) {
                    mFragmentList.add(JokeOrQuotationsFragment.instance("美女"));
                } else if (channelName.equals("财经")) {
                    mFragmentList.add(RecommendedFragment.instance("财经"));
                } else if (channelName.equals("体育")) {
                    mFragmentList.add(RecommendedFragment.instance("体育"));
                } else if (channelName.equals("萌物")) {
                    mFragmentList.add(JokeOrQuotationsFragment.instance("萌物"));
                } else if (channelName.equals("旅游")) {
                    mFragmentList.add(RecommendedFragment.instance("旅游"));
                } else if (channelName.equals("直播")) {
                    mFragmentList.add(RecommendedFragment.instance("直播"));
                } else if (channelName.equals("评论")) {
                    mFragmentList.add(RecommendedFragment.instance("评论"));
                } else if (channelName.equals("政能量")) {
                    mFragmentList.add(RecommendedFragment.instance("政能量"));
                } else if (channelName.equals("文化")) {
                    mFragmentList.add(RecommendedFragment.instance("文化"));
                } else if (channelName.equals("国学")) {
                    mFragmentList.add(RecommendedFragment.instance("国学"));
                } else if (channelName.equals("房产")) {
                    mFragmentList.add(RecommendedFragment.instance("房产"));
                } else if (channelName.equals("汽车")) {
                    mFragmentList.add(RecommendedFragment.instance("汽车"));
                } else if (channelName.equals("语录")) {
                    mFragmentList.add(JokeOrQuotationsFragment.instance("语录"));
                } else if (channelName.equals("段子")) {
                    mFragmentList.add(JokeOrQuotationsFragment.instance("段子"));
                } else if (channelName.equals("图片")) {
                    mFragmentList.add(ImageFragment.instance("图片"));
                }
            }
        }
        return mFragmentList;
    }


    private List<String> getTitles() {
        List<ChannelBean> channelBeanList = JSON.parseArray(ACache.get(this).getAsString(Constants.MY_CHANNEL_LIST), ChannelBean.class);
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < channelBeanList.size(); i++) {
            titles.add(channelBeanList.get(i).getChannelName());
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
            "头条", "推荐", "科技", "娱乐", "财经", "国际", "社会", "美女", "军事"
    };

    private String channelNames2[] = new String[]{
            "国学", "台湾", "文化", "时政", "星座", "读书", "游戏", "青年", "健康", "暖新闻", "公益", "教育",
            "电影", "港澳", "家居", "语录", "评论", "政能量", "跑步", "智库", "历史", "NBA", "图片", "体育", "汽车", "时尚", "房产", "段子", "萌物", "旅游", "数码"
    };


    private void setData() {
        String isExistLocalData = ACache.get(this).getAsString(Constants.IS_EXIST_LOCAL_DATA);
        if (TextUtils.isEmpty(isExistLocalData)) {
            List<IDragEntity> mMyIDragEntities = new ArrayList<>();
            List<IDragEntity> mRecommendIDragEntities = new ArrayList<>();
            for (int i = 0; i < channelNames.length; i++) {
                ChannelBean channelBean = new ChannelBean();
                channelBean.setId(i);
                channelBean.setChannelName(channelNames[i]);
                mMyIDragEntities.add(channelBean);
            }
            ACache.get(this).put(Constants.MY_CHANNEL_LIST, JSON.toJSONString(mMyIDragEntities));

            for (int i = 0; i < channelNames2.length; i++) {
                ChannelBean channelBean = new ChannelBean();
                channelBean.setId(i + mMyIDragEntities.size());
                channelBean.setChannelName(channelNames2[i]);
                mRecommendIDragEntities.add(channelBean);
            }
            ACache.get(this).put(Constants.RECOMMEND_CHANNEL_LIST, JSON.toJSONString(mRecommendIDragEntities));
            ACache.get(this).put(Constants.IS_EXIST_LOCAL_DATA, "TRUE");
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

        int id = 1;
        private Map<String, Integer> IdsMap = new HashMap<>();
        private List<String> preIds = new ArrayList<>();

        private List<String> mTitles = new ArrayList<>();

        private List<Fragment> mFragments = new ArrayList<>();

        public KFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setTitles(List<String> titles) {
            this.mTitles.clear();
            this.mTitles = titles;
        }

        public void setFragments(List<Fragment> fragments) {
            this.mFragments.clear();
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return IdsMap.get(getPageTitle(position));
        }

        @Override
        public int getItemPosition(Object object) {
            Fragment fragment = (Fragment) object;
            String title = "";
            if (fragment instanceof RecommendedFragment) {
                title = ((RecommendedFragment) fragment).getKeyType();
            } else if (fragment instanceof ImageFragment) {
                title = ((RecommendedFragment) fragment).getKeyType();
            } else if (fragment instanceof JokeOrQuotationsFragment) {
                title = ((RecommendedFragment) fragment).getKeyType();
            }
            int preId = preIds.indexOf(title);
            int newId = -1;
            int i = 0;
            int size = getCount();
            for (; i < size; i++) {
                if (getPageTitle(i).equals(title)) {
                    newId = i;
                    break;
                }
            }
            if (newId != -1 && newId == preId) {
                return POSITION_UNCHANGED;
            }
            if (newId != -1) {
                return newId;
            }
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }


        @Override
        public void notifyDataSetChanged() {
            for (String title : mTitles) {
                if (!IdsMap.containsKey(title)) {
                    IdsMap.put(title, id++);
                }
            }
            super.notifyDataSetChanged();
            preIds.clear();
            int size = getCount();
            for (int i = 0; i < size; i++) {
                preIds.add((String) getPageTitle(i));
            }
        }


        @Override
        public int getCount() {
            return mTitles.size();
        }
    }
}
