package site.krason.focusdaily.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.LiveAdapter;
import site.krason.focusdaily.bean.LiveContentBean;
import site.krason.focusdaily.bean.LiveListBean;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2016/12/28.
 * @email 535089696@qq.com
 */

public class TextLiveActivity extends BaseActivity implements OnRecyclerLoadMoreLisener, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTxtNumber;
    private TextView mTxtDescription;
    private PLVideoTextureView mPLVideoTextureView;
    private KReyccleView mKReyccleView;
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;

    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_text_live;
    }

    private int mPage = 1;

    private void getListData() {
        OkHttpUtils.get().url("http://ichat.3g.ifeng.com/interface/get?lr_id=" + id + "&type=live&gv=5.4.1&av=5.4.1&uid=863055036432979&deviceid=863055036432979&proid=ifengnews&os=android_22&df=androidphone&vt=5&screen=1080x1920&publishid=6102&nw=4g").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSON.parseObject(response);
                if (jsonObject.containsKey("content")) {
                    List<LiveListBean> liveContentBeen = JSON.parseArray(jsonObject.getString("content"), LiveListBean.class);
                    mLiveAdapter.setData(liveContentBeen);
                }
            }
        });
    }

    private void getLiveInfo() {
        OkHttpUtils.get().url("http://ichat.3g.ifeng.com/interface/getlrinfo2?lr_id=" + id + "&gv=5.4.1&av=5.4.1&uid=863055036432979&deviceid=863055036432979&proid=ifengnews&os=android_22&df=androidphone&vt=5&screen=1080x1920&publishid=6102&nw=4g").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSON.parseObject(response);
                if (jsonObject.containsKey("content")) {
                    LiveContentBean
                            liveContentBean = JSON.parseObject(jsonObject.getString("content"), LiveContentBean.class);
                    if (mTxtDescription != null) {
                        mTxtNumber.setText("直播中....        在线人数：" + liveContentBean.getOnline_num());
                        mTxtDescription.setText(liveContentBean.getDescription());
                        if (liveContentBean.getExtra().isHasvideo()) {
                            mRelativeLayout.setVisibility(View.VISIBLE);
                            if (mImageView != null && !isFinishing()) {
                                Glide.with(TextLiveActivity.this).load(liveContentBean.getExtra().getVpic()).into(mImageView);
                                mPLVideoTextureView.setVideoPath(liveContentBean.getExtra().getVideoLive().split("##")[0]);
                            }
                        } else {
                            mRelativeLayout.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }


    private String id;

    private LiveAdapter mLiveAdapter;

    @Override
    public void initViews() {
        id = getIntent().getStringExtra(RecommendedFragment.KEY_NEWS);
        mTxtNumber = (TextView) findViewById(R.id.txt_number);
        mTxtDescription = (TextView) findViewById(R.id.txt_description);
        mTxtDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTxtDescription.getLineCount() <= 1) {
                    mTxtDescription.setMaxLines(10);
                } else {
                    mTxtDescription.setMaxLines(1);
                }
            }
        });

        mPLVideoTextureView = (PLVideoTextureView) findViewById(R.id.pl_video_texture_view);
        mKReyccleView = (KReyccleView) findViewById(R.id.recycle_view);
        mKReyccleView.setOnRecyclerLoadMoreListener(this);
        mKReyccleView.setLayoutManager(new LinearLayoutManager(this));
        mLiveAdapter = new LiveAdapter(this);
        mKReyccleView.setAdapter(mLiveAdapter);
        mImageView = (ImageView) findViewById(R.id.img_pic);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rlayout_cover_view);
        getLiveInfo();
        getListData();
    }

    @Override
    public void onRecyclerViewLoadMore() {
        mPage++;
        OkHttpUtils.get().url("http://ichat.3g.ifeng.com/interface/get?lr_id=" + id + "&page=" + mPage + "&type=live&gv=5.4.1&av=5.4.1&uid=863055036432979&deviceid=863055036432979&proid=ifengnews&os=android_22&df=androidphone&vt=5&screen=1080x1920&publishid=6102&nw=4g").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSON.parseObject(response);
                if (jsonObject.containsKey("content")) {
                    List<LiveListBean> liveContentBeen = JSON.parseArray(jsonObject.getString("content"), LiveListBean.class);
                    if (liveContentBeen.size() > 0) {
                        mLiveAdapter.addData(liveContentBeen);
                        mKReyccleView.setCurrentLoadComplete();
                    } else {
                        mKReyccleView.setAllLoadComplete();
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        getListData();
    }
}
