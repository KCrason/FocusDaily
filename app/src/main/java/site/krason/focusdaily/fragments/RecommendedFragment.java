package site.krason.focusdaily.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.ScrollAdpter;
import site.krason.focusdaily.widgets.recyclerview.HeaderView;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class RecommendedFragment extends Fragment implements OnRecyclerLoadMoreLisener {

    private KReyccleView mRecyclerView;

    private ScrollAdpter mScrollAdpter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (KReyccleView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHeaderView(new HeaderView(getContext()));
        mRecyclerView.setOnRecyclerLoadMoreListener(this);

        mScrollAdpter = new ScrollAdpter(getContext());
        mRecyclerView.setAdapter(mScrollAdpter);
        mScrollAdpter.setData(getTitles());
    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            titles.add("推荐");
        }
        return titles;
    }


    @Override
    public void onRecyclerViewLoadMore() {
        Log.d("Krason","loadmore");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollAdpter.addData(getTitles());
                mRecyclerView.setCurrentLoadComplete();
            }
        },2000);
    }
}
