package site.krason.focusdaily.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.activities.FullScreenPlayVideoActivity;
import site.krason.focusdaily.bean.ProjectBean;
import site.krason.focusdaily.utils.KUtils;

/**
 * @author Created by KCrason on 2016/12/26.
 * @email 535089696@qq.com
 */

public class ProjectFooterAblumAdapter extends BaseAdapter {

    private Context mContext;

    public ProjectFooterAblumAdapter(Context context) {
        this.mContext = context;
    }

    private List<ProjectBean.PodItemsBean> mPodItemsBeen = new ArrayList<>();

    public void setData(List<ProjectBean.PodItemsBean> podItemsBeen) {
        if (mPodItemsBeen != null) {
            mPodItemsBeen.clear();
        }
        this.mPodItemsBeen = podItemsBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mPodItemsBeen != null) {
            return mPodItemsBeen.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (mPodItemsBeen != null) {
            mPodItemsBeen.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        if (mPodItemsBeen != null) {
            mPodItemsBeen.size();
        }
        return 0;
    }

    public class AlbumViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ProjectBean.PodItemsBean podItemsBean = mPodItemsBeen.get(i);
        AlbumViewHolder albumViewHolder;
        if (view == null) {
            albumViewHolder = new AlbumViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_album, viewGroup, false);
            albumViewHolder.mImageView = (ImageView) view.findViewById(R.id.img_pic);
            albumViewHolder.mTextView = (TextView) view.findViewById(R.id.txt_title);
            view.setTag(albumViewHolder);
        } else {
            albumViewHolder = (AlbumViewHolder) view.getTag();
        }
        if (podItemsBean != null) {
            albumViewHolder.mTextView.setText(podItemsBean.getTitle());
            ViewGroup.LayoutParams layoutParams = albumViewHolder.mImageView.getLayoutParams();
            layoutParams.width = KUtils.getScreenWidth() / 2 - 2 * KUtils.dip2px(10);
            layoutParams.height = KUtils.getScreenWidth() / 2 - 2 * KUtils.dip2px(10);
            albumViewHolder.mImageView.setLayoutParams(layoutParams);
            Glide.with(mContext).load(podItemsBean.getThumbnail()).into(albumViewHolder.mImageView);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreenPlayVideoActivity.actionStart(mContext, podItemsBean.getLinks().get(0).getUrl());
            }
        });
        return view;
    }
}
