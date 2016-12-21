package site.krason.focusdaily.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kcrason.randomtransforview.BaseViewHolder;
import com.kcrason.randomtransforview.TransformAdapter;

import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.RelativeVideoInfo;
import site.krason.focusdaily.utils.KUtils;

/**
 * @author Created by KCrason on 2016/12/21.
 * @email 535089696@qq.com
 */

public class RecommendVideoTransformAdpter extends TransformAdapter<RelativeVideoInfo.GuidRelativeVideoInfoBean> {

    private Context mContext;

    public RecommendVideoTransformAdpter(BaseViewHolder baseViewHolder, Context context) {
        super(baseViewHolder);
        this.mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_llayout_recommend_video_list;
    }

    @Override
    public void setItemData(BaseViewHolder baseViewHolder, RelativeVideoInfo.GuidRelativeVideoInfoBean guidRelativeVideoInfoBean) {
        TextView txtTitle = baseViewHolder.getChildView(R.id.txt_title);
        TextView baseInfo = baseViewHolder.getChildView(R.id.txt_baseinfo);
        ImageView imageView = baseViewHolder.getChildView(R.id.img_pic);
        TextView txtDuration =baseViewHolder.getChildView(R.id.txt_time);
        txtTitle.setText(guidRelativeVideoInfoBean.getName());
        baseInfo.setText(guidRelativeVideoInfoBean.getColumnName() +"    "+ KUtils.transformPlayCount(guidRelativeVideoInfoBean.getPlayTime()));
        int size = guidRelativeVideoInfoBean.getFiles().size();
        Glide.with(mContext).load(guidRelativeVideoInfoBean.getFiles().get(size - 3).getMediaUrl()).into(imageView);
        txtDuration.setText(KUtils.formatVideoDuration(guidRelativeVideoInfoBean.getDuration()));
    }
}
