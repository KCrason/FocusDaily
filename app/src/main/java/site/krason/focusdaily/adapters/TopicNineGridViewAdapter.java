package site.krason.focusdaily.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import site.krason.focusdaily.bean.ImageBean;
import site.krason.focusdaily.widgets.NineGridView;

/**
 * @author Created by KCrason on 2017/1/17.
 * @email 535089696@qq.com
 */

public class TopicNineGridViewAdapter implements NineGridView.NineGridAdapter<ImageBean> {

    private List<ImageBean> mImageBeanList;
    private Context mContext;

    public TopicNineGridViewAdapter(List<ImageBean> imageBeen, Context context) {
        this.mImageBeanList = imageBeen;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mImageBeanList == null) {
            return 0;
        }
        return mImageBeanList.size();
    }

    @Override
    public ImageBean getItem(int position) {
        return mImageBeanList.get(position);
    }

    private ImageView getImageView() {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundColor(Color.parseColor("#f2f2f2"));
        return imageView;
    }

    @Override
    public View getView(int positon, View recycleView) {
        ImageView imageView;
        if (recycleView == null) {
            imageView = getImageView();
        } else {
            imageView = (ImageView) recycleView;
        }
        Glide.with(mContext).load(mImageBeanList.get(positon).getUrl()).into(imageView);
        return imageView;
    }
}
