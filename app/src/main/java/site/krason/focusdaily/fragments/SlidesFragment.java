package site.krason.focusdaily.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.SlidesBean;
import uk.co.senab.photoview.PhotoView;

/**
 * @author Created by KCrason on 2016/12/20.
 * @email 535089696@qq.com
 */

public class SlidesFragment extends Fragment {

    public final static String KEY_BUNDLE_INDEX = "key_bundle_index";
    private PhotoView mPhotoView;



    private SlidesBean mSlidesBean;

    public static SlidesFragment instantce(SlidesBean slidesBean) {
        SlidesFragment slidesFragment = new SlidesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_INDEX, slidesBean);
        slidesFragment.setArguments(bundle);
        return slidesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSlidesBean = (SlidesBean) getArguments().getSerializable(KEY_BUNDLE_INDEX);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slides, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoView = (PhotoView) view.findViewById(R.id.photo_view);

        Glide.with(getContext()).load(mSlidesBean.getImage()).into(mPhotoView);
    }


}
