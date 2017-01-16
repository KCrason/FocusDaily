package site.krason.focusdaily.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kcrason.randomtransforview.OnTransformItemClickListener;
import com.kcrason.randomtransforview.RandomTransformView;
import com.pili.pldroid.player.widget.PLVideoView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.RecommendVideoTransformAdpter;
import site.krason.focusdaily.adapters.VideoCommentAdapter;
import site.krason.focusdaily.bean.RelativeVideoInfo;
import site.krason.focusdaily.bean.SingleVideoInfo;
import site.krason.focusdaily.common.UrlUtils;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.interfaces.PlayCallBack;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.viewholders.RecommendVideoTransformViewHolder;
import site.krason.focusdaily.widgets.MediaController;
import site.krason.focusdaily.widgets.MutilStatusVideoView;

/**
 * @author Created by KCrason on 2016/12/20.
 * @email 535089696@qq.com
 */

public class VideoActivity extends BaseActivity implements PlayCallBack, OnTransformItemClickListener {

    private PLVideoView mPLVideoView;
    private MediaController mMediaController;
    private ListView mListView;

    private VideoCommentAdapter mVideoCommentAdapter;

    private String mGuid;

    private String mVideoPath;

    private MutilStatusVideoView mMutilStatusVideoView;


    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void initViews() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mGuid = getIntent().getStringExtra(RecommendedFragment.KEY_NEWS);
        processExtraData();
    }

    private int mScreenWidth, mScreenHeight, mDefaultHeight;

    private void processExtraData() {
        mScreenWidth = KUtils.getScreenWidth();
        mScreenHeight = KUtils.getScreenHeight();
        mDefaultHeight = KUtils.dip2px(200);

        mPLVideoView = (PLVideoView) findViewById(R.id.pl_video_view);
        mMutilStatusVideoView = (MutilStatusVideoView) findViewById(R.id.mutil_status_video_view);
        mMutilStatusVideoView.setPlayCallBack(this);
        setCurLoadingView();

        mListView = (ListView) findViewById(R.id.list_view);
        setHeaderVideoInfo(mListView);
        mVideoCommentAdapter = new VideoCommentAdapter();
        mListView.setAdapter(mVideoCommentAdapter);

        mMediaController = new MediaController(this);
        mPLVideoView.setMediaController(mMediaController);
        getVideoDetailInfo(mGuid);
    }

    private void setCurLoadingView() {
        if (mMutilStatusVideoView != null) {
            if (KUtils.Network.isMobile()) {
                mMutilStatusVideoView.addMobileLoadingView();
            } else if (!KUtils.Network.isExistNetwork()) {
                mMutilStatusVideoView.addNotNetworkLoadingView();
            } else {
                mMutilStatusVideoView.addNormalLoadingView(mPLVideoView);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mPLVideoView != null && !mPLVideoView.isPlaying()) {
            mPLVideoView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onStop();
        if (mPLVideoView != null) {
            mPLVideoView.pause();
        }
    }

    private TextView mVideoTitle, mVideoTime, mVideoPraise, mVideoTread, mPlayCount;
    private LinearLayout mLinearLayoutRoot;

    private void setHeaderVideoInfo(ListView listView) {
        View view = getLayoutInflater().inflate(R.layout.header_list_video, null);
        mVideoTitle = (TextView) view.findViewById(R.id.txt_title);
        mVideoTime = (TextView) view.findViewById(R.id.txt_time);
        mVideoPraise = (TextView) view.findViewById(R.id.txt_praise);
        mVideoTread = (TextView) view.findViewById(R.id.txt_tread);
        mPlayCount = (TextView) view.findViewById(R.id.txt_play_count);
        mLinearLayoutRoot = (LinearLayout) view.findViewById(R.id.llayout_root);
        listView.addHeaderView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPLVideoView != null) {
            mPLVideoView.stopPlayback();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            //横屏
            if (mListView != null && mMutilStatusVideoView != null) {
                LinearLayout.LayoutParams layoutParams
                        = (LinearLayout.LayoutParams) mMutilStatusVideoView.getLayoutParams();
                layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
                layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
                mMutilStatusVideoView.setLayoutParams(layoutParams);
                mListView.setVisibility(View.GONE);
            }
        } else {
            //竖屏
            if (mListView != null && mMutilStatusVideoView != null) {
                LinearLayout.LayoutParams layoutParams
                        = (LinearLayout.LayoutParams) mMutilStatusVideoView.getLayoutParams();
                layoutParams.height = KUtils.dip2px(200);
                layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
                mMutilStatusVideoView.setLayoutParams(layoutParams);
                mListView.setVisibility(View.VISIBLE);
            }
        }
    }

    private SingleVideoInfo mSingleVideoInfo;

    private void getVideoDetailInfo(String guid) {
        OkHttpUtils.get().url(UrlUtils.IFENG.API_PHOENIXTV_DETAILS)
                .params(getParams(guid))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSON.parseObject(response);
                if (!jsonObject.containsKey("singleVideoInfo")) {
                    return;
                }
                JSONArray jsonArray = jsonObject.getJSONArray("singleVideoInfo");
                JSONObject singleVideoInfoObject = jsonArray.getJSONObject(0);
                mSingleVideoInfo = JSON.parseObject(singleVideoInfoObject.toString(), SingleVideoInfo.class);
                if (mMediaController != null && mPLVideoView != null && mSingleVideoInfo != null) {
                    mMediaController.setVideoTitle(mSingleVideoInfo.getTitle());
                    mMediaController.setVideoPath(mPLVideoView, mSingleVideoInfo.getVideoURLHigh());
                    mPLVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
                    setSingleVideoBaseData(mSingleVideoInfo);
                    mVideoPath = mSingleVideoInfo.getVideoURLHigh();
                    if (!KUtils.Network.isMobile()) {
                        mPLVideoView.setVideoPath(mVideoPath);
                    }
                }
            }
        });
    }


    private void setSingleVideoBaseData(SingleVideoInfo singleVideoInfo) {
        if (KUtils.Network.isMobile() && mMutilStatusVideoView != null) {
            mMutilStatusVideoView.setVideoBackground(singleVideoInfo.getLargeImgURL())
                    .setVideoDuration(singleVideoInfo.getVideoLength())
                    .setVideoSize(singleVideoInfo.getVideoSizeHigh());
        }

        if (mLinearLayoutRoot != null) {
            mLinearLayoutRoot.setVisibility(View.VISIBLE);
        }
        if (mVideoTitle != null) {
            mVideoTitle.setText(singleVideoInfo.getTitle());
        }
        if (mVideoTime != null) {
            mVideoTime.setText(singleVideoInfo.getVideoPublishTime() + "发布");
        }
        if (mVideoPraise != null) {
            mVideoPraise.setText(singleVideoInfo.getPraise());
        }
        if (mVideoTread != null) {
            mVideoTread.setText(singleVideoInfo.getTread());
        }
        if (mPlayCount != null) {
            mPlayCount.setText(KUtils.transformPlayCount(singleVideoInfo.getPlayTime()));
        }

        setRecommendTitleView();
    }


    private void setRecommendTitleView() {
        View view = getLayoutInflater().inflate(R.layout.header_list_recommend_title, null);
        mListView.addHeaderView(view);
        setRecommendVideoListView();
    }

    private RandomTransformView mRandomTransformView;
    private RecommendVideoTransformAdpter mRecommendVideoTransformAdpter;
    private RecommendVideoTransformViewHolder mRecommendVideoTransformViewHolder;

    private void setRecommendVideoListView() {
        View view = getLayoutInflater().inflate(R.layout.header_list_recommend_video, null);
        mRandomTransformView = (RandomTransformView) view.findViewById(R.id.random_transform_view);
        mRandomTransformView.setOnTransformItemClickListener(this);
        mRecommendVideoTransformViewHolder = new RecommendVideoTransformViewHolder();
        mRecommendVideoTransformAdpter = new RecommendVideoTransformAdpter(mRecommendVideoTransformViewHolder, this);
        mRandomTransformView.setAdapter(mRecommendVideoTransformAdpter);
        mListView.addHeaderView(view);
        getGuidRelativeVideoDetailInfo();
    }

    private RelativeVideoInfo mRelativeVideoInfo;

    private void getGuidRelativeVideoDetailInfo() {
        OkHttpUtils.get().url(UrlUtils.IFENG.GET_GUID_RELATIVE_VIDEO_LIST)
                .params(getParams(mGuid))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mRelativeVideoInfo = JSON.parseObject(response, RelativeVideoInfo.class);
                if (mRecommendVideoTransformAdpter != null) {
                    mRecommendVideoTransformAdpter.setData(mRelativeVideoInfo.getGuidRelativeVideoInfo());
                }
            }
        });
    }


    private Map<String, String> getParams(String guid) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("guid", guid);
        stringMap.put("gv", "5.4.0");
        stringMap.put("av", "5.4.0");
        stringMap.put("uid", "863055036432979");
        stringMap.put("deviceid", "863055036432979");
        stringMap.put("proid", "ifengnews");
        stringMap.put("os", "android_22");
        stringMap.put("df", "androidphone");
        stringMap.put("vt", "5");
        stringMap.put("screen", "1080x1920");
        stringMap.put("publishid", "6102");
        stringMap.put("nw", "wifi");
        return stringMap;
    }


    @Override
    public void onPlayCallBack() {
        if (mMutilStatusVideoView != null && mPLVideoView != null) {
            mMutilStatusVideoView.addNormalLoadingView(mPLVideoView);
            mMutilStatusVideoView.setVideoBackground(mSingleVideoInfo.getLargeImgURL());
            mPLVideoView.setVideoPath(mVideoPath);
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        if (mRelativeVideoInfo != null) {
            Intent intent = new Intent(this, VideoActivity.class);
            String guid = mRelativeVideoInfo.getGuidRelativeVideoInfo().get(position).getGuid();
            intent.putExtra(RecommendedFragment.KEY_NEWS, guid);
            startActivity(intent);
        }
    }
}
