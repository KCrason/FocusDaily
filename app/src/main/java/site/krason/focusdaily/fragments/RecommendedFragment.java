package site.krason.focusdaily.fragments;

import android.app.Activity;
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
import site.krason.focusdaily.common.UrlUtils;
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

    private Activity mActivity;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = context;
    }

    public static RecommendedFragment instance(String type) {
        RecommendedFragment recommendedFragment = new RecommendedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        recommendedFragment.setArguments(bundle);
        return recommendedFragment;
    }


    public String getKeyType() {
        return mType;
    }


    private String mType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(KEY_TYPE);
    }

    private int mPag = 1;

    @Override
    public void LazyLoadDataToService() {
        if (!TextUtils.isEmpty(mType)) {
            if (mType.equals("头条") ||
                    mType.equals("台湾") ||
                    mType.equals("星座") ||
                    mType.equals("读书") ||
                    mType.equals("健康") ||
                    mType.equals("电影") ||
                    mType.equals("国际") ||
                    mType.equals("港澳") ||
                    mType.equals("家居") ||
                    mType.equals("跑步")) {
                defaultData();
            } else if (mType.equals("推荐")) {
                recommendData();
            } else if (mType.equals("科技") ||
                    mType.equals("娱乐") ||
                    mType.equals("时尚") ||
                    mType.equals("旅游") ||
                    mType.equals("国学") ||
                    mType.equals("时政") ||
                    mType.equals("青年") ||
                    mType.equals("评论") ||
                    mType.equals("政能量") ||
                    mType.equals("智库") ||
                    mType.equals("公益") ||
                    mType.equals("体育") ||
                    mType.equals("汽车") ||
                    mType.equals("财经")) {
                getPageData(mPag);
            } else {
                refreshData();
            }
        }
    }

    private void getPageData(int page) {
        OkHttpUtils.get().url(UrlUtils.IFENG.CLIENT_NEWS)
                .params(getPageParams(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Snackbar.make(mRootView, "推荐失败!", Snackbar.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        ACache.get(mActivity).put(getKey(), response);
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
                            ACache.get(mActivity).put(getKey(), response);
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
        OkHttpUtils.get().url(UrlUtils.IFENG.CLIENT_NEWS)
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
                        ACache.get(mActivity).put(getKey(), response);
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
        OkHttpUtils.get().url(UrlUtils.IFENG.CLIENT_NEWS)
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
                        ACache.get(mActivity).put(getKey(), response);
                        if (mType.equals("头条")) {
                            mScrollAdpter.setData(getKNewsBeanList(response, 1));
                            mScrollAdpter.addData(getKNewsBeanList(response, 2));
                        }
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

    private Map<String, String> getPageParams(int page) {
        String id = "KJ123";
        Map<String, String> stringMap = new HashMap<>();
        if (mType.equals("科技")) {
            id = "KJ123";
        } else if (mType.equals("娱乐")) {
            id = "YL53";
        } else if (mType.equals("时尚")) {
            id = "SS78";
        } else if (mType.equals("旅游")) {
            id = "LY67";
        } else if (mType.equals("国学")) {
            id = "GXPD";
        } else if (mType.equals("时政")) {
            id = "SZPD";
        } else if (mType.equals("青年")) {
            id = "QN39";
        } else if (mType.equals("暖新闻")) {
            id = "NXWPD";
        } else if (mType.equals("评论")) {
            id = "PL40";
        } else if (mType.equals("政能量")) {
            id = "ZNL345";
        } else if (mType.equals("智库")) {
            id = "ZK30";
        } else if (mType.equals("公益")) {
            id = "GYPD";
        } else if (mType.equals("体育")) {
            id = "TY43";
        } else if (mType.equals("汽车")) {
            id = "QC45";
        } else if (mType.equals("财经")) {
            id = "CJ33";
        }
        stringMap.put("id", id);
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


    private Map<String, String> getParams(String action) {
        String id = "SYLB10,SYDT10,SYRECOMMEND";
        Map<String, String> stringMap = new HashMap<>();
        if (mType != null) {
            if (mType.equals("头条")) {
                id = "SYLB10,SYDT10,SYRECOMMEND";
            } else if (mType.equals("国际")) {
                id = "GJPD";
            } else if (mType.equals("社会")) {
                id = "SH133";
            } else if (mType.equals("数码")) {
                id = "SM66";
            } else if (mType.equals("NBA")) {
                id = "NBAPD";
            } else if (mType.equals("电影")) {
                id = "DYPD";
            } else if (mType.equals("游戏")) {
                id = "YX11";
            } else if (mType.equals("军事")) {
                id = "JS83";
            } else if (mType.equals("历史")) {
                id = "LS153";
            } else if (mType.equals("台湾")) {
                id = "TW73";
            } else if (mType.equals("星座")) {
                id = "XZ09";
            } else if (mType.equals("读书")) {
                id = "DS57";
            } else if (mType.equals("健康")) {
                id = "JK36";
            } else if (mType.equals("港澳")) {
                id = "GA18";
            } else if (mType.equals("家居")) {
                id = "JJPD";
            } else if (mType.equals("跑步")) {
                id = "PBPD";
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
            if (mType.equals("头条")) {
                key = "KEY_FOCUS";
            } else if (mType.equals("国际")) {
                key = "KEY_WORLD";
            } else if (mType.equals("社会")) {
                key = "KEY_SOCIAL";
            } else if (mType.equals("数码")) {
                key = "KEY_DITIGAL";
            } else if (mType.equals("NBA")) {
                key = "KEY_NBA";
            } else if (mType.equals("电影")) {
                key = "KEY_MOVIE";
            } else if (mType.equals("游戏")) {
                key = "KEY_GAME";
            } else if (mType.equals("推荐")) {
                key = "KEY_RECOMMEND";
            } else if (mType.equals("科技")) {
                key = "KEY_KEJI";
            } else if (mType.equals("娱乐")) {
                key = "KEY_YULE";
            } else if (mType.equals("军事")) {
                key = "KEY_JUNSHI";
            } else if (mType.equals("历史")) {
                key = "KEY_LISHI";
            } else if (mType.equals("时尚")) {
                key = "KEY_SHISHANG";
            } else if (mType.equals("暖新闻")) {
                key = "KEY_NUANXINWEN";
            } else if (mType.equals("教育")) {
                key = "KEY_JIAOYU";
            } else if (mType.equals("港澳")) {
                key = "KEY_GANGAO";
            } else if (mType.equals("家居")) {
                key = "KEY_JIAJU";
            } else if (mType.equals("台湾")) {
                key = "KEY_TAIWAN";
            } else if (mType.equals("时政")) {
                key = "KEY_SHIZHENG";
            } else if (mType.equals("文化")) {
                key = "KEY_WENHUA";
            } else if (mType.equals("跑步")) {
                key = "KEY_PAOBU";
            } else if (mType.equals("星座")) {
                key = "KEY_XINGZUO";
            } else if (mType.equals("读书")) {
                key = "KEY_DUSHU";
            } else if (mType.equals("健康")) {
                key = "KEY_JIANKANG";
            } else if (mType.equals("青年")) {
                key = "KEY_QINGNIAN";
            } else if (mType.equals("智库")) {
                key = "KEY_ZHIKU";
            } else if (mType.equals("公益")) {
                key = "KEY_GONGYI";
            } else if (mType.equals("房产")) {
                key = "KEY_FANGCHAN";
            } else if (mType.equals("汽车")) {
                key = "KEY_QICHE";
            } else if (mType.equals("旅游")) {
                key = "KEY_LVYOU";
            } else if (mType.equals("评论")) {
                key = "KEY_PINGLUAN";
            } else if (mType.equals("政能量")) {
                key = "KEY_ZHENGNENGLIANG";
            } else if (mType.equals("国学")) {
                key = "KEY_GUOXUE";
            } else if (mType.equals("财经")) {
                key = "KEY_CAIJING";
            } else if (mType.equals("体育")) {
                key = "KEY_TIYU";
            }
        }
        return key;
    }

    private boolean isNeedLoadDataToLocal = false;

    @Override
    public void LazyLoadDataToLocal() {
        if (isLoadComplete && isNeedLoadDataToLocal) {
            String result = ACache.get(mActivity).getAsString(getKey());
            KNewBean kNewBean;
            if (mType.equals("推荐")) {
                kNewBean = JSON.parseObject(result, KNewBean.class);
            } else {
                kNewBean = getKNewsBeanList(result, 0);
            }
            if (kNewBean != null) {
                mScrollAdpter.setData(kNewBean);
                removeRootView();
            }
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
        mScrollAdpter = new RecommendAdpter(getContext(), this, mType);
        mRecyclerView.setAdapter(mScrollAdpter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isNeedLoadDataToLocal = true;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }


    @Override
    public void onRecyclerViewLoadMore() {
        if (KUtils.Network.isExistNetwork()) {
            String url = UrlUtils.IFENG.CLIENT_NEWS;
            if (mType.equals("推荐")) {
                mRecyclerView.setAllLoadComplete();
            } else if (mType.equals("科技") ||
                    mType.equals("娱乐") ||
                    mType.equals("时尚") ||
                    mType.equals("旅游") ||
                    mType.equals("国学") ||
                    mType.equals("时政") ||
                    mType.equals("青年") ||
                    mType.equals("评论") ||
                    mType.equals("政能量") ||
                    mType.equals("智库") ||
                    mType.equals("公益") ||
                    mType.equals("体育") ||
                    mType.equals("汽车") ||
                    mType.equals("财经")) {
                LoadMoreData(url);
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

    private void LoadMoreData(String url) {
        OkHttpUtils.get().url(url)
                .params(getPageParams(++mPag))
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
        if (mType.equals("推荐")) {
            recommendData();
        } else if (mType.equals("科技") ||
                mType.equals("娱乐") ||
                mType.equals("时尚") ||
                mType.equals("旅游") ||
                mType.equals("国学") ||
                mType.equals("时政") ||
                mType.equals("青年") ||
                mType.equals("评论") ||
                mType.equals("政能量") ||
                mType.equals("智库") ||
                mType.equals("公益") ||
                mType.equals("体育") ||
                mType.equals("汽车") ||
                mType.equals("财经")) {
            getPageData(1);
        } else {
            refreshData();
        }
    }
}
