package site.krason.focusdaily.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import site.krason.focusdaily.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author Created by KCrason on 2016/12/30.
 * @email 535089696@qq.com
 */

public class PreviewImagesAdapter extends PagerAdapter {

    private Context mContext;

    private String mUrls[] = new String[100];

    public PreviewImagesAdapter(Context context, String urls[]) {
        this.mContext = context;
        this.mUrls = urls;
    }

    @Override
    public int getCount() {
        if (mUrls != null) {
            return mUrls.length;
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_images, null);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v1) {
                ((Activity)mContext).finish();
            }
        });
        Glide.with(mContext).load(mUrls[position]).into(photoView);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
