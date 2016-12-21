package site.krason.focusdaily.activities;

import android.content.Context;
import android.content.Intent;

import com.pili.pldroid.player.widget.PLVideoView;

import site.krason.focusdaily.R;
import site.krason.focusdaily.widgets.MediaController;

/**
 * @author Created by KCrason on 2016/10/27.
 * @email 535089696@qq.com
 */

public class FullScreenPlayVideoActivity extends BaseActivity {

    private static final String KEY_CURRENT_DURATION = "key_current_duration";
    private PLVideoView mPLVideoView;

    public final static String KEY_PATH = "key_video_path";


    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    public static void actionStart(Context context, String videoPath, long currentDuration) {
        Intent intent = new Intent(context, FullScreenPlayVideoActivity.class);
        intent.putExtra(KEY_PATH, videoPath);
        intent.putExtra(KEY_CURRENT_DURATION, currentDuration);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPLVideoView != null) {
            mPLVideoView.stopPlayback();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_full_screen_play_video;
    }

    @Override
    public void initViews() {
        mPLVideoView = (PLVideoView) findViewById(R.id.pl_video_view);
        mPLVideoView.setMediaController(new MediaController(this));
        if (getIntent() != null) {
            mPLVideoView.setVideoPath(getIntent().getStringExtra(KEY_PATH));
            mPLVideoView.seekTo(getIntent().getLongExtra(KEY_CURRENT_DURATION, 0));
        }
    }
}
