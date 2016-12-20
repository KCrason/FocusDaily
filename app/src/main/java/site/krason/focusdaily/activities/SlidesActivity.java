package site.krason.focusdaily.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.bean.SlidesBean;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.fragments.SlidesFragment;

/**
 * @author Created by KCrason on 2016/12/20.
 * @email 535089696@qq.com
 */

public class SlidesActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private TextView mTxtDescription;
    private List<SlidesBean> mSlidesBeen;

    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_slides;
    }

    private SlidesFragmentPagerAdapter mSlidesFragmentPagerAdapter;

    @Override
    public void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);
        mTxtDescription = (TextView) findViewById(R.id.txt_description);
        mSlidesFragmentPagerAdapter = new SlidesFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSlidesFragmentPagerAdapter);
        if (getIntent() != null) {
            KNewBean.ItemBean kNewBean = (KNewBean.ItemBean) getIntent().getSerializableExtra(RecommendedFragment.KEY_NEWS);
            if (kNewBean != null && kNewBean.getLink() != null) {
                String url = kNewBean.getLink().getUrl();
                getSlidesImages(url);
            }
        }
    }

    private void getSlidesImages(String url) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (mSlidesFragmentPagerAdapter != null) {
                    JSONObject jsonObject = JSON.parseObject(response);
                    JSONObject jsonObjectBody = jsonObject.getJSONObject("body");
                    String title = jsonObjectBody.getString("title");
                    getSupportActionBar().setTitle(title);
                    mSlidesBeen = JSON.parseArray(jsonObjectBody.getString("slides"), SlidesBean.class);
                    if (mSlidesBeen != null) {
                        mSlidesFragmentPagerAdapter.setFragments(getFragments(mSlidesBeen.size(), mSlidesBeen));
                        mSlidesFragmentPagerAdapter.notifyDataSetChanged();
                        mTxtDescription.setText(1 + "/" + mSlidesBeen.size() + "  " + mSlidesBeen.get(0).getDescription());
                    }
                }
            }
        });
    }

    private List<Fragment> getFragments(int size, List<SlidesBean> slidesBeen) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fragments.add(SlidesFragment.instantce(slidesBeen.get(i)));
        }
        return fragments;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int sildesSize = mSlidesBeen.size();
        if (mSlidesBeen != null && position < sildesSize) {
            int curIndex = position + 1;
            mTxtDescription.setText(curIndex + "/" + sildesSize + "  " + mSlidesBeen.get(position).getDescription());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public final class SlidesFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public void setFragments(List<Fragment> fragments) {
            this.mFragments = fragments;
        }

        public SlidesFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments != null) {
                return mFragments.get(position);
            }
            return null;
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
