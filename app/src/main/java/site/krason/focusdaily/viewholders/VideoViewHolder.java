package site.krason.focusdaily.viewholders;

import android.view.View;
import android.widget.ImageView;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2017/1/17.
 * @email 535089696@qq.com
 */

public class VideoViewHolder extends TopicBaseViewHolder {

    public ImageView mImageView;

    public VideoViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.img_pic);
    }
}
