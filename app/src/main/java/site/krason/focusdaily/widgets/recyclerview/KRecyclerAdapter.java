package site.krason.focusdaily.widgets.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class KRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int VIEW_TYPE_HEADER = 0;
    private final static int VIEW_TYPE_FOOTER = -1;
    private final static int VIEW_TYPE_DEFAULT = -2;

    private final static int VIEW_TYPE_COUNT_NO_HEADER = 1;
    private final static int VIEW_TYPE_COUNT = 2;


    private RecyclerView.Adapter<RecyclerView.ViewHolder> mViewHolderAdapter;

    private View mFooterView = null;
    private View mHeaderView = null;

    public KRecyclerAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> viewHolderAdapter) {
        this.mViewHolderAdapter = viewHolderAdapter;
    }

    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView == null) {
            if (viewType == VIEW_TYPE_FOOTER) {
                if (mFooterView == null) {
                    throw new RuntimeException("FooterView can not null...");
                }
                return new FooterViewHolder(mFooterView);
            } else {
                return mViewHolderAdapter.createViewHolder(parent, viewType);
            }
        } else {
            if (viewType == VIEW_TYPE_HEADER) {
                return new HeaderViewHolder(mHeaderView);
            } else if (viewType == VIEW_TYPE_FOOTER) {
                if (mFooterView == null) {
                    throw new RuntimeException("FooterView can not null...");
                }
                return new FooterViewHolder(mFooterView);
            } else {
                return mViewHolderAdapter.createViewHolder(parent, viewType);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mViewHolderAdapter != null) {
            if (mHeaderView == null) {
                if (position < getItemCount() - 1) {
                    mViewHolderAdapter.onBindViewHolder(holder, position);
                }
            } else {
                if (position > 0 && position < getItemCount() - 1) {
                    mViewHolderAdapter.onBindViewHolder(holder, position - 1);
                }
            }
        }
    }

    public boolean isFooter(int position) {
        return position < getItemCount() && position >= getItemCount() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            if (isFooter(position)) {
                return VIEW_TYPE_FOOTER;
            } else {
                if (mViewHolderAdapter != null) {
                    int count = mViewHolderAdapter.getItemCount();
                    if (position < count) {
                        return mViewHolderAdapter.getItemViewType(position);
                    }
                }
            }
        } else {
            if (position == 0) {
                return VIEW_TYPE_HEADER;
            } else if (isFooter(position)) {
                return VIEW_TYPE_FOOTER;
            } else {
                if (mViewHolderAdapter != null) {
                    int count = mViewHolderAdapter.getItemCount();
                    if (position < count) {
                        return mViewHolderAdapter.getItemViewType(position);
                    }
                }
            }
        }

        return VIEW_TYPE_DEFAULT;
    }


    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return mViewHolderAdapter.getItemCount() + VIEW_TYPE_COUNT_NO_HEADER;
        }

        return mViewHolderAdapter.getItemCount() + VIEW_TYPE_COUNT;
    }



}
