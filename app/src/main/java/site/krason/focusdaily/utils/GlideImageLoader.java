package site.krason.focusdaily.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import site.krason.focusdaily.bean.ProjectBean;

/**
 * @author Created by KCrason on 2016/12/26.
 * @email 535089696@qq.com
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ProjectBean.PodItemsBean podItemsBean = (ProjectBean.PodItemsBean) path;
        Glide.with(context).load(podItemsBean.getThumbnail()).into(imageView);
    }
}
