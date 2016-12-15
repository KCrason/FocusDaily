package site.krason.focusdaily.widgets.recyclerview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2016/12/14.
 * @email 535089696@qq.com
 */

public class MultiStatusView extends FrameLayout {
    public MultiStatusView(Context context) {
        super(context);
    }

    public MultiStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MultiStatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private TextView mTextView1, mTextView2;

    public void cleanAnimation() {
        if (mTextView1 != null) {
            mTextView1.clearAnimation();
        }
        if (mTextView2 != null) {
            mTextView2.clearAnimation();
        }
    }


    public void addRootView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_root_loading, null);
        mTextView1 = (TextView) view.findViewById(R.id.txt_1);
        mTextView2 = (TextView) view.findViewById(R.id.txt_2);
        addView(view);
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.4f, 1.0f);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.4f, 1.0f);
        alphaAnimation1.setDuration(700);
        alphaAnimation2.setDuration(900);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        alphaAnimation2.setRepeatCount(Animation.INFINITE);
        alphaAnimation2.setRepeatMode(Animation.REVERSE);
        mTextView1.startAnimation(alphaAnimation1);
        mTextView2.startAnimation(alphaAnimation2);
    }

    public MultiStatusView addBaseView(View view) {
        this.addView(view);
        return this;
    }
}
