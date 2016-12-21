package site.krason.focusdaily.viewholders;

import android.view.View;

import com.kcrason.randomtransforview.BaseViewHolder;

/**
 * @author Created by KCrason on 2016/12/21.
 * @email 535089696@qq.com
 */

public class RecommendVideoTransformViewHolder extends BaseViewHolder {
    private View mView;

    @Override
    public <T extends View> T getChildView(int id) {
        return (T) mView.findViewById(id);
    }

    @Override
    public void setItemView(View view) {

        this.mView = view;
    }
}
