package site.krason.focusdaily.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.JokeAdapter;
import site.krason.focusdaily.bean.ShortNewsBean;
import site.krason.focusdaily.common.UrlUtils;
import site.krason.focusdaily.utils.ACache;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRealItemClickCallBack;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/16.
 * @email 535089696@qq.com
 */

public class JokeFragment extends BaseFragment implements OnRecyclerLoadMoreLisener
        , OnRealItemClickCallBack<ShortNewsBean>, SwipeRefreshLayout.OnRefreshListener {

    private KReyccleView mRecyclerView;

    private JokeAdapter mJokeAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private View mRootView;


    @Override
    public void LazyLoadDataToService() {
        refreshData();
    }

    private Map<String, String> getParams(int page) {
        Map<String, String> stringMap = new HashMap<>();
        String type = "joke";
        if (mType != null) {
            if (mType.equals("joke")) {
                type = "joke";
            } else if (mType.equals("yulu")) {
                type = "phil";
            }
        }
        stringMap.put("type", type);
        stringMap.put("page", String.valueOf(page));
        stringMap.put("ltime", String.valueOf(0));
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(KEY_TYPE);
    }

    private final static String KEY_TYPE = "key_type";
    private String mType;

    public static JokeFragment instance(String type) {
        JokeFragment jokeFragment = new JokeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        jokeFragment.setArguments(bundle);
        return jokeFragment;
    }

    private String getKey() {
        String key = "KEY_JOKE";
        if (mType != null) {
            if (mType.equals("joke")) {
                key = "KEY_JOKE";
            } else if (mType.equals("yulu")) {
                key = "KEY_YULU";
            }
        }
        return key;
    }

    private int mPageNow = 1;

    private void refreshData() {
        OkHttpUtils.get().params(getParams(mPageNow)).url(UrlUtils.IFENG.CLIENT_SHORT_NEWS).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Snackbar.make(mRootView, "推荐失败！", Snackbar.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response, int id) {
                ACache.get(getContext()).put(getKey(), response);
                JSONObject jsonObject = JSON.parseObject(response);
                if (jsonObject.containsKey("body")) {
                    List<ShortNewsBean> shortNewsBeen = JSON.parseArray(jsonObject.getString("body"), ShortNewsBean.class);
                    mJokeAdapter.setData(shortNewsBeen);
                    isLoadComplete = true;
                    removeRootView();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });


    }

    @Override
    public void LazyLoadDataToLocal() {
        String cache = ACache.get(getContext()).getAsString(getKey());
        JSONObject jsonObject = JSON.parseObject(cache);
        if (jsonObject.containsKey("body")) {
            List<ShortNewsBean> shortNewsBeen = JSON.parseArray(jsonObject.getString("body"), ShortNewsBean.class);
            mJokeAdapter.setData(shortNewsBeen);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnRecyclerLoadMoreListener(this);
        mJokeAdapter = new JokeAdapter(getContext(), this);
        mRecyclerView.setAdapter(mJokeAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_joke;
    }


    @Override
    public void onRecyclerViewLoadMore() {
        if (KUtils.Network.isExistNetwork()) {
            mPageNow++;
            OkHttpUtils.get().params(getParams(mPageNow)).url(UrlUtils.IFENG.CLIENT_SHORT_NEWS).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                }

                @Override
                public void onResponse(String response, int id) {
                    mRecyclerView.setCurrentLoadComplete();
                    JSONObject jsonObject = JSON.parseObject(response);
                    if (jsonObject.containsKey("body")) {
                        List<ShortNewsBean> shortNewsBeen = JSON.parseArray(jsonObject.getString("body"), ShortNewsBean.class);
                        mJokeAdapter.addData(shortNewsBeen);
                    }
                }
            });

        } else {
            mRecyclerView.setNetworkError();
        }
    }


    @Override
    public void onRealItemClick(View view, ShortNewsBean dataBean) {
//        if (dataBean != null && dataBean.getImageCount() >= 1) {
//            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//            intent.putExtra(NewsDetailActivity.KEY_NEWS, dataBean);
//            startActivity(intent);
//        }
    }

    @Override
    public void onRefresh() {
        mPageNow = 1;
        refreshData();
    }
}
