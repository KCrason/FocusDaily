package site.krason.focusdaily.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.activities.SlidesActivity;
import site.krason.focusdaily.bean.RecommendSlideBean;

import static site.krason.focusdaily.fragments.RecommendedFragment.KEY_NEWS;

/**
 * @author Created by KCrason on 2016/12/23.
 * @email 535089696@qq.com
 */

public class GalleryRecommendAdapter extends RecyclerView.Adapter<GalleryRecommendAdapter.GalleryRecommendViewHolder> {

    private List<RecommendSlideBean> mRecommendSlideBeen;

    private Context mContext;

    public GalleryRecommendAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public GalleryRecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_gallery_recommend, parent, false);
        return new GalleryRecommendViewHolder(view);
    }

    public void setData(List<RecommendSlideBean> recommendSlideBeen) {
        this.mRecommendSlideBeen = recommendSlideBeen;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(GalleryRecommendViewHolder holder, int position) {
        final RecommendSlideBean recommendSlideBean = mRecommendSlideBeen.get(position);
        if (holder != null && recommendSlideBean != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SlidesActivity.class);
                    intent.putExtra(KEY_NEWS, recommendSlideBean.getLinks());
                    mContext.startActivity(intent);
                }
            });

            if (recommendSlideBean != null) {
                if (holder.mTextView != null) {
                    holder.mTextView.setText(recommendSlideBean.getTitle());
                }
                if (holder.mImageView != null) {
                    Glide.with(mContext).load(recommendSlideBean.getThumbnail()).into(holder.mImageView);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mRecommendSlideBeen != null) {
            return mRecommendSlideBeen.size();
        }
        return 0;
    }

    public final class GalleryRecommendViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public GalleryRecommendViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.img_pic);
            mTextView = (TextView) itemView.findViewById(R.id.txt_title);
        }
    }
}
