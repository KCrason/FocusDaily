package site.krason.focusdaily.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import me.shaohui.bottomdialog.BottomDialog;
import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.RecommendSlideBean;
import site.krason.focusdaily.bean.SlidesBean;
import site.krason.focusdaily.fragments.GalleryRecommendFragment;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.fragments.SlidesFragment;
import site.krason.focusdaily.interfaces.OnImageClickListener;
import site.krason.focusdaily.widgets.GalleryDescriptionTextView;

/**
 * @author Created by KCrason on 2016/12/20.
 * @email 535089696@qq.com
 */

public class SlidesActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        OnImageClickListener, View.OnClickListener {

    private ViewPager mViewPager;
    private GalleryDescriptionTextView mTxtDescription;
    private List<SlidesBean> mSlidesBeen;
    private List<RecommendSlideBean> mRecommendSlideBeen;

    private ImageView mImageBack, mImageMore;
    private TextView mTxtTitle;

    private RelativeLayout mRelativeLayoutTitleBar;

    private String mGalleryTitle;

    private boolean isShowTiltleAndDescrition = true;


    private SlidesFragmentPagerAdapter mSlidesFragmentPagerAdapter;

    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_slides;
    }

    @Override
    public void initViews() {
        mRelativeLayoutTitleBar = (RelativeLayout) findViewById(R.id.rlayout_title_bar);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mImageBack = (ImageView) findViewById(R.id.img_back);
        mImageMore = (ImageView) findViewById(R.id.img_more);
        mImageBack.setOnClickListener(this);
        mImageMore.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(this);
        mTxtDescription = (GalleryDescriptionTextView) findViewById(R.id.txt_description);
        mTxtDescription.setMovementMethod(ScrollingMovementMethod.getInstance());
        mSlidesFragmentPagerAdapter = new SlidesFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSlidesFragmentPagerAdapter);
        if (getIntent() != null) {
            String url = getIntent().getStringExtra(RecommendedFragment.KEY_NEWS);
            if (!TextUtils.isEmpty(url)) {
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
                    mGalleryTitle = jsonObjectBody.getString("title");
                    if (mTxtTitle != null) {
                        mTxtTitle.setText(mGalleryTitle);
                    }
                    mSlidesBeen = JSON.parseArray(jsonObjectBody.getString("slides"), SlidesBean.class);
                    mRecommendSlideBeen = JSON.parseArray(jsonObjectBody.getString("recommend"), RecommendSlideBean.class);

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
        fragments.add(GalleryRecommendFragment.instantce(mRecommendSlideBeen));
        return fragments;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == mSlidesBeen.size() - 1) {

        }
    }

    @Override
    public void onPageSelected(int position) {
        int sildesSize = mSlidesBeen.size();
        if (mSlidesBeen != null && position < sildesSize) {
            int curIndex = position + 1;
            mTxtDescription.setScrollToTop();
            mTxtDescription.setText(curIndex + "/" + sildesSize + "  " + mSlidesBeen.get(position).getDescription());
        }

        if (position >= sildesSize) {
            mTxtTitle.setText("推荐图片");
            mImageMore.setVisibility(View.GONE);
            if (mRelativeLayoutTitleBar.getVisibility() == View.GONE) {
                visible(mRelativeLayoutTitleBar, -mRelativeLayoutTitleBar.getHeight(), 0, true);
            }
            if (mTxtDescription.getVisibility() == View.VISIBLE) {
                visible(mTxtDescription, 0, mTxtDescription.getHeight(), false);
            }
        } else {
            mImageMore.setVisibility(View.VISIBLE);
            mTxtTitle.setText(mGalleryTitle);
            if (isShowTiltleAndDescrition) {
                if (mRelativeLayoutTitleBar.getVisibility() == View.GONE) {
                    visible(mRelativeLayoutTitleBar, -mRelativeLayoutTitleBar.getHeight(), 0, true);
                }
                if (mTxtDescription.getVisibility() == View.GONE) {
                    visible(mTxtDescription, mTxtDescription.getHeight(), 0, true);
                }
            } else {
                if (mRelativeLayoutTitleBar.getVisibility() == View.VISIBLE) {
                    visible(mRelativeLayoutTitleBar, 0, -mRelativeLayoutTitleBar.getHeight(), false);
                }

                if (mTxtDescription.getVisibility() == View.VISIBLE) {
                    visible(mTxtDescription, 0, mTxtDescription.getHeight(), false);
                }
            }
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onImageClick(View view) {
        if (mRelativeLayoutTitleBar.getVisibility() == View.VISIBLE) {
            isShowTiltleAndDescrition = false;
            visible(mRelativeLayoutTitleBar, 0, -mRelativeLayoutTitleBar.getHeight(), false);
        } else {
            isShowTiltleAndDescrition = true;
            visible(mRelativeLayoutTitleBar, -mRelativeLayoutTitleBar.getHeight(), 0, true);
        }
        if (mTxtDescription.getVisibility() == View.VISIBLE) {
            visible(mTxtDescription, 0, mTxtDescription.getHeight(), false);
        } else {
            visible(mTxtDescription, mTxtDescription.getHeight(), 0, true);
        }
    }


    private void visible(final View view, int startY, int endY, final boolean isVisible) {
        TranslateAnimation translateAnimation;
        translateAnimation = new TranslateAnimation(0, 0, startY, endY);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isVisible) {
                    view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isVisible) {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(translateAnimation);
    }

    private BottomDialog mBottomDialog;

    @Override
    public void onClick(View view) {
        if (mBottomDialog != null) {
            mBottomDialog.dismiss();
        }
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.llayout_weixin:
                Toast.makeText(this, "微信", Toast.LENGTH_SHORT).show();
                break;
            case R.id.llayout_moments:
                Toast.makeText(this, "朋友圈", Toast.LENGTH_SHORT).show();
                break;
            case R.id.llayout_qq:
                Toast.makeText(this, "qq", Toast.LENGTH_SHORT).show();
                break;
            case R.id.llayout_qzone:
                Toast.makeText(this, "qq空间", Toast.LENGTH_SHORT).show();
                break;
            case R.id.llayout_other:
                Toast.makeText(this, "其他", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_more:
                mBottomDialog = BottomDialog.create(getSupportFragmentManager());
                mBottomDialog.setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        view.findViewById(R.id.llayout_weixin).setOnClickListener(SlidesActivity.this);
                        view.findViewById(R.id.llayout_moments).setOnClickListener(SlidesActivity.this);
                        view.findViewById(R.id.llayout_qq).setOnClickListener(SlidesActivity.this);
                        view.findViewById(R.id.llayout_qzone).setOnClickListener(SlidesActivity.this);
                        view.findViewById(R.id.llayout_other).setOnClickListener(SlidesActivity.this);
                    }
                }).setLayoutRes(R.layout.dialog_show).show();
                break;
        }
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
