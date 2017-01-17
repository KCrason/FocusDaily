package site.krason.focusdaily.viewholders;

import android.view.View;

import site.krason.focusdaily.R;
import site.krason.focusdaily.widgets.NineGridView;

/**
 * @author Created by KCrason on 2017/1/17.
 * @email 535089696@qq.com
 */

public class MorePictureViewHolder extends TopicBaseViewHolder {

    public NineGridView mNineGridView;

    public MorePictureViewHolder(View itemView) {
        super(itemView);
        mNineGridView = (NineGridView) itemView.findViewById(R.id.nine_grid_view);
    }
}
