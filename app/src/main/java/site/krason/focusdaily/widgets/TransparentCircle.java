package site.krason.focusdaily.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Created by KCrason on 2017/1/4.
 * @email 535089696@qq.com
 */

public class TransparentCircle extends View {
    public TransparentCircle(Context context) {
        super(context);
    }

    public TransparentCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransparentCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TransparentCircle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint mPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#64000000"));
        int width = getWidth();
        canvas.drawCircle(width / 2, width / 2, width / 2, mPaint);
    }
}
