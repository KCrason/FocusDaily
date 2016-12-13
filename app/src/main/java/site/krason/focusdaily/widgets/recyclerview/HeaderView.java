package site.krason.focusdaily.widgets.recyclerview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class HeaderView extends FrameLayout {
    public HeaderView(Context context) {
        super(context);
        initview(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initview(context);
    }

    private void initview(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_recycler_header,null);
        addView(view);
    }
}
