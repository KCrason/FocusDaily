package site.krason.focusdaily.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.activities.PreviewImagesActivty;
import site.krason.focusdaily.bean.ShortNewsBean;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRealItemClickCallBack;

/**
 * @author Created by KCrason on 2016/12/16.
 * @email 535089696@qq.com
 */

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.HandPickViewHolder> {

    private Context mContext;

    private List<ShortNewsBean> mDataBeen = new ArrayList<>();
    private OnRealItemClickCallBack<ShortNewsBean> mDataBeanOnRealItemClickCallBack;

    public JokeAdapter(Context context, OnRealItemClickCallBack dataBeanOnRealItemClickCallBack) {
        this.mContext = context;
        this.mDataBeanOnRealItemClickCallBack = dataBeanOnRealItemClickCallBack;
    }

    public void setData(List<ShortNewsBean> strings) {
        this.mDataBeen.clear();
        this.mDataBeen = strings;
        notifyDataSetChanged();
    }

    public void addData(List<ShortNewsBean> strings) {
        this.mDataBeen.addAll(strings);
        notifyDataSetChanged();
    }


    @Override
    public HandPickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_joke, parent, false);
        return new HandPickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HandPickViewHolder holder, int position) {
        final ShortNewsBean dataBean = mDataBeen.get(position);
        holder.itemView.setOnClickListener(new OnRecyclerItemClick(dataBean));
        if (TextUtils.isEmpty(dataBean.getContent())) {
            holder.mImageView.setVisibility(View.GONE);
        } else {
            holder.mImageView.setVisibility(View.VISIBLE);
            holder.mTextView.setText(dataBean.getContent());
        }


        holder.mTxtPraise.setText(dataBean.getPraise());
        holder.mTxtTread.setText(dataBean.getTread());
        if (dataBean.getImg() == null || dataBean.getImg().size() <= 0) {
            holder.mImageView.setVisibility(View.GONE);
        } else {
            holder.mImageView.setVisibility(View.VISIBLE);
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PreviewImagesActivty.class);
                    intent.putExtra("key_urls", new String[]{dataBean.getImg().get(0).getUrl()});
                    intent.putExtra("key_position", 0);
                    mContext.startActivity(intent);
                }
            });
            setImageSize(holder.mImageView, dataBean.getImg().get(0).getSize().getWidth(), dataBean.getImg().get(0).getSize().getHeight());
            Glide.with(mContext).load(dataBean.getImg().get(0).getUrl()).asBitmap().into(holder.mImageView);
        }
    }

    private void setImageSize(ImageView imageView, String width, String height) {
        float scal = Integer.parseInt(height) / Float.parseFloat(width);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = (int) (scal * KUtils.getScreenWidth());
        imageView.setLayoutParams(layoutParams);
    }


    public final class OnRecyclerItemClick implements View.OnClickListener {

        private ShortNewsBean mDataBean;

        public OnRecyclerItemClick(ShortNewsBean dataBean) {
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
        private TextView mTextView;
        private ImageView mImageView;
        private TextView mTxtPraise;
        private TextView mTxtTread;

        public HandPickViewHolder(View itemView) {
            super(itemView);
            mTxtPraise = (TextView) itemView.findViewById(R.id.txt_praise);
            mTxtTread = (TextView) itemView.findViewById(R.id.txt_tread);
            mTextView = (TextView) itemView.findViewById(R.id.txt_title);
            mImageView = (ImageView) itemView.findViewById(R.id.img_pic);
        }
    }

}
