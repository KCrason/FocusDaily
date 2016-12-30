package site.krason.focusdaily.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import site.krason.focusdaily.widgets.recyclerview.MultiStatusView;

/**
 * @author Created by KCrason on 2016/12/14.
 * @email 535089696@qq.com
 */

public abstract class BaseFragment extends Fragment {


    public boolean isLoadComplete;

    public MultiStatusView mMultiStatusView;

    public boolean isViewPrepare;

    private boolean mIsVisibleToUser;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisibleToUser = true;
            onVisible();
        } else {
            mIsVisibleToUser = false;
        }
    }

    public void removeRootView() {
        if (mMultiStatusView != null && mMultiStatusView.getChildCount() >= 2) {
            mMultiStatusView.setIsCanRefresh(true);
            mMultiStatusView.cleanAnimation();
            mMultiStatusView.removeViewAt(1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() != 0) {
            View view = inflater.inflate(getLayoutId(), container, false);
            mMultiStatusView = new MultiStatusView(getContext());
            mMultiStatusView.addBaseView(view).addRootView(getContext());
            return mMultiStatusView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepare = true;
        initFragment(view);
        onVisible();
    }

    private void onVisible() {
        if (mIsVisibleToUser && isViewPrepare) {
            if (isLoadComplete) {
                LazyLoadDataToLocal();
            } else {
                LazyLoadDataToService();
            }
        }
    }

    public abstract void LazyLoadDataToService();

    public abstract void LazyLoadDataToLocal();

    public abstract void initFragment(View view);

    public abstract int getLayoutId();
}
