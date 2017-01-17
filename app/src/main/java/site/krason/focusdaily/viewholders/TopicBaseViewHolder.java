package site.krason.focusdaily.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2017/1/17.
 * @email 535089696@qq.com
 */

public class TopicBaseViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageViewAvatar;

    public TextView getTextViewContent() {
        return mTextViewContent;
    }

    private TextView mTextViewContent;

    private TextView mTextViewName;

    public ImageView getImageViewAvatar() {
        return mImageViewAvatar;
    }

    public TextView getTextViewName() {
        return mTextViewName;
    }

    public TextView getTextViewTime() {
        return mTextViewTime;
    }

    public LinearLayout getLinearLayoutShare() {
        return mLinearLayoutShare;
    }

    public TextView getTextViewFavorites() {
        return mTextViewFavorites;
    }

    public TextView getTextViewComments() {
        return mTextViewComments;
    }

    private TextView mTextViewTime;

    private LinearLayout mLinearLayoutShare;

    private TextView mTextViewFavorites;
    private TextView mTextViewComments;

    public TopicBaseViewHolder(View itemView) {
        super(itemView);
        mImageViewAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        mTextViewName = (TextView) itemView.findViewById(R.id.txt_username);
        mTextViewTime = (TextView) itemView.findViewById(R.id.txt_createtime);
        mLinearLayoutShare = (LinearLayout) itemView.findViewById(R.id.llayout_share);
        mTextViewFavorites = (TextView) itemView.findViewById(R.id.txt_favorites_count);
        mTextViewComments = (TextView) itemView.findViewById(R.id.txt_comment_count);
        mTextViewContent = (TextView) itemView.findViewById(R.id.txt_content);
    }
}
