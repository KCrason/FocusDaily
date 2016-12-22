package site.krason.focusdaily.widgets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pili.pldroid.player.widget.PLVideoView;

import site.krason.focusdaily.R;
import site.krason.focusdaily.interfaces.PlayCallBack;
import site.krason.focusdaily.utils.KUtils;

/**
 * @author Created by KCrason on 2016/12/22.
 * @email 535089696@qq.com
 */

public class MutilStatusVideoView extends FrameLayout implements View.OnClickListener {

    public MutilStatusVideoView(Context context) {
        super(context);
        initVideoView(context);
    }

    public MutilStatusVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public MutilStatusVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MutilStatusVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initVideoView(context);
    }

    private Context mContext;
    private View mNormalLoadingView, mNotNetworkLoadingView, mMobleLoadingView;

    private void initVideoView(Context context) {
        this.mContext = context;
    }

    public void addMobileLoadingView() {
        addView(createMobileNetworkView());
    }

    public void addNotNetworkLoadingView() {
        addView(createNotNetworkView());
    }

    public void addNormalLoadingView(PLVideoView plVideoView) {
        addView(createNormalView());
        setLoadingView(plVideoView);
    }

    public void setLoadingView(PLVideoView plVideoView) {
        if (plVideoView != null && mNormalLoadingView != null) {
            plVideoView.setBufferingIndicator(mNormalLoadingView);
        }
    }

    private View createNormalView() {
        mNormalLoadingView = getLayoutInflater().inflate(R.layout.include_loading_view, null);
        mVideoBackground = (ImageView) mNormalLoadingView.findViewById(R.id.img_pic);
        return mNormalLoadingView;
    }


    private View createNotNetworkView() {
        mNotNetworkLoadingView = getLayoutInflater().inflate(R.layout.include_loading_view_not_network, null);
        mNotNetworkLoadingView.findViewById(R.id.btn_check_network).setOnClickListener(this);
        return mNotNetworkLoadingView;
    }


    private ImageView mVideoBackground;
    private TextView mVideoDuration, mVideoSize;

    private View createMobileNetworkView() {
        mMobleLoadingView = getLayoutInflater().inflate(R.layout.include_loading_view_mobile_network, null);
        mVideoDuration = (TextView) mMobleLoadingView.findViewById(R.id.txt_time);
        mVideoSize = (TextView) mMobleLoadingView.findViewById(R.id.txt_size);
        mVideoBackground = (ImageView) mMobleLoadingView.findViewById(R.id.img_pic);
        mMobleLoadingView.findViewById(R.id.btn_start_play).setOnClickListener(this);
        return mMobleLoadingView;
    }


    public MutilStatusVideoView setVideoBackground(String imageUrl) {
        if (mVideoBackground != null) {
            Glide.with(mContext).load(imageUrl).into(mVideoBackground);
        }
        return this;
    }

    public MutilStatusVideoView setVideoDuration(String duration) {
        if (mVideoDuration != null) {
            mVideoDuration.setText("视频时长 " + duration);
        }
        return this;
    }

    public MutilStatusVideoView setVideoSize(int videoSize) {
        if (mVideoSize != null) {
            mVideoSize.setText("流量 约" + videoSize / 1024 + "MB");
        }
        return this;
    }

    private PlayCallBack mPlayCallBack;

    public void setPlayCallBack(PlayCallBack playCallBack) {
        this.mPlayCallBack = playCallBack;
    }

    private LayoutInflater getLayoutInflater() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return layoutInflater;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_check_network:
                if (KUtils.Network.isExistNetwork()) {
                    if (mNotNetworkLoadingView != null) {
                        removeView(mNotNetworkLoadingView);
                    }
                }
                break;
            case R.id.btn_start_play:
                if (mMobleLoadingView != null) {
                    removeView(mMobleLoadingView);
                }
                if (mNotNetworkLoadingView != null) {
                    removeView(mNotNetworkLoadingView);
                }
                if (mPlayCallBack != null) {
                    mPlayCallBack.onPlayCallBack();
                }
                break;
        }
    }
}
