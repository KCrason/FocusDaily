package site.krason.focusdaily.widgets;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.IntersterTagAdapter;
import site.krason.focusdaily.interfaces.OnIntersterClickListener;
import site.krason.focusdaily.utils.KUtils;

/**
 * Created by KCrason on 2016/12/17.
 */

public class DeletePopupWindow extends PopupWindow implements PopupWindow.OnDismissListener, View.OnClickListener {

    private Context mContext;

    private int windowHeight;

    private NoScrollGridView mNoScrollGridView;

    private Button mBtnNoInterster;

    public DeletePopupWindow(Context context) {
        this.mContext = context;
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_popup_delete, null);
        initViews(contentView);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        windowHeight = KUtils.dip2px(170);
        setHeight(windowHeight);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);
        setOnDismissListener(this);
    }

    private OnIntersterClickListener mOnIntersterClickListener;

    public DeletePopupWindow setOnIntersterClickListener(OnIntersterClickListener onIntersterClickListener) {
        this.mOnIntersterClickListener = onIntersterClickListener;
        return this;
    }

    private int mPosition;

    public DeletePopupWindow setPosition(int position) {
        this.mPosition = position;
        return this;
    }

    private IntersterTagAdapter mIntersterTagAdapter;

    private void initViews(View view) {
        mBtnNoInterster = (Button) view.findViewById(R.id.btn_not_interster);
        mBtnNoInterster.setOnClickListener(this);
        mNoScrollGridView = (NoScrollGridView) view.findViewById(R.id.noscroll_grid_view);
        mIntersterTagAdapter = new IntersterTagAdapter(view.getContext());
        mNoScrollGridView.setAdapter(mIntersterTagAdapter);
    }


    public DeletePopupWindow setTags(List<String> tags) {
        if (mIntersterTagAdapter != null) {
            mIntersterTagAdapter.setData(tags);
        }
        return this;
    }


    public void isShowInTop(View view) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(1.0f, 0.7f);
        valueAnimator.setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setWindowAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.start();
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        int distance = screenHeight - location[1] - view.getHeight();
        if (distance > windowHeight) {
            this.setAnimationStyle(R.style.popwin_anim_bottom_style);
            showAsDropDown(view, 0, 0);
        } else {
            this.setAnimationStyle(R.style.popwin_anim_top_style);
            showAtLocation(view, Gravity.NO_GRAVITY, 0, location[1] - windowHeight);
        }
    }


    public void showPopupWindow(View view) {
        isShowInTop(view);
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
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0.7f, 1.0f);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setWindowAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    @Override
    public void onClick(View view) {
        if (mOnIntersterClickListener != null) {
            dismiss();
            mOnIntersterClickListener.onIntersterClick(mPosition);
        }
    }
}
