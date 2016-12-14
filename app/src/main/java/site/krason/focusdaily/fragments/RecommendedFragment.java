package site.krason.focusdaily.fragments;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.ScrollAdpter;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class RecommendedFragment extends BaseFragment implements OnRecyclerLoadMoreLisener {

    private KReyccleView mRecyclerView;

    private ScrollAdpter mScrollAdpter;


    @Override
    public void LazyLoadDataToService() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollAdpter.setData(getTitles());
                isLoadComplete = true;
                removeRootView();
            }
        }, 2000);
    }

    @Override
    public void LazyLoadDataToLocal() {

    }

    @Override
    public void initFragment(View view) {
        mRecyclerView = (KReyccleView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnRecyclerLoadMoreListener(this);
        mScrollAdpter = new ScrollAdpter(getContext());
        mRecyclerView.setAdapter(mScrollAdpter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            titles.add("推荐");
        }
        return titles;
    }

    private int page;

    @Override
    public void onRecyclerViewLoadMore() {
        if (KUtils.Network.isExistNetwork()) {
            if (page == 5) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setAllLoadComplete();
                    }
                }, 2000);
                return;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    page++;
                    mRecyclerView.setCurrentLoadComplete();
                    mScrollAdpter.addData(getTitles());
                }
            }, 2000);
        } else {
            mRecyclerView.setNetworkError();
        }
    }
}
