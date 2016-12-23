package site.krason.focusdaily.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pili.pldroid.player.IMediaController;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;

import java.util.Locale;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2016/10/27.
 * @email 535089696@qq.com
 */

public class MediaController extends FrameLayout implements IMediaController, View.OnClickListener, SeekBar.OnSeekBarChangeListener
        , PLMediaPlayer.OnCompletionListener, PLMediaPlayer.OnErrorListener {

    private MediaPlayerControl mMediaPlayerControl;

    private Context mContext;

    private TextView mVideoTitle;

    private ImageView mVideoPlayControl;

    private TextView mLeftTime;
    private TextView mRightTime;
    private SeekBar mVideoProgress;
    private ImageView mExpand;

    private ImageView mBack;

    /**
     * 用于判断ControlView是否已经显示
     */
    private boolean isControlViewShowing;

    private int mDefaultTimeOut = 3000;


    private final static int MSG_HIDE = 0x01;
    private final static int MSG_UPDATA_TIME = 0x02;

    private View mControlView;

    public final static int VIDEO_TYPE_SINGLE = 0;
    public final static int VIDEO_TYPE_LIST = 1;

    private int mVideoType = VIDEO_TYPE_SINGLE;

    private boolean mDefaultShowMediaController = false;

    public MediaController(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public void setVideoType(int videoType) {
        this.mVideoType = videoType;
    }

    private String mVideoPath;

    private PLVideoView mPLVideoView;

    public void setVideoPath(PLVideoView plVideoView, String videoPath) {
        this.mVideoPath = videoPath;
        this.mPLVideoView = plVideoView;
    }

    public MediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setVideoTitle(String title) {
        if (mVideoTitle != null) {
            mVideoTitle.setText(title);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MediaController(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context) {
        if (mControlView == null) {
            if (mVideoType == VIDEO_TYPE_SINGLE) {
                mControlView = LayoutInflater.from(context).inflate(R.layout.view_media_controller_single, this, false);
            } else {
                mControlView = LayoutInflater.from(context).inflate(R.layout.view_media_controller, this, false);
            }
            initControllerView(mControlView);
        }
        removeAllViews();
        addView(mControlView);
    }

    private void setControllerViewVisible(int isVisible) {
        if (mControlView != null) {
            mControlView.setVisibility(isVisible);
        }
    }

    private void initControllerView(View view) {
        if (mVideoType == VIDEO_TYPE_LIST) {
            mVideoTitle = (TextView) view.findViewById(R.id.txt_video_name);
        }
        if (mVideoType == VIDEO_TYPE_SINGLE) {
            mBack = (ImageView) view.findViewById(R.id.img_back);
            mBack.setOnClickListener(this);
        }
        mVideoProgress = (SeekBar) view.findViewById(R.id.pb_video_progress);
        mVideoProgress.setOnSeekBarChangeListener(this);
        mLeftTime = (TextView) view.findViewById(R.id.txt_left_time);
        mRightTime = (TextView) view.findViewById(R.id.txt_right_time);
        mVideoPlayControl = (ImageView) view.findViewById(R.id.img_play_control);
        mVideoPlayControl.setOnClickListener(this);
        mExpand = (ImageView) view.findViewById(R.id.img_landscape);
        mExpand.setOnClickListener(this);
    }


    @Override
    public void setMediaPlayer(MediaPlayerControl mediaPlayerControl) {
        this.mMediaPlayerControl = mediaPlayerControl;
    }


    @Override
    public void show() {
        if (!mDefaultShowMediaController) {
            mDefaultShowMediaController = true;
            return;
        }
        if (!isShowing()) {
            show(mDefaultTimeOut);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_HIDE:
                    hide();
                    break;
                case MSG_UPDATA_TIME:
                    setTime(mLeftTime, mMediaPlayerControl.getCurrentPosition());
                    if (isShowing()) {
                        mHandler.sendMessageDelayed(createMessage(), 1000);
                    }
                    break;
            }
        }
    };

    private Message createMessage() {
        if (mMediaPlayerControl == null) {
            return null;
        }
        Message message = new Message();
        message.what = MSG_UPDATA_TIME;
        return message;
    }

    private boolean isAddViewToAnchor;

    /**
     * 设置ControlView的显示超时时间
     * 当timeout = 0时，一直显示。
     *
     * @param timeout
     */
    @Override
    public void show(int timeout) {
        if (mAnchorView != null) {
            if (!isAddViewToAnchor) {
                ((ViewGroup) mAnchorView).addView(this);
                isAddViewToAnchor = true;
            }
            setControllerViewVisible(VISIBLE);
            isControlViewShowing = true;
            updataVideoProgress();
        }
        if (timeout != 0) {
            mHandler.sendEmptyMessageDelayed(MSG_HIDE, timeout);
        }
    }

    private long mDuration;

    private void updataVideoProgress() {
        if (mAnchorView == null || mControlView == null || mVideoProgress == null) {
            return;
        }
        if (mMediaPlayerControl != null) {
            mDuration = mMediaPlayerControl.getDuration();
            setTime(mLeftTime, mMediaPlayerControl.getCurrentPosition());
            setTime(mRightTime, mDuration);
            mHandler.sendMessage(createMessage());
        }
    }

    private int calculationHour(long duration) {
        int hour = (int) (duration / 1000 / 3600);
        return hour;
    }

    private int calculationMinute(long duration, int hour) {
        int minute = (int) ((duration / 1000 - (3600 * hour)) / 60);
        return minute;
    }

    private int calculationSecond(long duration, int hour, int minute) {
        int second = (int) ((duration / 1000 - (3600 * hour) - (60 * minute)));
        return second;
    }

    private void setVideoProgress() {
        int percentage = (int) (mMediaPlayerControl.getCurrentPosition() / (float) mMediaPlayerControl.getDuration() * 100);
        mVideoProgress.setProgress(percentage + 1);

    }

    private void setBufferProgress() {
        mVideoProgress.setSecondaryProgress(mMediaPlayerControl.getBufferPercentage() + 1);
    }


    private void setTime(TextView textTime, long duration) {
        int hours = calculationHour(duration);
        int minutes = calculationMinute(duration, hours);
        int seconds = calculationSecond(duration, hours, minutes);
        String time;
        if (hours == 0) {
            time = String.format(Locale.US, "%02d:%02d", minutes, seconds)
                    .toString();
        } else {
            time = String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        }
        if (textTime != null) {
            textTime.setText(time);
        }
        setBufferProgress();
        setVideoProgress();
    }

    /**
     * 此方法不会导致sdk的回调
     * 如果此时ControlView已经显示，那么手动也可导致该方法回调。
     */
    @Override
    public void hide() {
        if (mControlView != null && isShowing()) {
            setControllerViewVisible(INVISIBLE);
            isControlViewShowing = false;
            /**
             * 在这里需要加上一个remove消息的操作，因为如果用户切换为手动控制时，
             * 我们需要将自动隐藏的延时消息给从消息队列移除。
             */
            mHandler.removeMessages(MSG_HIDE);
        }
    }

    @Override
    public boolean isShowing() {
        return isControlViewShowing;
    }

    private View mAnchorView;

    @Override
    public void setAnchorView(View view) {
        this.mAnchorView = view;
    }

    @Override
    public void onClick(View view) {
        Configuration mConfiguration = getResources().getConfiguration(); //获取设置的配置信息
        Activity activity = (Activity) mContext;
        switch (view.getId()) {
            case R.id.img_play_control:
                playControl();
                break;
            case R.id.img_back:
                if (mConfiguration.orientation == mConfiguration.ORIENTATION_PORTRAIT) {
                    //如果是竖屏，则关闭当前页面
                    activity.finish();
                } else {
                    //如果是横屏，则返回为竖屏
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
            case R.id.img_landscape:
                if (mMediaPlayerControl != null) {
                    if (mConfiguration.orientation == mConfiguration.ORIENTATION_PORTRAIT) {
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
                break;
        }
    }


    /**
     * 控制播放和暂停
     */
    private void playControl() {
        if (mMediaPlayerControl.isPlaying()) {
            stopPlay();
        } else {
            startPlay();
        }
    }

    private void startPlay() {
        if (mMediaPlayerControl != null && mVideoPlayControl != null) {
            mHandler.sendMessageDelayed(createMessage(), 1000);
            mVideoPlayControl.setImageResource(R.drawable.playing);
            mMediaPlayerControl.start();
        }
    }

    public void stopPlay() {
        if (mMediaPlayerControl != null && mVideoPlayControl != null) {
            mVideoPlayControl.setImageResource(R.drawable.stop);
            mMediaPlayerControl.pause();
            mHandler.removeMessages(MSG_UPDATA_TIME);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        /**
         * formUser参数代表的是是否是用户滑动，如果是则进行视频的快进操作
         */
        if (fromUser) {
            hide();
            final long curDuration = (mDuration * progress) / 100;
            mMediaPlayerControl.seekTo(curDuration);
            userHandlerToMessage();
        }
    }

    public void userHandlerToMessage() {
        mHandler.sendMessageDelayed(createMessage(), 1000);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeMessages(MSG_UPDATA_TIME);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {

    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
        return false;
    }
}
