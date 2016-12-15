package site.krason.focusdaily.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRealItemClickCallBack;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class ScrollAdpter extends RecyclerView.Adapter {

    private final static int VIEW_TYPE_NO_PIC = 0;
    private final static int VIEW_TYPE_1_PIC = 1;
    private final static int VIEW_TYPE_2_PIC = 2;
    private final static int VIEW_TYPE_3_PIC = 3;


    private Context mContext;

    private List<KNewBean.DataBean> mStrings = new ArrayList<>();

    private OnRealItemClickCallBack<KNewBean.DataBean> mDataBeanOnRealItemClickCallBack;

    public ScrollAdpter(Context context, OnRealItemClickCallBack<KNewBean.DataBean> dataBeanOnRealItemClickCallBack) {
        this.mContext = context;
        this.mDataBeanOnRealItemClickCallBack = dataBeanOnRealItemClickCallBack;
    }

    public void setData(List<KNewBean.DataBean> strings) {
        this.mStrings = strings;
        notifyDataSetChanged();
    }

    public void addData(List<KNewBean.DataBean> strings) {
        this.mStrings.addAll(strings);
        notifyDataSetChanged();
    }


    public final class OnRecyclerItemClick implements View.OnClickListener {

        private KNewBean.DataBean mDataBean;

        public OnRecyclerItemClick(KNewBean.DataBean dataBean) {
            this.mDataBean = dataBean;
        }

        @Override
        public void onClick(View view) {
            if (mDataBeanOnRealItemClickCallBack != null) {
                mDataBeanOnRealItemClickCallBack.onRealItemClick(view, mDataBean);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NO_PIC:
                return new NoPicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_not_pic, parent, false));
            case VIEW_TYPE_1_PIC:
                return new OnePicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_1_pic, parent, false));
            case VIEW_TYPE_2_PIC:
                return new TwoPicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_2_pic, parent, false));
            case VIEW_TYPE_3_PIC:
                return new ThreePicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_3_pic, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            KNewBean.DataBean dataBean = mStrings.get(position);
            holder.itemView.setOnClickListener(new OnRecyclerItemClick(dataBean));
            if (dataBean != null) {
                if (holder instanceof NoPicViewHolder) {
                    ((NoPicViewHolder) holder).mTitle.setText(dataBean.getTitle());
                    ((NoPicViewHolder) holder).mBaseInfo.setText(dataBean.getSource() + "  " + dataBean.getCTime());
                } else if (holder instanceof OnePicViewHolder) {
                    ((OnePicViewHolder) holder).mTitle.setText(dataBean.getTitle());
                    ((OnePicViewHolder) holder).mBaseInfo.setText(dataBean.getSource() + "  " + dataBean.getCTime());
                    Glide.with(mContext).load(dataBean.getCoverUrl()).into(((OnePicViewHolder) holder).imgOnePic);
                } else if (holder instanceof TwoPicViewHolder) {
                    ((TwoPicViewHolder) holder).mTitle.setText(dataBean.getTitle());
                    ((TwoPicViewHolder) holder).mBaseInfo.setText(dataBean.getSource() + "  " + dataBean.getCTime());
                    Glide.with(mContext).load(dataBean.getCoverUrl()).into(((TwoPicViewHolder) holder).imgBigPic);
                } else if (holder instanceof ThreePicViewHolder) {
                    ((ThreePicViewHolder) holder).mTitle.setText(dataBean.getTitle());
                    ((ThreePicViewHolder) holder).mBaseInfo.setText(dataBean.getSource() + "  " + dataBean.getCTime());
                    Glide.with(mContext).load(dataBean.getImages().get(0).getImageUrl()).into(((ThreePicViewHolder) holder).imgOnePic);
                    Glide.with(mContext).load(dataBean.getImages().get(1).getImageUrl()).into(((ThreePicViewHolder) holder).imgTwoPic);
                    Glide.with(mContext).load(dataBean.getImages().get(2).getImageUrl()).into(((ThreePicViewHolder) holder).imgThreePic);
                }
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position < mStrings.size()) {
            KNewBean.DataBean dataBean = mStrings.get(position);
            if (dataBean != null) {
                int count = dataBean.getImageCount();
                if (count <= 0) {
                    return VIEW_TYPE_NO_PIC;
                } else if (count == 1) {
                    return VIEW_TYPE_1_PIC;
                } else if (count == 2) {
                    return VIEW_TYPE_2_PIC;
                } else if (count >= 3) {
                    return VIEW_TYPE_3_PIC;
                }
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }


    public final static class NoPicViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mBaseInfo;

        public NoPicViewHolder(View itemView) {
            super(itemView);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
        }

    }

    public final static class OnePicViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mBaseInfo;
        public ImageView imgOnePic;

        public OnePicViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            imgOnePic = (ImageView) itemView.findViewById(R.id.img_pic);
        }

    }

    public final static class TwoPicViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mBaseInfo;
        public ImageView imgBigPic;

        public TwoPicViewHolder(View itemView) {
            super(itemView);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            imgBigPic = (ImageView) itemView.findViewById(R.id.img_pic);
        }

    }


    public final static class ThreePicViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mBaseInfo;
        public ImageView imgOnePic, imgTwoPic, imgThreePic;

        public ThreePicViewHolder(View itemView) {
            super(itemView);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            imgOnePic = (ImageView) itemView.findViewById(R.id.img_pic_1);
            imgTwoPic = (ImageView) itemView.findViewById(R.id.img_pic_2);
            imgThreePic = (ImageView) itemView.findViewById(R.id.img_pic_3);
        }

    }
}
