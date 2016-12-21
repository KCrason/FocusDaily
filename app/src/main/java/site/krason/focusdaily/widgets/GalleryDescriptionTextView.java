package site.krason.focusdaily.widgets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * @author Created by KCrason on 2016/12/21.
 * @email 535089696@qq.com
 */

public class GalleryDescriptionTextView extends TextView {

    public GalleryDescriptionTextView(Context context) {
        super(context);
        init(context);
    }

    public GalleryDescriptionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GalleryDescriptionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private int mViewTouthSlop;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GalleryDescriptionTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    private void init(Context context) {
        mViewTouthSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mStartX = event.getX();
//                mStartY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float xDistance = event.getX() - mStartX;
//                float yDistance = event.getY() - mStartY;
//                Log.d("KCrason", xDistance + "//" + yDistance);
//                if (Math.abs(xDistance) > mViewTouthSlop && Math.abs(xDistance) > Math.abs(yDistance)) {
//                    Log.d("KCrason", "return false");
//                    return false;
//                }
//                Log.d("KCrason", "break");
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                float xDistanceUp = event.getX() - mStartX;
//                float yDistanceUp = event.getY() - mStartY;
//                Log.d("KCrason", xDistanceUp + "%%" + yDistanceUp);
//                if (Math.abs(xDistanceUp) > mViewTouthSlop) {
//                    return false;
//                } else if (Math.abs(yDistanceUp) > mViewTouthSlop) {
//                    break;
//                }
//        }
//        return super.dispatchTouchEvent(event);
//    }

    private float mStartX;
    private float mStartY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 回到原始位置
     */
    public void setScrollToTop() {
        this.scrollTo(0, 0);
    }
}
