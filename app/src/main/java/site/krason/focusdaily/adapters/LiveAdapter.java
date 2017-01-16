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
import site.krason.focusdaily.bean.LiveListBean;

/**
 * @author Created by KCrason on 2016/12/28.
 * @email 535089696@qq.com
 */

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.LiveListViewHolder> {


    private List<LiveListBean> mLiveListBeen = new ArrayList<>();

    private Context mContext;

    public LiveAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<LiveListBean> data) {
        if (this.mLiveListBeen != null) {
            this.mLiveListBeen.clear();
        }
        this.mLiveListBeen = data;
        notifyDataSetChanged();
    }

    public void addData(List<LiveListBean> liveListBeen) {
        this.mLiveListBeen.addAll(liveListBeen);
        notifyDataSetChanged();
    }


    @Override
    public LiveListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LiveListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_live, parent, false));
    }

    @Override
    public void onBindViewHolder(LiveListViewHolder holder, int position) {
        LiveListBean liveListBean = mLiveListBeen.get(position);
        holder.mTextViewContent.setText(liveListBean.getContent());
        holder.mTextViewTime.setText(liveListBean.getCreate_date());
        Glide.with(mContext).load(liveListBean.getHost_avatar()).asBitmap().into(holder.mImageViewAvatar);
        holder.mTextViewName.setText(liveListBean.getHost_name());
        if (liveListBean.getImg() != null && liveListBean.getImg().size() > 0) {
            Glide.with(mContext).load(liveListBean.getImg().get(0).getO_src()).asBitmap().into(holder.mImageViewPic);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if (mLiveListBeen == null) {
            return 0;
        }
        return mLiveListBeen.size();
    }

    public final class LiveListViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewContent;
        private ImageView mImageViewPic;
        private TextView mTextViewName;
        private ImageView mImageViewAvatar;
        private TextView mTextViewTime;

        public LiveListViewHolder(View itemView) {
            super(itemView);
            mTextViewContent = (TextView) itemView.findViewById(R.id.txt_content);
            mImageViewPic = (ImageView) itemView.findViewById(R.id.img_pic);
            mTextViewName = (TextView) itemView.findViewById(R.id.txt_host_name);
            mImageViewAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            mTextViewTime = (TextView) itemView.findViewById(R.id.txt_time);
        }
    }

}
