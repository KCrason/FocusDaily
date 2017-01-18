package site.krason.focusdaily.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.ImageBean;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author Created by KCrason on 2016/12/30.
 * @email 535089696@qq.com
 */

public class PreviewImagesAdapter extends PagerAdapter {

    private Context mContext;

    private List<ImageBean> imageBeanList = new ArrayList<>();

    public PreviewImagesAdapter(Context context, List<ImageBean> imageBeanList) {
        this.mContext = context;
        this.imageBeanList = imageBeanList;
    }

    @Override
    public int getCount() {
        if (imageBeanList != null) {
            return imageBeanList.size();
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
        WebView webView = (WebView) view.findViewById(R.id.webview);
        ImageBean imageBean = imageBeanList.get(position);
        if (imageBean.getHeight() > 800 || imageBean.getUrl().endsWith("gif")) {
            photoView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("<html><body style=\"margin:0px; padding:0px;background-color:#000000\">");
            stringBuffer.append("<img src=" + imageBean.getUrl() + " width=\"100%\" >");
            stringBuffer.append("</body></html>");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadData(stringBuffer.toString(), "text/html; charset=UTF-8", null);
            webView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity) mContext).finish();
                }
            });
        } else {
            photoView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float v, float v1) {
                    ((Activity) mContext).finish();
                }
            });
            Glide.with(mContext).load(imageBeanList.get(position).getUrl()).into(photoView);
        }
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
