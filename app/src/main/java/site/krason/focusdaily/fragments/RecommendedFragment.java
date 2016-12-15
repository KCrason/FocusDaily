package site.krason.focusdaily.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.krason.focusdaily.R;
import site.krason.focusdaily.activities.NewsDetailActivity;
import site.krason.focusdaily.adapters.ScrollAdpter;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.common.Constants;
import site.krason.focusdaily.internet.http.RetrofitApi;
import site.krason.focusdaily.internet.http.RetrofitManage;
import site.krason.focusdaily.utils.ACache;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRealItemClickCallBack;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class RecommendedFragment extends BaseFragment implements OnRecyclerLoadMoreLisener, OnRealItemClickCallBack<KNewBean.DataBean> {

    private KReyccleView mRecyclerView;

    private ScrollAdpter mScrollAdpter;


    @Override
    public void LazyLoadDataToService() {
        RetrofitManage.getRetrofit(Constants.BAES_URL_NEWS)
                .create(RetrofitApi.class)
                .getNewsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KNewBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(KNewBean kNewBean) {
                        ACache.get(getContext()).put("RECOMMEND", kNewBean);
                        mScrollAdpter.setData(kNewBean.getData());
                        isLoadComplete = true;
                        removeRootView();
                    }
                });


    }

    @Override
    public void LazyLoadDataToLocal() {
        KNewBean kNewBean = (KNewBean) ACache.get(getContext()).getAsObject("RECOMMEND");
        mScrollAdpter.setData(kNewBean.getData());
        isLoadComplete = true;
        removeRootView();
    }

    @Override
    public void initFragment(View view) {
        mRecyclerView = (KReyccleView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnRecyclerLoadMoreListener(this);
        mScrollAdpter = new ScrollAdpter(getContext(), this);
        mRecyclerView.setAdapter(mScrollAdpter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }


    @Override
    public void onRecyclerViewLoadMore() {
        if (KUtils.Network.isExistNetwork()) {
            RetrofitManage.getRetrofit(Constants.BAES_URL_NEWS)
                    .create(RetrofitApi.class)
                    .getNewsList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<KNewBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("KCrason", e + "//");
                        }

                        @Override
                        public void onNext(KNewBean kNewBean) {
                            mRecyclerView.setCurrentLoadComplete();
                            mScrollAdpter.addData(kNewBean.getData());
                        }
                    });

        } else {
            mRecyclerView.setNetworkError();
        }
    }


    @Override
    public void onRealItemClick(View view, KNewBean.DataBean dataBean) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.KEY_NEWS, dataBean);
        startActivity(intent);
    }
}
