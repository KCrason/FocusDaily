package site.krason.focusdaily.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.activities.NewsDetailActivity;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class ScrollAdpter extends RecyclerView.Adapter<ScrollAdpter.ScrollViewHolder> {

    private Context mContext;

    private List<String> mStrings = new ArrayList<>();

    public ScrollAdpter(Context context) {
        this.mContext = context;
    }

    public void setData(List<String> strings) {
        this.mStrings = strings;
        notifyDataSetChanged();
    }


    @Override
    public ScrollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new ScrollViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScrollViewHolder holder, int position) {
        TextView textView = holder.getTextView();
        if (textView != null) {
            textView.setText(mStrings.get(position));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, NewsDetailActivity.class));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public final static class ScrollViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ScrollViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
        }

        public TextView getTextView() {
            return mTextView;
        }
    }
}
