package site.krason.focusdaily.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import site.krason.focusdaily.R;
import site.krason.focusdaily.utils.KUtils;

/**
 * Created by KCrason on 2016/12/17.
 */

public class DeletePopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    private Context mContext;

    private int windowHeight;

    public DeletePopupWindow(Context context) {
        this.mContext = context;
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_popup_delete, null);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        windowHeight = KUtils.dip2px(150);
        setHeight(windowHeight);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setAnimationStyle(R.style.popwin_anim_style);
        setOutsideTouchable(true);
        setOnDismissListener(this);
    }


    public boolean isShowInTop(View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        int distance = screenHeight - location[1] - view.getHeight();
        if (distance > windowHeight) {
            return false;
        } else {
            return true;
        }
    }


    public void showPopupWindow(View view) {
        isShowInTop(view);
        setWindowAlpha(0.7f);
        showAsDropDown(view);
        update();
    }


    private WindowManager.LayoutParams layoutParams;

    private void setWindowAlpha(float alpha) {
        Window window = ((Activity) mContext).getWindow();
        layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }

    @Override
    public void onDismiss() {
        setWindowAlpha(1.0f);
    }
}
