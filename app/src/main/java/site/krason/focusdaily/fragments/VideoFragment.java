package site.krason.focusdaily.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.VideoListAdapter;
import site.krason.focusdaily.bean.VideoListBean;
import site.krason.focusdaily.common.UrlUtils;
import site.krason.focusdaily.utils.ACache;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/16.
 * @email 535089696@qq.com
 */

public class VideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerLoadMoreLisener {

    private View mRootView;


    private Map<String, String> getParams(int page) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("page", String.valueOf(page));
        stringMap.put("gv", "5.4.0");
        stringMap.put("av", "5.4.0");
        stringMap.put("uid", "863055036432979");
        stringMap.put("deviceid", "863055036432979");
        stringMap.put("proid", "ifengnews");
        stringMap.put("os", "android_22");
        stringMap.put("df", "androidphone");
        stringMap.put("vt", "5");
        stringMap.put("screen", "1080x1920");
        stringMap.put("publishid", "6102");
        stringMap.put("nw", "wifi");
        return stringMap;
    }

    private int mPageNow;

    private void refreshData() {
        OkHttpUtils.get().params(getParams(mPageNow)).url(UrlUtils.IFENG.IFENG_VIDEO_LIST).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Snackbar.make(mRootView, "推荐失败！", Snackbar.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response, int id) {
                ACache.get(getContext()).put("KEY_VIDEO_LIST", response);
                JSONArray jsonArray = JSON.parseArray(response);
                if (jsonArray.size() > 0) {
                    VideoListBean videoListBean = JSON.parseObject(jsonArray.getString(0), VideoListBean.class);
                    mVideoListAdapter.setData(videoListBean.getItem());
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private KReyccleView mRecyclerView;
    private VideoListAdapter mVideoListAdapter;

    private void initFragment(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#ed4040"));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRootView = view.findViewById(R.id.llayout_root);

        mRecyclerView = (KReyccleView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnRecyclerLoadMoreListener(this);
        mVideoListAdapter = new VideoListAdapter(getContext());
        mRecyclerView.setAdapter(mVideoListAdapter);

        refreshData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view);
    }

    @Override
    public void onRefresh() {
        mPageNow = 1;
        refreshData();
    }

    @Override
    public void onRecyclerViewLoadMore() {
        if (KUtils.Network.isExistNetwork()) {
            mPageNow++;
            OkHttpUtils.get().params(getParams(mPageNow))
                    .url(UrlUtils.IFENG.IFENG_VIDEO_LIST)
                    .build()
                    .execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                }

                @Override
                public void onResponse(String response, int id) {
                    mRecyclerView.setCurrentLoadComplete();
                    JSONArray jsonArray = JSON.parseArray(response);
                    if (jsonArray.size() > 0) {
                        VideoListBean videoListBean = JSON.parseObject(jsonArray.getString(0), VideoListBean.class);
                        mVideoListAdapter.addData(videoListBean.getItem());
                    }
                }
            });
        } else {
            mRecyclerView.setNetworkError();
        }
    }
}
