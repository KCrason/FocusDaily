package site.krason.focusdaily.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.activities.NewsDetailActivity;
import site.krason.focusdaily.activities.ProjectActivity;
import site.krason.focusdaily.activities.SlidesActivity;
import site.krason.focusdaily.activities.TextLiveActivity;
import site.krason.focusdaily.activities.VideoActivity;
import site.krason.focusdaily.adapters.RecommendAdpter;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.events.RefreshEvent;
import site.krason.focusdaily.utils.ACache;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRealItemClickCallBack;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class RecommendedFragment extends BaseFragment implements OnRecyclerLoadMoreLisener
        , OnRealItemClickCallBack<KNewBean.ItemBean>, SwipeRefreshLayout.OnRefreshListener {
    public final static String KEY_NEWS = "key_news";

    private KReyccleView mRecyclerView;

    private RecommendAdpter mScrollAdpter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private View mRootView;

    private final static String KEY_TYPE = "key_type";

    public static RecommendedFragment instance(String type) {
        RecommendedFragment recommendedFragment = new RecommendedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        recommendedFragment.setArguments(bundle);
        return recommendedFragment;
    }

    private String mType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(KEY_TYPE);
    }

    @Override
    public void LazyLoadDataToService() {
        if (!TextUtils.isEmpty(mType)) {
            if (mType.equals("focus")) {
                defaultData();
            } else if (mType.equals("recommend")) {
                recommendData();
            } else {
                refreshData();
            }
        }
    }

    private void recommendData() {
        OkHttpUtils.get().url("http://api.irecommend.ifeng.com/irecommendList.php")
                .params(getRecommendParams())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Snackbar.make(mRootView, "推荐失败！", Snackbar.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (!TextUtils.isEmpty(response)) {
                            ACache.get(getContext()).put(getKey(), response);
                            KNewBean bannerBean = JSON.parseObject(response, KNewBean.class);
                            if (bannerBean != null) {
                                mScrollAdpter.setData(bannerBean);
                                isLoadComplete = true;
                                removeRootView();
                            } else {
                                KUtils.showSnackbar("暂无更新推荐", mRootView);
                            }
                        }
                    }
                });
    }

    private void refreshData() {
        OkHttpUtils.get().url("http://api.iclient.ifeng.com/ClientNews")
                .params(getParams("down"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Snackbar.make(mRootView, "推荐失败！", Snackbar.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        ACache.get(getContext()).put(getKey(), response);
                        KNewBean bannerBean = getKNewsBeanList(response, 0);
                        if (bannerBean != null) {
                            mScrollAdpter.setData(bannerBean);
                            isLoadComplete = true;
                            removeRootView();
                        } else {
                            KUtils.showSnackbar("暂无更新推荐", mRootView);
                        }
                    }
                });
    }


    private void defaultData() {
        OkHttpUtils.get().url("http://api.iclient.ifeng.com/ClientNews")
                .params(getParams("default"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Snackbar.make(mRootView, "推荐失败！", Snackbar.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ACache.get(getContext()).put(getKey(), response);
                        mScrollAdpter.setData(getKNewsBeanList(response, 1));
                        mScrollAdpter.addData(getKNewsBeanList(response, 2));
                        mScrollAdpter.addData(getKNewsBeanList(response, 0));
                        isLoadComplete = true;
                        removeRootView();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshDataOfEvent(RefreshEvent refreshEvent) {
        if (refreshEvent.getEventCode() == 0 && refreshEvent.getData() == 0) {
            mRecyclerView.scrollToPosition(0);
            if (mSwipeRefreshLayout != null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
            }
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            }, 1000);
        }
    }


    private Map<String, String> getParams(String action) {
        String id = "SYLB10,SYDT10,SYRECOMMEND";
        Map<String, String> stringMap = new HashMap<>();
        if (mType != null) {
            if (mType.equals("focus")) {
                id = "SYLB10,SYDT10,SYRECOMMEND";
            } else if (mType.equals("world")) {
                id = "GJPD";
            } else if (mType.equals("social")) {
                id = "SH133,FOCUSSH133";
            } else if (mType.equals("digital")) {
                id = "SM66,FOCUSSM66";
            } else if (mType.equals("nba")) {
                id = "NBAPD";
            } else if (mType.equals("movie")) {
                id = "DYPD";
            } else if (mType.equals("game")) {
                id = "YX11,FOCUSYX11";
            }
        }
        stringMap.put("id", id);
        stringMap.put("action", action);
        stringMap.put("province", "%E5%B9%BF%E4%B8%9C%E7%9C%81");
        stringMap.put("city", "%E6%B7%B1%E5%9C%B3%E5%B8%82");
        stringMap.put("district", "%E5%8D%97%E5%B1%B1%E5%8C%BA");
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


    private Map<String, String> getRecommendParams() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("userId", "863055036432979");
        stringMap.put("count", String.valueOf(6));
        stringMap.put("province", "%E5%B9%BF%E4%B8%9C%E7%9C%81");
        stringMap.put("city", "%E6%B7%B1%E5%9C%B3%E5%B8%82");
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


    private KNewBean getKNewsBeanList(String result, int index) {

        JSONArray jsonArray = JSON.parseArray(result);
        if (jsonArray != null && jsonArray.size() > index) {
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            KNewBean kNewBean = JSON.parseObject(jsonObject.toString(), KNewBean.class);
            return kNewBean;
        }
        return null;
    }

    private String getKey() {
        String key = "KEY_FOCUS";
        if (mType != null) {
            if (mType.equals("focus")) {
                key = "KEY_FOCUS";
            } else if (mType.equals("world")) {
                key = "KEY_WORLD";
            } else if (mType.equals("social")) {
                key = "KEY_SOCIAL";
            } else if (mType.equals("ditigal")) {
                key = "KEY_DITIGAL";
            } else if (mType.equals("nba")) {
                key = "KEY_NBA";
            } else if (mType.equals("movie")) {
                key = "KEY_MOVIE";
            } else if (mType.equals("game")) {
                key = "KEY_GAME";
            } else if (mType.equals("recommend")) {
                key = "KEY_RECOMMEND";
            }
        }
        return key;
    }

    @Override
    public void LazyLoadDataToLocal() {
        String result = ACache.get(getContext()).getAsString(getKey());
        KNewBean kNewBean;
        if (mType.equals("recommend")) {
            kNewBean = JSON.parseObject(result, KNewBean.class);
        } else {
            kNewBean = getKNewsBeanList(result, 0);
        }
        if (kNewBean != null) {
            mScrollAdpter.setData(kNewBean);
            removeRootView();
        }
    }

    @Override
    public void initFragment(View view) {
        EventBus.getDefault().register(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#ed4040"));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRootView = view.findViewById(R.id.llayout_root);
        mRecyclerView = (KReyccleView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnRecyclerLoadMoreListener(this);
        mScrollAdpter = new RecommendAdpter(getContext(), this);
        mRecyclerView.setAdapter(mScrollAdpter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }


    @Override
    public void onRecyclerViewLoadMore() {
        if (KUtils.Network.isExistNetwork()) {
            String url = "http://api.iclient.ifeng.com/ClientNews";
            if (mType.equals("recommend")) {
                mRecyclerView.setAllLoadComplete();
            } else {
                OkHttpUtils.get().url(url)
                        .params(getParams("down"))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                KNewBean kNewBean = getKNewsBeanList(response, 0);
                                if (kNewBean == null) {
                                    mRecyclerView.setAllLoadComplete();
                                } else {
                                    mRecyclerView.setCurrentLoadComplete();
                                    mScrollAdpter.addData(kNewBean);
                                }
                            }
                        });
            }
        } else {
            mRecyclerView.setNetworkError();
        }
    }


    @Override
    public void onRealItemClick(View view, KNewBean.ItemBean dataBean) {
        String type = dataBean.getType();
        Intent intent = null;
        if (type != null) {
            if (type.equals("doc") || type.equals("web")) {
                intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(KEY_NEWS, dataBean);
                startActivity(intent);
                return;
            } else if (type.equals("phvideo")) {
                intent = new Intent(getActivity(), VideoActivity.class);
            } else if (type.equals("slide")) {
                intent = new Intent(getActivity(), SlidesActivity.class);
            } else if (type.equals("topic2")) {
                intent = new Intent(getActivity(), ProjectActivity.class);
            } else if (type.equals("text_live")) {
                intent = new Intent(getActivity(), TextLiveActivity.class);
            }
        }
        if (intent != null) {
            intent.putExtra(KEY_NEWS, dataBean.getLink().getUrl());
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        mRecyclerView.reload();
        if (mType.equals("recommend")) {
            recommendData();
        } else {
            refreshData();
        }
    }
}
