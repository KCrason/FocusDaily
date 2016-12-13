package site.krason.focusdaily.widgets.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class KReyccleView extends RecyclerView {

    private OnRecyclerLoadMoreLisener mOnRecyclerLoadMoreLisener;


    private KRecyclerAdapter mAdapter;

    private Context mContext;

    private FooterView mFooterView;


    private View mHeaderView = null;

    private boolean isLoading;

    private boolean isCanLoadMore = true;

    public KReyccleView(Context context) {
        super(context);
        initRecycleView(context);
    }

    public KReyccleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRecycleView(context);
    }

    public KReyccleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initRecycleView(context);
    }

    private void initRecycleView(Context context) {
        this.mContext = context;
        this.addOnScrollListener(new RecyclerViewOnScroll());
    }


    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    public void setOnRecyclerLoadMoreListener(OnRecyclerLoadMoreLisener onRecyclerLoadMoreListener) {
        this.mOnRecyclerLoadMoreLisener = onRecyclerLoadMoreListener;
    }

    /**
     * 标示当前页是否已经加载完成
     */
    public void setCurrentLoadComplete() {
        this.isLoading = false;
    }

    /**
     * 设置是否可以加载更多
     *
     * @param isCanLoadMore
     */
    public void setCancleLoadMore(boolean isCanLoadMore) {
        this.isCanLoadMore = isCanLoadMore;
    }


    /**
     * 重新加载更多，恢复FooterView的初始状态
     */
    public void reload() {
        if (mFooterView != null) {
            mFooterView.resetFooterView();
        }
    }


    /**
     * 设置网络错误时的显示状态
     */
    public void setNetworkError() {
        if (mFooterView != null) {
            mFooterView.setLoadAllCompleteText();
            setCurrentLoadComplete();
        }
    }


    /**
     * 所有数据已经加载完成的显示状态
     * 如果所有数据已经加载完成，则不再回调加载更多接口
     */
    public void setAllLoadComplete() {
        if (mFooterView != null) {
            setCurrentLoadComplete();
            mFooterView.setLoadAllCompleteText();
            setCancleLoadMore(false);
        }
    }


    public final class RecyclerViewOnScroll extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            int lastVisiblePosition = 0;
            if (layoutManager instanceof LinearLayoutManager || layoutManager instanceof GridLayoutManager) {
                lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                lastVisiblePosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int into[] = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisiblePosition = getLastPosition(into);
            }
            if (layoutManager.getChildCount() > 0 && lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                if (mOnRecyclerLoadMoreLisener != null && !isLoading) {
                    if (isCanLoadMore) {
                        mOnRecyclerLoadMoreLisener.onRecyclerViewLoadMore();
                        isLoading = true;
                    }
                }
            }
        }
    }

    /**
     * 获取最后一个item的position
     *
     * @param lastPositions
     * @return
     */
    private int getLastPosition(int[] lastPositions) {
        int lastPosition = lastPositions[0];
        for (int value : lastPositions) {
            if (value > lastPosition) {
                lastPosition = value;
            }
        }
        return lastPosition;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.mAdapter = new KRecyclerAdapter(adapter);
        this.mFooterView = new FooterView(mContext);
        mAdapter.setFooterView(mFooterView);
        mAdapter.setHeaderView(mHeaderView);
        super.setAdapter(mAdapter);
    }
}
