package site.krason.focusdaily.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.JokeAdapter;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.utils.ACache;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRealItemClickCallBack;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/16.
 * @email 535089696@qq.com
 */

public class JokeFragment extends BaseFragment implements OnRecyclerLoadMoreLisener
        , OnRealItemClickCallBack<KNewBean>, SwipeRefreshLayout.OnRefreshListener {

    private KReyccleView mRecyclerView;

    private JokeAdapter mJokeAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private View mRootView;


    @Override
    public void LazyLoadDataToService() {
        refreshData();
    }

    private void refreshData() {
//        RetrofitManage.getRetrofit(Constants.BAES_URL_NEWS)
//                .create(RetrofitApi.class)
//                .getNewsList("joke")
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
//                        Snackbar.make(mRootView, "推荐失败！", Snackbar.LENGTH_SHORT).show();
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onNext(KNewBean kNewBean) {
//                        ACache.get(getContext()).put("JOKE", kNewBean);
//                        mJokeAdapter.setData(kNewBean.getData());
//                        isLoadComplete = true;
//                        removeRootView();
//                        Snackbar.make(mRootView, "更新了10条", Snackbar.LENGTH_SHORT).show();
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                });

    }

    @Override
    public void LazyLoadDataToLocal() {
        KNewBean kNewBean = (KNewBean) ACache.get(getContext()).getAsObject("JOKE");
//        mJokeAdapter.setData(kNewBean.getData());
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
        mJokeAdapter = new JokeAdapter(getContext(), this);
        mRecyclerView.setAdapter(mJokeAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_joke;
    }


    @Override
    public void onRecyclerViewLoadMore() {
//        if (KUtils.Network.isExistNetwork()) {
//            RetrofitManage.getRetrofit(Constants.BAES_URL_NEWS)
//                    .create(RetrofitApi.class)
//                    .getNewsList("joke")
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<KNewBean>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(KNewBean kNewBean) {
//                            mRecyclerView.setCurrentLoadComplete();
//                            mJokeAdapter.addData(kNewBean.getData());
//                        }
//                    });
//
//        } else {
//            mRecyclerView.setNetworkError();
//        }
    }


    @Override
    public void onRealItemClick(View view, KNewBean dataBean) {
//        if (dataBean != null && dataBean.getImageCount() >= 1) {
//            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//            intent.putExtra(NewsDetailActivity.KEY_NEWS, dataBean);
//            startActivity(intent);
//        }
    }

    @Override
    public void onRefresh() {
        refreshData();
    }
}
