package site.krason.focusdaily.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRealItemClickCallBack;

/**
 * @author Created by KCrason on 2016/12/16.
 * @email 535089696@qq.com
 */

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.HandPickViewHolder> {

    private Context mContext;

    private List<KNewBean> mDataBeen = new ArrayList<>();
    private OnRealItemClickCallBack<KNewBean> mDataBeanOnRealItemClickCallBack;

    public JokeAdapter(Context context, OnRealItemClickCallBack dataBeanOnRealItemClickCallBack) {
        this.mContext = context;
        this.mDataBeanOnRealItemClickCallBack = dataBeanOnRealItemClickCallBack;
    }

    public void setData(List<KNewBean> strings) {
        this.mDataBeen.clear();
        this.mDataBeen = strings;
        notifyDataSetChanged();
    }

    public void addData(List<KNewBean> strings) {
        this.mDataBeen.addAll(strings);
        notifyDataSetChanged();
    }


    @Override
    public HandPickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_joke, parent, false);
        return new HandPickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HandPickViewHolder holder, int position) {
//        KNewBean dataBean = mDataBeen.get(position);
//        holder.itemView.setOnClickListener(new OnRecyclerItemClick(dataBean));
//        holder.mTextView.setText(dataBean.getTitle());
//        holder.mBaseInfo.setText(dataBean.getSource() + "  " + KUtils.betweenOf2Days(dataBean.getCTime()));
//        if (dataBean.getImageCount() >= 1) {
//            holder.mImageView.setVisibility(View.VISIBLE);
//            Glide.with(mContext).load(dataBean.getCoverUrl()).asBitmap().into(holder.mImageView);
//        } else {
//            holder.mImageView.setVisibility(View.GONE);
//        }
    }


    public final class OnRecyclerItemClick implements View.OnClickListener {

        private KNewBean mDataBean;

        public OnRecyclerItemClick(KNewBean dataBean) {
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
    public int getItemCount() {
        if (mDataBeen != null) {
            return mDataBeen.size();
        }
        return 0;
    }

    public final class HandPickViewHolder extends RecyclerView.ViewHolder {
        public TextView mBaseInfo;
        private TextView mTextView;
        private ImageView mImageView;

        public HandPickViewHolder(View itemView) {
            super(itemView);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            mTextView = (TextView) itemView.findViewById(R.id.txt_title);
            mImageView = (ImageView) itemView.findViewById(R.id.img_pic);
        }
    }

}
