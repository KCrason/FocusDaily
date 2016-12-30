package site.krason.focusdaily.fragments;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.ImageAdpter;
import site.krason.focusdaily.bean.ImageListBean;
import site.krason.focusdaily.common.UrlUtils;
import site.krason.focusdaily.utils.ACache;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/16.
 * @email 535089696@qq.com
 */

public class ImageFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerLoadMoreLisener {

    private View mRootView;

    @Override
    public void LazyLoadDataToService() {
        refreshData();
    }

    private Map<String, String> getParams(int page) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("channel", "news");
        stringMap.put("page", String.valueOf(page));
        stringMap.put("pagesize", String.valueOf(20));
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


    private int mPageNow = 1;

    private void refreshData() {
        OkHttpUtils.get().params(getParams(mPageNow)).url(UrlUtils.IFENG.GET_PIC_LIST).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Snackbar.make(mRootView, "推荐失败！", Snackbar.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response, int id) {
                ACache.get(getContext()).put("KEY_IMAGE_LIST", response);
                JSONObject jsonObject = JSON.parseObject(response);
                if (jsonObject.containsKey("body")) {
                    ImageListBean imageListBean = JSON.parseObject(jsonObject.getString("body"), ImageListBean.class);
                    mImageAdpter.setData(imageListBean.getItem());
                    isLoadComplete = true;
                    removeRootView();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }


    private SwipeRefreshLayout mSwipeRefreshLayout;

    private KReyccleView mRecyclerView;

    private ImageAdpter mImageAdpter;

    @Override
    public void LazyLoadDataToLocal() {
        String cache = ACache.get(getContext()).getAsString("KEY_IMAGE_LIST");
        JSONObject jsonObject = JSON.parseObject(cache);
        if (jsonObject.containsKey("body")) {
            ImageListBean imageListBean = JSON.parseObject(jsonObject.getString("body"), ImageListBean.class);
            mImageAdpter.setData(imageListBean.getItem());
        }
        removeRootView();
    }

    @Override
    public void initFragment(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#ed4040"));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRootView = view.findViewById(R.id.llayout_root);

        mRecyclerView = (KReyccleView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setOnRecyclerLoadMoreListener(this);
        mImageAdpter = new ImageAdpter(getContext());
        mRecyclerView.setAdapter(mImageAdpter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_image;
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
            OkHttpUtils.get().params(getParams(mPageNow)).url(UrlUtils.IFENG.GET_PIC_LIST).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                }

                @Override
                public void onResponse(String response, int id) {
                    mRecyclerView.setCurrentLoadComplete();
                    JSONObject jsonObject = JSON.parseObject(response);
                    if (jsonObject.containsKey("body")) {
                        ImageListBean imageListBean = JSON.parseObject(jsonObject.getString("body"), ImageListBean.class);
                        mImageAdpter.addData(imageListBean.getItem());
                    }
                }
            });
        } else {
            mRecyclerView.setNetworkError();
        }
    }
}
