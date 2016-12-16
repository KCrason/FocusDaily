package site.krason.focusdaily.fragments;

import android.view.View;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2016/12/16.
 * @email 535089696@qq.com
 */

public class ImageFragment extends BaseFragment {
    @Override
    public void LazyLoadDataToService() {
        isLoadComplete = true;
        removeRootView();
    }

    @Override
    public void LazyLoadDataToLocal() {
        removeRootView();
    }

    @Override
    public void initFragment(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_image;
    }
}
