package site.krason.focusdaily.activities;

import android.support.v4.view.ViewPager;

import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.PreviewImagesAdapter;

/**
 * @author Created by KCrason on 2016/12/30.
 * @email 535089696@qq.com
 */

public class PreviewImagesActivty extends BaseActivity {

    private ViewPager mViewPager;

    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_images;
    }


    @Override
    public void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        String urls[] = getIntent().getStringArrayExtra("key_urls");
        int curPosition = getIntent().getIntExtra("key_position", -1);
        PreviewImagesAdapter previewImagesAdapter = new PreviewImagesAdapter(this, urls);
        mViewPager.setAdapter(previewImagesAdapter);
        if (curPosition >= 0) {
            mViewPager.setCurrentItem(curPosition);
        }
    }
}
