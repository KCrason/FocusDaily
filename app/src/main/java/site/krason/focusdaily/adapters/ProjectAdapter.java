package site.krason.focusdaily.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import site.krason.focusdaily.activities.NewsDetailActivity;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.bean.ProjectBean;

import static site.krason.focusdaily.fragments.RecommendedFragment.KEY_NEWS;

/**
 * @author Created by KCrason on 2016/12/26.
 * @email 535089696@qq.com
 */

public class ProjectAdapter extends BaseAdapter {

    private Context mContext;

    public ProjectAdapter(Context context) {
        this.mContext = context;
    }

    private List<ProjectBean.PodItemsBean> mPodItemsBeen = new ArrayList<>();
    private List<String> mStrings = new ArrayList<>();

    public void setData(List<ProjectBean.PodItemsBean> podItemsBeen, List<String> strings) {
        if (mPodItemsBeen != null) {
            mPodItemsBeen.clear();
        }
        if (mStrings != null) {
            mStrings.clear();
        }
        this.mStrings = strings;
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

    public class ListViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
        private TextView mTxtType;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ProjectBean.PodItemsBean podItemsBean = mPodItemsBeen.get(i);
        ListViewHolder listViewHolder;
        if (view == null) {
            listViewHolder = new ListViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_1_pic, viewGroup, false);
            listViewHolder.mImageView = (ImageView) view.findViewById(R.id.img_pic);
            listViewHolder.mTextView = (TextView) view.findViewById(R.id.txt_title);
            listViewHolder.mTxtType = (TextView) view.findViewById(R.id.txt_type);
            view.setTag(listViewHolder);
        } else {
            listViewHolder = (ListViewHolder) view.getTag();
        }
        if (podItemsBean != null) {
            listViewHolder.mTextView.setText(podItemsBean.getTitle());
            if (TextUtils.isEmpty(podItemsBean.getThumbnail())) {
                listViewHolder.mImageView.setVisibility(View.GONE);
            } else {
                listViewHolder.mImageView.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(podItemsBean.getThumbnail()).asBitmap().into(listViewHolder.mImageView);
            }
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                KNewBean.ItemBean dataBean = new KNewBean.ItemBean();
                dataBean.setType(podItemsBean.getLinks().get(0).getType());
                KNewBean.ItemBean.LinkBean linkBean = new KNewBean.ItemBean.LinkBean();
                linkBean.setUrl(podItemsBean.getLinks().get(0).getUrl());
                dataBean.setLink(linkBean);
                intent.putExtra(KEY_NEWS, dataBean);
                mContext.startActivity(intent);
            }
        });
        return view;
    }
}
