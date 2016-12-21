package site.krason.focusdaily.activities;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
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
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.bean.RelativeVideoInfo;
import site.krason.focusdaily.bean.SingleVideoInfo;
import site.krason.focusdaily.common.UrlUtils;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.viewholders.RecommendVideoTransformViewHolder;
import site.krason.focusdaily.widgets.MediaController;

/**
 * @author Created by KCrason on 2016/12/20.
 * @email 535089696@qq.com
 */

public class VideoActivity extends BaseActivity implements View.OnClickListener {

    private PLVideoView mPLVideoView;
    private MediaController mMediaController;
    private ListView mListView;

    private VideoCommentAdapter mVideoCommentAdapter;

    private String mGuid;

    private String mVideoPath;

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
        KNewBean.ItemBean itemBean = (KNewBean.ItemBean) getIntent().getSerializableExtra(RecommendedFragment.KEY_NEWS);
        mPLVideoView = (PLVideoView) findViewById(R.id.pl_video_view);
        RelativeLayout videoParent = (RelativeLayout) findViewById(R.id.rlayout_video_parent);
        setLoadingView(videoParent, itemBean);

        mListView = (ListView) findViewById(R.id.list_view);
        setHeaderVideoInfo(mListView);
        mVideoCommentAdapter = new VideoCommentAdapter();
        mListView.setAdapter(mVideoCommentAdapter);

        mMediaController = new MediaController(this);
        mMediaController.setVideoType(MediaController.VIDEO_TYPE_SINGLE);
        mPLVideoView.setMediaController(mMediaController);

        mGuid = itemBean.getLink().getUrl();
        getVideoDetailInfo(mGuid);
    }

    private View mMobileNetworkLoadingView, mNormalLoadingView;

    private void setLoadingView(RelativeLayout videoParent, KNewBean.ItemBean itemBean) {
        if (KUtils.Network.isMobile()) {
            mMobileNetworkLoadingView = getLayoutInflater().inflate(R.layout.include_loading_view_not_network, null);
            initMobileNetworkView(mMobileNetworkLoadingView, itemBean);
            videoParent.addView(mMobileNetworkLoadingView);
        } else {
            mNormalLoadingView = getLayoutInflater().inflate(R.layout.include_loading_view, null);
            videoParent.addView(mNormalLoadingView);
        }
        View loadingView = findViewById(R.id.llayout_loading_view);
        ImageView backImageView = (ImageView) loadingView.findViewById(R.id.img_pic);
        Glide.with(this).load(itemBean.getThumbnail()).into(backImageView);
        mPLVideoView.setBufferingIndicator(loadingView);
    }

    private TextView mVideoLenth, mVideoMB;
    private Button mBtnStartPlay;

    private void initMobileNetworkView(View view, KNewBean.ItemBean itemBean) {
        mVideoLenth = (TextView) view.findViewById(R.id.txt_time);
        mVideoMB = (TextView) view.findViewById(R.id.txt_size);
        mBtnStartPlay = (Button) view.findViewById(R.id.btn_start_play);
        mVideoLenth.setText(KUtils.formatVideoDuration(itemBean.getPhvideo().getLength()));
        mBtnStartPlay.setOnClickListener(this);
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
                JSONArray jsonArray = jsonObject.getJSONArray("singleVideoInfo");
                JSONObject singleVideoInfoObject = jsonArray.getJSONObject(0);
                SingleVideoInfo singleVideoInfo = JSON.parseObject(singleVideoInfoObject.toString(), SingleVideoInfo.class);
                if (mMediaController != null && mPLVideoView != null && singleVideoInfo != null) {
                    mMediaController.setVideoTitle(singleVideoInfo.getTitle());
                    mMediaController.setVideoPath(mPLVideoView, singleVideoInfo.getVideoURLHigh());
                    mPLVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
                    setSingleVideoBaseData(singleVideoInfo);
                    mVideoPath = singleVideoInfo.getVideoURLHigh();
                    if (!KUtils.Network.isMobile()) {
                        mPLVideoView.setVideoPath(mVideoPath);
                    }
                }
            }
        });
    }


    private void setSingleVideoBaseData(SingleVideoInfo singleVideoInfo) {
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
        mRecommendVideoTransformViewHolder = new RecommendVideoTransformViewHolder();
        mRecommendVideoTransformAdpter = new RecommendVideoTransformAdpter(mRecommendVideoTransformViewHolder, this);
        mRandomTransformView.setAdapter(mRecommendVideoTransformAdpter);
        mListView.addHeaderView(view);
        getGuidRelativeVideoDetailInfo();
    }

    private void getGuidRelativeVideoDetailInfo() {
        OkHttpUtils.get().url(UrlUtils.IFENG.GET_GUID_RELATIVE_VIDEO_LIST)
                .params(getParams(mGuid))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                RelativeVideoInfo relativeVideoInfo = JSON.parseObject(response, RelativeVideoInfo.class);
                if (mRecommendVideoTransformAdpter != null) {
                    mRecommendVideoTransformAdpter.setData(relativeVideoInfo.getGuidRelativeVideoInfo());
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_play:
                if (mPLVideoView != null && !TextUtils.isEmpty(mVideoPath)) {
                    if (mNormalLoadingView == null) {
                        //如果继续播放，设置正常的view
                        mNormalLoadingView = getLayoutInflater().inflate(R.layout.include_loading_view, null);
                    }
                    mPLVideoView.setBufferingIndicator(mNormalLoadingView);
                    mPLVideoView.setVideoPath(mVideoPath);
                }
                break;
        }
    }
}
