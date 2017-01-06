package site.krason.focusdaily.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.VideoListBean;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.utils.ViewHolderManage;
import site.krason.focusdaily.widgets.MediaControllerOfList;

/**
 * @author Created by KCrason on 2016/12/27.
 * @email 535089696@qq.com
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> implements
        PLMediaPlayer.OnSeekCompleteListener, PLMediaPlayer.OnErrorListener, PLMediaPlayer.OnBufferingUpdateListener {

    private Context mContext;

    private List<VideoListBean.ItemBean> mDataBeen = new ArrayList<>();

    public VideoListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<VideoListBean.ItemBean> strings) {
        ViewHolderManage.create().pauseVideoPlay();
        this.mDataBeen.clear();
        this.mDataBeen = strings;
        notifyDataSetChanged();
    }

    public void addData(List<VideoListBean.ItemBean> strings) {
        this.mDataBeen.addAll(strings);
        ViewHolderManage.create().pauseVideoPlay();
        notifyDataSetChanged();
    }


    @Override
    public VideoListAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_video_list, parent, false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(view);
        ViewHolderManage.create().setViewHolder(videoViewHolder);
        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(final VideoListAdapter.VideoViewHolder holder, int position) {
        final VideoListBean.ItemBean dataBean = mDataBeen.get(position);
        holder.mTextView.setText(dataBean.getTitle());
        holder.mPLVideoTextureView.setOnPreparedListener(new VideoListOnPreparedListener(holder));
        holder.mPLVideoTextureView.setOnBufferingUpdateListener(this);
        holder.mPLVideoTextureView.setOnErrorListener(this);
        holder.mPLVideoTextureView.setOnSeekCompleteListener(this);
        Glide.with(mContext).load(dataBean.getImage()).into(holder.mImageView);
        holder.mTxtLength.setText(KUtils.formatVideoDuration(dataBean.getDuration()));
        holder.itemView.setOnClickListener(new VideoItemClick(position, dataBean.getVideo_url(), dataBean.getTitle(), holder));
    }

    private int mCurPlayPosition = -1;


    class VideoListOnPreparedListener implements PLMediaPlayer.OnPreparedListener {
        private VideoViewHolder mVideoViewHolder;

        public VideoListOnPreparedListener(VideoViewHolder videoViewHolder) {
            this.mVideoViewHolder = videoViewHolder;
        }

        @Override
        public void onPrepared(PLMediaPlayer plMediaPlayer) {
            mVideoViewHolder.getCoverView().setVisibility(View.GONE);
            plMediaPlayer.start();
        }
    }


    @Override
    public void onSeekComplete(PLMediaPlayer plMediaPlayer) {

    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
        return false;
    }

    @Override
    public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int i) {

    }

    public final class VideoItemClick implements View.OnClickListener {

        private int mPosition;
        private String mVideoUrl;
        private String mTitlte;

        private VideoViewHolder mVideoViewHolder;

        public VideoItemClick(int position, String videoUrl, String title, VideoViewHolder videoViewHolder) {
            this.mVideoUrl = videoUrl;
            this.mPosition = position;
            this.mTitlte = title;
            this.mVideoViewHolder = videoViewHolder;
        }

        @Override
        public void onClick(View view) {
            if (KUtils.Network.isExistNetwork()) {
                if (mVideoViewHolder != null) {
                    if (mCurPlayPosition != mPosition) {
                        ViewHolderManage.create().pauseVideoPlay();
                        mCurPlayPosition = mPosition;
                        mVideoViewHolder.mMediaControllerOfList.setVideoTitle(mTitlte);
                        mVideoViewHolder.mPLVideoTextureView.setVideoPath(mVideoUrl);
                    }
                }
            } else {
                Toast.makeText(mContext, "网络不可用", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public int getItemCount() {
        if (mDataBeen != null) {
            return mDataBeen.size();
        }
        return 0;
    }


    public int getCurPlayPosition() {
        return mCurPlayPosition;
    }

    public final class VideoViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout getCoverView() {
            return mCoverView;
        }

        private RelativeLayout mCoverView;
        private PLVideoTextureView mPLVideoTextureView;
        private TextView mTextView;


        public PLVideoTextureView getPLVideoTextureView() {
            return mPLVideoTextureView;
        }


        private ImageView mImageView;
        private TextView mTxtLength;

        public MediaControllerOfList getMediaControllerOfList() {
            return mMediaControllerOfList;
        }

        private MediaControllerOfList mMediaControllerOfList;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mCoverView = (RelativeLayout) itemView.findViewById(R.id.rlayout_cover_view);
            mTextView = (TextView) itemView.findViewById(R.id.txt_title);
            mImageView = (ImageView) itemView.findViewById(R.id.img_pic);
            mTxtLength = (TextView) itemView.findViewById(R.id.txt_length);
            mPLVideoTextureView = (PLVideoTextureView) itemView.findViewById(R.id.pl_video_texture_view);
            mMediaControllerOfList = new MediaControllerOfList(mContext);
            mPLVideoTextureView.setMediaController(mMediaControllerOfList);
            mPLVideoTextureView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
            mPLVideoTextureView.setBufferingIndicator( itemView.findViewById(R.id.LoadingView));
            AVOptions options = new AVOptions();
            options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
            mPLVideoTextureView.setAVOptions(options);
        }
    }
}
