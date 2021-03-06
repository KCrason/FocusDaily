package site.krason.focusdaily.widgets.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import site.krason.focusdaily.adapters.VideoListAdapter;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.utils.ViewHolderManage;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnScrollStatusListener;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class KReyccleView extends RecyclerView {

    private OnRecyclerLoadMoreLisener mOnRecyclerLoadMoreLisener;

    private RecyclerView.Adapter mOriginalAdapter;

    private KRecyclerAdapter mAdapter;

    private Context mContext;

    private FooterView mFooterView;

    private View mHeaderView = null;

    private boolean isLoading;

    private boolean isCanLoadMore = true;

    private Status mStatus;

    private RecyclerViewOnScroll mRecyclerViewOnScroll;


    public enum Status {
        NETWORL_ERROR, RELOAD
    }


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
        mRecyclerViewOnScroll = new RecyclerViewOnScroll();
        this.addOnScrollListener(mRecyclerViewOnScroll);
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
            mStatus = Status.RELOAD;
            setCancleLoadMore(true);
            mFooterView.resetFooterView();
        }
    }


    /**
     * 设置网络错误时的显示状态
     */
    public void setNetworkError() {
        if (mFooterView != null) {
            mStatus = Status.NETWORL_ERROR;
            mFooterView.setNetworkErrorText();
            setCurrentLoadComplete();
        }
    }

    private OnScrollStatusListener mOnLoadImageListener;

    public void setOnLoadImageListener(OnScrollStatusListener onLoadImageListener) {
        this.mOnLoadImageListener = onLoadImageListener;
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

    private LayoutManager mLayoutManager;
    private int mLastVisiblePosition;

    public final class RecyclerViewOnScroll extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (recyclerView != null) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager && mOriginalAdapter instanceof VideoListAdapter) {
                    int firstVisiblePosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    int lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int currentPlayPosition = ((VideoListAdapter) mOriginalAdapter).getCurPlayPosition();
                    if (currentPlayPosition != -1 && currentPlayPosition != firstVisiblePosition) {
                        if (dy > 0 && firstVisiblePosition > currentPlayPosition) {
                            ViewHolderManage.create().pauseVideoPlay();
                        } else if (dy < 0 && lastVisiblePosition < currentPlayPosition) {
                            ViewHolderManage.create().pauseVideoPlay();
                        }
                    }
                }
            }
        }

        private int mFirstVisiblePosition;
        private int mItemCount;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            mLayoutManager = recyclerView.getLayoutManager();
            if (mLayoutManager instanceof LinearLayoutManager || mLayoutManager instanceof GridLayoutManager) {
                mLastVisiblePosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                mFirstVisiblePosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            } else if (mLayoutManager instanceof GridLayoutManager) {
                mLastVisiblePosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                mFirstVisiblePosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                int into[] = new int[((StaggeredGridLayoutManager) mLayoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(into);
                ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(into);
                mLastVisiblePosition = getLastPosition(into);
                mFirstVisiblePosition = getFirstPosition(into);
            }

            if (mLastVisiblePosition >= mLayoutManager.getItemCount() - 1) {
                mItemCount = mLastVisiblePosition - mFirstVisiblePosition;
                Log.d("KCrason", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + mItemCount + "//" + mLastVisiblePosition + "//" + mLayoutManager.getItemCount());
            } else {
                mItemCount = mLastVisiblePosition - mFirstVisiblePosition + 1;
                Log.d("KCrason", "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy" + mItemCount);
            }

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mOnLoadImageListener != null) {
                    mOnLoadImageListener.onScrollStatus(true, mFirstVisiblePosition, mItemCount);
                }
            } else {
                if (mOnLoadImageListener != null) {
                    mOnLoadImageListener.onScrollStatus(false, mFirstVisiblePosition, mItemCount);
                }
            }


            if (mLayoutManager.getChildCount() > 0 && mLastVisiblePosition >= mLayoutManager.getItemCount() - 1) {
                startOnLoadMore();
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

    private int getFirstPosition(int[] into) {
        int firstPositon = into[0];
        for (int value : into) {
            if (value < firstPositon) {
                firstPositon = value;
            }
        }
        return firstPositon;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.mOriginalAdapter = adapter;
        this.mAdapter = new KRecyclerAdapter(adapter);
        this.mFooterView = new FooterView(mContext);
        mAdapter.setFooterView(mFooterView);
        mAdapter.setHeaderView(mHeaderView);
        super.setAdapter(mAdapter);
        adapter.registerAdapterDataObserver(new KAdapterDataObserver());
    }

    public void startOnLoadMore() {
        if (mOnRecyclerLoadMoreLisener != null && !isLoading) {
            if (isCanLoadMore) {
                if (KUtils.Network.isExistNetwork() && mStatus == Status.NETWORL_ERROR) {
                    reload();
                }
                isLoading = true;
                mOnRecyclerLoadMoreLisener.onRecyclerViewLoadMore();
            }
        }
    }


    public final class KAdapterDataObserver extends AdapterDataObserver {
        @Override
        public void onChanged() {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }
}
