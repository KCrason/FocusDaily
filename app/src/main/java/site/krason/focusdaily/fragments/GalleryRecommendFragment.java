package site.krason.focusdaily.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.GalleryRecommendAdapter;
import site.krason.focusdaily.bean.RecommendSlideBean;

/**
 * @author Created by KCrason on 2016/12/23.
 * @email 535089696@qq.com
 */

public class GalleryRecommendFragment extends Fragment {

    public final static String KEY_BUNDLE_INDEX_RECOMMEND = "key_bundle_index_recommend";

    private RecyclerView mRecyclerView;

    private List<RecommendSlideBean> mRecommendSlideBean;


    public static GalleryRecommendFragment instantce(List<RecommendSlideBean> recommendSlideBean) {
        GalleryRecommendFragment galleryRecommendFragment = new GalleryRecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_INDEX_RECOMMEND, (Serializable) recommendSlideBean);
        galleryRecommendFragment.setArguments(bundle);
        return galleryRecommendFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecommendSlideBean = (List<RecommendSlideBean>) getArguments().getSerializable(KEY_BUNDLE_INDEX_RECOMMEND);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grallery_recommend, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        GalleryRecommendAdapter galleryRecommendAdapter = new GalleryRecommendAdapter(getContext());
        mRecyclerView.setAdapter(galleryRecommendAdapter);
        if (mRecommendSlideBean != null) {
            galleryRecommendAdapter.setData(mRecommendSlideBean);
        }
    }
}
