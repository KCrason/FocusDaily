package site.krason.focusdaily.widgets.recyclerview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class FooterView extends LinearLayout {

    private TextView mTextView;

    private ProgressBar mProgressBar;

    public FooterView(Context context) {
        super(context);
        initView(context);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FooterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_recycler_footer, this,false);
        mTextView = (TextView) view.findViewById(R.id.txt_message);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_load);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        this.setLayoutParams(layoutParams);
        addView(view);
    }

    public void resetFooterView() {
        mTextView.setText("加载中...");
        mProgressBar.setVisibility(VISIBLE);
    }

    public void setNetworkErrorText() {
        mTextView.setText("请检查网络设置:）");
        mProgressBar.setVisibility(GONE);
    }

    public void setLoadAllCompleteText() {
        mTextView.setText("我是有底线的O(∩_∩)O~");
        mProgressBar.setVisibility(GONE);
    }
}
