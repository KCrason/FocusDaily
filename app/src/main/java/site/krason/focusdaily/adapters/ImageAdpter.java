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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.activities.SlidesActivity;
import site.krason.focusdaily.bean.ImageBean;
import site.krason.focusdaily.bean.ImageListBean;
import site.krason.focusdaily.utils.KUtils;

import static site.krason.focusdaily.fragments.RecommendedFragment.KEY_NEWS;

/**
 * @author Created by KCrason on 2016/12/27.
 * @email 535089696@qq.com
 */

public class ImageAdpter extends RecyclerView.Adapter<ImageAdpter.ImageViewHolder> {

    private Context mContext;

    private List<ImageListBean.ItemBean> mDataBeen = new ArrayList<>();

    public ImageAdpter(Context context) {
        this.mContext = context;

    }

    public void setData(List<ImageListBean.ItemBean> strings) {
        this.mDataBeen.clear();
        this.mDataBeen = strings;
        notifyDataSetChanged();
    }

    public void addData(List<ImageListBean.ItemBean> strings) {
        this.mDataBeen.addAll(strings);
        notifyDataSetChanged();
    }


    @Override
    public ImageAdpter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_image_list, parent, false);
        return new ImageAdpter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final ImageListBean.ItemBean dataBean = mDataBeen.get(position);
        holder.mTextView.setText(dataBean.getTitle());
        Glide.with(mContext).load(dataBean.getThumbnail()).asBitmap().into(holder.mImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SlidesActivity.class);
                List<ImageBean> imageBeanList = new ArrayList<>();
                ImageBean imageBean = new ImageBean();
                imageBean.setUrl(dataBean.getLinks().get(0).getUrl());
                imageBean.setWidth(0);
                imageBean.setHeight(0);
                imageBeanList.add(imageBean);
                intent.putExtra(KEY_NEWS, (Serializable) imageBeanList);
                mContext.startActivity(intent);
            }
        });
    }


    private void setImageSize(ImageView imageView, String width, String height) {
        float scal = Integer.parseInt(height) / Float.parseFloat(width);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = (int) (scal * KUtils.getScreenWidth());
        imageView.setLayoutParams(layoutParams);
    }


    @Override
    public int getItemCount() {
        if (mDataBeen != null) {
            return mDataBeen.size();
        }
        return 0;
    }

    public final class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.txt_title);
            mImageView = (ImageView) itemView.findViewById(R.id.img_pic);
        }
    }
}
