package site.krason.focusdaily.utils;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.adapters.VideoListAdapter;

/**
 * @author Created by KCrason on 2016/12/27.
 * @email 535089696@qq.com
 */

public class ViewHolderManage {

    private static ViewHolderManage mViewHolderManage;

    public static ViewHolderManage create() {
        if (mViewHolderManage == null) {
            mViewHolderManage = new ViewHolderManage();
        }
        return mViewHolderManage;
    }


    private List<VideoListAdapter.VideoViewHolder> mVideoViewHolders = new ArrayList<>();

    public void setViewHolder(VideoListAdapter.VideoViewHolder viewHolder) {
        mVideoViewHolders.add(viewHolder);
    }

    public void stopVideoPlay(){
        if (mVideoViewHolders != null) {
            for (int i = 0; i < mVideoViewHolders.size(); i++) {
                VideoListAdapter.VideoViewHolder videoViewHolder = mVideoViewHolders.get(i);
                videoViewHolder.getMediaControllerOfList().hide();
                videoViewHolder.getPLVideoTextureView().stopPlayback();
                videoViewHolder.getCoverView().setVisibility(View.VISIBLE);
            }
        }
    }


    public void pauseVideoPlay() {
        if (mVideoViewHolders != null) {
            for (int i = 0; i < mVideoViewHolders.size(); i++) {
                VideoListAdapter.VideoViewHolder videoViewHolder = mVideoViewHolders.get(i);
                videoViewHolder.getMediaControllerOfList().hide();
                videoViewHolder.getPLVideoTextureView().pause();
                videoViewHolder.getCoverView().setVisibility(View.VISIBLE);
            }
        }
    }
}
