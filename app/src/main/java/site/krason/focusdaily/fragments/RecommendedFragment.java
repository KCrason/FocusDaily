package site.krason.focusdaily.fragments;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.activities.NewsDetailActivity;
import site.krason.focusdaily.adapters.RecommendAdpter;
import site.krason.focusdaily.bean.KNewBean;
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
        , OnRealItemClickCallBack<KNewBean>, SwipeRefreshLayout.OnRefreshListener {

    private KReyccleView mRecyclerView;

    private RecommendAdpter mScrollAdpter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private View mRootView;


    @Override
    public void LazyLoadDataToService() {
        refreshData();
    }

    private void refreshData() {

//        RetrofitManage.getRetrofit(Constants.BAES_URL_NEWS)
//                .create(RetrofitApi.class)
//                .getNewsList("recommend")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<KNewBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(KNewBean kNewBean) {
//
//                    }
//                });


        OkHttpUtils.get().url("http://api.iclient.ifeng.com/ClientNews")
                .params(getParams("down"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("KCrason", "onError:" + e);
                        Snackbar.make(mRootView, "推荐失败！", Snackbar.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONArray jsonArray = JSON.parseArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        List<KNewBean> kNewBeanList = JSON.parseArray(jsonObject.getString("item"), KNewBean.class);
                        Log.d("KCrason", "Result:" + response);

//                        ACache.get(getContext()).put("RECOMMEND", response);
                        mScrollAdpter.setData(kNewBeanList);
                        isLoadComplete = true;
                        removeRootView();
                        Snackbar.make(mRootView, "更新了10条", Snackbar.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private Map<String, String> getParams(String action) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("id", "SYLB10,SYDT10,SYRECOMMEND");
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

    @Override
    public void LazyLoadDataToLocal() {
        KNewBean kNewBean = (KNewBean) ACache.get(getContext()).getAsObject("RECOMMEND");
//        mScrollAdpter.setData(kNewBean.getData());
        removeRootView();
    }

    @Override
    public void initFragment(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRootView = view.findViewById(R.id.llayout_root);
        mRecyclerView = (KReyccleView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnRecyclerLoadMoreListener(this);
        mScrollAdpter = new RecommendAdpter(getContext(), this);
        mRecyclerView.setAdapter(mScrollAdpter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }


    @Override
    public void onRecyclerViewLoadMore() {
        if (KUtils.Network.isExistNetwork()) {
            OkHttpUtils.get().url("http://api.iclient.ifeng.com/ClientNews")
                    .params(getParams("down"))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            JSONArray jsonArray = JSON.parseArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            List<KNewBean> kNewBeanList = JSON.parseArray(jsonObject.getString("item"), KNewBean.class);
                            mRecyclerView.setCurrentLoadComplete();
                            mScrollAdpter.addData(kNewBeanList);
                        }
                    });
        } else {
            mRecyclerView.setNetworkError();
        }
    }


    @Override
    public void onRealItemClick(View view, KNewBean dataBean) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.KEY_NEWS, dataBean);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        refreshData();
    }
}
