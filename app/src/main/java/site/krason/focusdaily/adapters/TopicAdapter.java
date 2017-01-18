package site.krason.focusdaily.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.activities.PreviewImagesActivty;
import site.krason.focusdaily.bean.TopicBean;
import site.krason.focusdaily.viewholders.MorePictureViewHolder;
import site.krason.focusdaily.viewholders.NoPictureViewHolder;
import site.krason.focusdaily.viewholders.TopicBaseViewHolder;
import site.krason.focusdaily.viewholders.VideoViewHolder;
import site.krason.focusdaily.widgets.NineGridView;

/**
 * @author Created by KCrason on 2017/1/17.
 * @email 535089696@qq.com
 */

public class TopicAdapter extends RecyclerView.Adapter {

    private final static int TYPE_NO_PICTURE = 0;
    private final static int TYPE_MORE_PICTURE = 1;
    private final static int TYPE_SINGLE = 2;


    private List<TopicBean> mTopicBeen = new ArrayList<>();

    private Context mContext;

    private boolean isLoadImage;

    public void setData(List<TopicBean> topicBeen, boolean b) {
        if (!b) {
            this.mTopicBeen.clear();
        }
        this.mTopicBeen.addAll(topicBeen);
        notifyItemRangeChanged(this.mTopicBeen.size(), topicBeen.size());
    }


    public TopicAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NO_PICTURE:
                return new NoPictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_topic_no_picture, parent, false));
            case TYPE_MORE_PICTURE:
                return new MorePictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_topic_more_picture, parent, false));
            case TYPE_SINGLE:
                return new VideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_topic_video, parent, false));
            default:
                return new NoPictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_topic_no_picture, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TopicBean topicBean = mTopicBeen.get(position);
        setBaseData(holder, topicBean);
        if (holder instanceof MorePictureViewHolder) {
            TopicNineGridViewAdapter topicNineGridViewAdapter = new TopicNineGridViewAdapter(topicBean.getImageBeanList(), mContext);
            ((MorePictureViewHolder) holder).mNineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
                @Override
                public void onImageCilcked(int position, View view) {
                    Intent intent = new Intent(mContext, PreviewImagesActivty.class);
                    intent.putExtra("key_urls", (Serializable) topicBean.getLargeImageBeanList());
                    intent.putExtra("key_position", position);
                    mContext.startActivity(intent);
                }
            });
            topicNineGridViewAdapter.isLoadImage(isLoadImage);
            ((MorePictureViewHolder) holder).mNineGridView.setAdapter(topicNineGridViewAdapter);
        } else if (holder instanceof VideoViewHolder) {
            if (topicBean.getMBlogBeen().getPic_ids().size() <= 1) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((VideoViewHolder) holder).mImageView.getLayoutParams();
                layoutParams.width = topicBean.getImageBeanList().get(0).getWidth();
                layoutParams.height = topicBean.getImageBeanList().get(0).getHeight();
                ((VideoViewHolder) holder).mImageView.setLayoutParams(layoutParams);
                Glide.with(mContext).load(topicBean.getImageBeanList().get(0).getUrl()).into(((VideoViewHolder) holder).mImageView);
                ((VideoViewHolder) holder).mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, PreviewImagesActivty.class);
                        intent.putExtra("key_urls", (Serializable) topicBean.getLargeImageBeanList());
                        intent.putExtra("key_position", 0);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }


    private void setBaseData(RecyclerView.ViewHolder viewHolder, TopicBean topicBean) {
        TopicBaseViewHolder topicBaseViewHolder = (TopicBaseViewHolder) viewHolder;

        topicBaseViewHolder.getTextViewContent().setText(topicBean.getMBlogBeen().getText());
        topicBaseViewHolder.getTextViewName().setText(topicBean.getMBlogBeen().getUser().getName());
        topicBaseViewHolder.getTextViewTime().setText(topicBean.getMBlogBeen().getCreated_at());

        Glide.with(mContext).load(topicBean.getMBlogBeen().getUser().getProfile_image_url()).into(topicBaseViewHolder.getImageViewAvatar());

        topicBaseViewHolder.getTextViewComments().setText(topicBean.getMBlogBeen().getComments_count() + "");
        topicBaseViewHolder.getTextViewFavorites().setText(topicBean.getMBlogBeen().getAttitudes_count() + "");
        topicBaseViewHolder.getLinearLayoutShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "share", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        TopicBean topicBean = mTopicBeen.get(position);
        List<String> picIds = topicBean.getMBlogBeen().getPic_ids();
        if (picIds != null) {
            if (picIds.size() > 1) {
                return TYPE_MORE_PICTURE;
            } else if (picIds.size() == 1) {
                return TYPE_SINGLE;
            } else {
                return TYPE_NO_PICTURE;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (mTopicBeen != null) {
            return mTopicBeen.size();
        }
        return 0;
    }

    public void loadImage(boolean b, int positionStart, int itemCount) {
        this.isLoadImage = b;
    }
}
