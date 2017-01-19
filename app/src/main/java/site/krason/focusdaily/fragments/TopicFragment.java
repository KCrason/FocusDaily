package site.krason.focusdaily.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.TopicAdapter;
import site.krason.focusdaily.bean.ImageBean;
import site.krason.focusdaily.bean.TopicBean;
import site.krason.focusdaily.utils.ACache;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.recyclerview.KReyccleView;
import site.krason.focusdaily.widgets.recyclerview.MultiStatusView;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnScrollStatusListener;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRecyclerLoadMoreLisener;

/**
 * @author Created by KCrason on 2017/1/17.
 * @email 535089696@qq.com
 */

public class TopicFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        OnRecyclerLoadMoreLisener, OnScrollStatusListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private KReyccleView mKReyccleView;

    private TopicAdapter mTopicAdapter;
    private ImageView mImageViewRefresh;
    private MultiStatusView mMultiStatusView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    public void removeRootView() {
        if (mMultiStatusView != null && mMultiStatusView.getChildCount() >= 2) {
            mMultiStatusView.setIsCanRefresh(true);
            mMultiStatusView.cleanAnimation();
            mMultiStatusView.removeViewAt(1);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view);
    }

    private void initFragment(View view) {
        mMultiStatusView = (MultiStatusView) view.findViewById(R.id.mutil_status_view);
        mMultiStatusView.addRootView(getContext());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#ed4040"));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mImageViewRefresh = (ImageView) view.findViewById(R.id.img_refresh);
        mImageViewRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KUtils.showSwipeRefresh(mSwipeRefreshLayout);
                startAnimation();
                mKReyccleView.scrollToPosition(0);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onRefresh();
                    }
                }, 2000);
            }
        });
        mKReyccleView = (KReyccleView) view.findViewById(R.id.recycle_view);
        mKReyccleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mKReyccleView.setOnRecyclerLoadMoreListener(this);
        mKReyccleView.setOnLoadImageListener(this);
        mTopicAdapter = new TopicAdapter(getContext());
        mKReyccleView.setAdapter(mTopicAdapter);
        String response = ACache.get(getActivity()).getAsString("keys_topic");
        if (!TextUtils.isEmpty(response)) {
            praseData(response, false);
        }
        KUtils.showSwipeRefresh(mSwipeRefreshLayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getTopicData(false);
            }
        }, 2000);
    }

    private void startAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(500);
        mImageViewRefresh.startAnimation(rotateAnimation);
    }

    private int mPage = 1;

    private void getTopicData(final boolean isLoadMore) {
        String url;
        switch ((int) (Math.random() * 6)) {
            case 0:
                url = "http://api.weibo.cn/2/cardlist?networktype=wifi&extparam=discover&scenes=0&uicode=10000327&moduleID=708&wb_version=3314&c=android&i=6bf3ab0&s=64909e49&ua=OPPO-OPPO%20R9m__weibo__6.12.3__android__android5.1&wm=9847_0002&aid=01An7W2Qrb2ly23qoLunLFgKKZdtuvA8z4ykaaSXOI2MlMkR4.&fid=102803_ctg1_8999_-_ctg1_8999_home&uid=5220533979&v_f=2&v_p=42&from=106C395010&gsid=_2A251ZTUwDeTxGeNM6VIU8y3FzDWIHXVUM8_4rDV6PUJbkdAKLU3YkWo-zKVVphhgObyY_xxcf_CAF1cC_A..&imsi=460000930389157&lang=zh_CN&lfid=231091&page=" + mPage + "&skin=default&count=20&oldwm=9893_0044&sflag=1&containerid=102803_ctg1_8999_-_ctg1_8999_home&luicode=10000010&need_head_cards=0";
                break;
            case 1:
                url = "http://api.weibo.cn/2/cardlist?networktype=wifi&extparam=discover&uicode=10000011&moduleID=708&wb_version=3314&c=android&i=6bf3ab0&s=64909e49&ua=OPPO-OPPO%20R9m__weibo__6.12.3__android__android5.1&wm=9847_0002&aid=01An7W2Qrb2ly23qoLunLFgKKZdtuvA8z4ykaaSXOI2MlMkR4.&fid=102803_ctg1_9999_-_ctg1_9999&uid=5220533979&v_f=2&v_p=42&from=106C395010&gsid=_2A251ZTUwDeTxGeNM6VIU8y3FzDWIHXVUM8_4rDV6PUJbkdAKLU3YkWo-zKVVphhgObyY_xxcf_CAF1cC_A..&imsi=460000930389157&lang=zh_CN&page=" + mPage + "&skin=default&count=20&oldwm=9893_0044&sflag=1&containerid=102803_ctg1_9999_-_ctg1_9999&need_head_cards=1";
                break;
            case 2:
                url = "http://api.weibo.cn/2/cardlist?networktype=wifi&extparam=discover&uicode=10000011&moduleID=708&wb_version=3314&c=android&i=6bf3ab0&s=64909e49&ua=OPPO-OPPO%20R9m__weibo__6.12.3__android__android5.1&wm=9847_0002&aid=01An7W2Qrb2ly23qoLunLFgKKZdtuvA8z4ykaaSXOI2MlMkR4.&fid=102803_ctg1_8998_-_ctg1_8998&uid=5220533979&v_f=2&v_p=42&from=106C395010&gsid=_2A251ZTUwDeTxGeNM6VIU8y3FzDWIHXVUM8_4rDV6PUJbkdAKLU3YkWo-zKVVphhgObyY_xxcf_CAF1cC_A..&imsi=460000930389157&lang=zh_CN&page=" + mPage + "&skin=default&count=20&oldwm=9893_0044&sflag=1&containerid=102803_ctg1_8998_-_ctg1_8998&need_head_cards=1";
                break;
            case 3:
                url = "http://api.weibo.cn/2/cardlist?networktype=wifi&extparam=discover&uicode=10000011&moduleID=708&wb_version=3314&c=android&i=6bf3ab0&s=64909e49&ua=OPPO-OPPO%20R9m__weibo__6.12.3__android__android5.1&wm=9847_0002&aid=01An7W2Qrb2ly23qoLunLFgKKZdtuvA8z4ykaaSXOI2MlMkR4.&fid=102803_ctg1_8997_-_ctg1_8997&uid=5220533979&v_f=2&v_p=42&from=106C395010&gsid=_2A251ZTUwDeTxGeNM6VIU8y3FzDWIHXVUM8_4rDV6PUJbkdAKLU3YkWo-zKVVphhgObyY_xxcf_CAF1cC_A..&imsi=460000930389157&lang=zh_CN&page=" + mPage + "&skin=default&count=20&oldwm=9893_0044&sflag=1&containerid=102803_ctg1_8997_-_ctg1_8997&need_head_cards=1";
                break;
            case 4:
                url = "http://api.weibo.cn/2/cardlist?networktype=wifi&extparam=discover&uicode=10000011&moduleID=708&wb_version=3314&c=android&i=6bf3ab0&s=64909e49&ua=OPPO-OPPO%20R9m__weibo__6.12.3__android__android5.1&wm=9847_0002&aid=01An7W2Qrb2ly23qoLunLFgKKZdtuvA8z4ykaaSXOI2MlMkR4.&fid=102803_ctg1_8899_-_ctg1_8899&uid=5220533979&v_f=2&v_p=42&from=106C395010&gsid=_2A251ZTUwDeTxGeNM6VIU8y3FzDWIHXVUM8_4rDV6PUJbkdAKLU3YkWo-zKVVphhgObyY_xxcf_CAF1cC_A..&imsi=460000930389157&lang=zh_CN&page=" + mPage + "&skin=default&count=20&oldwm=9893_0044&sflag=1&containerid=102803_ctg1_8899_-_ctg1_8899&need_head_cards=1";
                break;
            case 5:
                url = "http://api.weibo.cn/2/cardlist?networktype=wifi&extparam=discover&uicode=10000011&moduleID=708&wb_version=3314&c=android&i=6bf3ab0&s=64909e49&ua=OPPO-OPPO%20R9m__weibo__6.12.3__android__android5.1&wm=9847_0002&aid=01An7W2Qrb2ly23qoLunLFgKKZdtuvA8z4ykaaSXOI2MlMkR4.&fid=102803_ctg1_8799_-_ctg1_8799&uid=5220533979&v_f=2&v_p=42&from=106C395010&gsid=_2A251ZTUwDeTxGeNM6VIU8y3FzDWIHXVUM8_4rDV6PUJbkdAKLU3YkWo-zKVVphhgObyY_xxcf_CAF1cC_A..&imsi=460000930389157&lang=zh_CN&page=" + mPage + "&skin=default&count=20&oldwm=9893_0044&sflag=1&containerid=102803_ctg1_8799_-_ctg1_8799&need_head_cards=1";
                break;
            default:
                url = "http://api.weibo.cn/2/cardlist?networktype=wifi&extparam=discover&scenes=0&uicode=10000327&moduleID=708&wb_version=3314&c=android&i=6bf3ab0&s=64909e49&ua=OPPO-OPPO%20R9m__weibo__6.12.3__android__android5.1&wm=9847_0002&aid=01An7W2Qrb2ly23qoLunLFgKKZdtuvA8z4ykaaSXOI2MlMkR4.&fid=102803_ctg1_8999_-_ctg1_8999_home&uid=5220533979&v_f=2&v_p=42&from=106C395010&gsid=_2A251ZTUwDeTxGeNM6VIU8y3FzDWIHXVUM8_4rDV6PUJbkdAKLU3YkWo-zKVVphhgObyY_xxcf_CAF1cC_A..&imsi=460000930389157&lang=zh_CN&lfid=231091&page=" + mPage + "&skin=default&count=20&oldwm=9893_0044&sflag=1&containerid=102803_ctg1_8999_-_ctg1_8999_home&luicode=10000010&need_head_cards=0";
                break;
        }
        OkHttpUtils.get().url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response, int id) {
                if (mSwipeRefreshLayout != null) {
                    KUtils.hideSwipeRefresh(mSwipeRefreshLayout);
                }

                if (mImageViewRefresh != null) {
                    mImageViewRefresh.clearAnimation();
                }
                if (!isLoadMore) {
                    ACache.get(getActivity()).put("keys_topic", response);
                }
                praseData(response, isLoadMore);
            }
        });
    }

    private void praseData(String response, boolean isLoadMore) {
        List<TopicBean> topicBeanArrayList = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject.containsKey("cards")) {
            JSONArray jsonArray = jsonObject.getJSONArray("cards");
            if (jsonArray.size() <= 0 && isLoadMore) {
                mKReyccleView.setAllLoadComplete();
                return;
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                TopicBean topicBean = new TopicBean();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                if (jsonObject1.containsKey("card_type")) {
                    int cardType = jsonObject1.getIntValue("card_type");
                    if (cardType == 9 && jsonObject1.containsKey("mblog")) {
                        TopicBean.MBlogBean mBlogBean = JSON.parseObject(jsonObject1.getString("mblog"), TopicBean.MBlogBean.class);
                        topicBean.setMBlogBeen(mBlogBean);
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("mblog");
                        if (mBlogBean.getPic_ids() != null && mBlogBean.getPic_ids().size() > 0 &&
                                jsonObject2.containsKey("pic_infos")) {
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("pic_infos");
                            List<ImageBean> imageBeanList = new ArrayList<>();
                            List<ImageBean> imageBeanList1 = new ArrayList<ImageBean>();
                            for (int j = 0; j < mBlogBean.getPic_ids().size(); j++) {
                                String key = mBlogBean.getPic_ids().get(j);
                                JSONObject jsonObject4 = jsonObject3.getJSONObject(key);
                                JSONObject jsonObject5 = jsonObject4.getJSONObject("bmiddle");
                                JSONObject jsonObject6 = jsonObject4.getJSONObject("original");
                                ImageBean imageBean = new ImageBean();
                                ImageBean imageBean1 = new ImageBean();
                                imageBean.setUrl(jsonObject5.getString("url"));
                                imageBean.setWidth(jsonObject5.getIntValue("width"));
                                imageBean.setHeight(jsonObject5.getIntValue("height"));

                                imageBean1.setUrl(jsonObject6.getString("url"));
                                imageBean1.setWidth(jsonObject6.getIntValue("width"));
                                imageBean1.setHeight(jsonObject6.getIntValue("height"));
                                imageBeanList.add(imageBean);
                                imageBeanList1.add(imageBean1);
                            }
                            topicBean.setImageBeanList(imageBeanList);
                            topicBean.setLargeImageBeanList(imageBeanList1);
                        }
                    } else {
                        continue;
                    }
                }
                topicBeanArrayList.add(topicBean);
            }
            mTopicAdapter.setData(topicBeanArrayList, isLoadMore);
            removeRootView();
            if (isLoadMore) {
                mKReyccleView.setCurrentLoadComplete();
            }
        }
    }


    @Override
    public void onRefresh() {
        mKReyccleView.setCancleLoadMore(true);
        mKReyccleView.reload();
        mPage = 1;
        getTopicData(false);
    }

    @Override
    public void onRecyclerViewLoadMore() {
        if (KUtils.Network.isExistNetwork()) {
            mPage++;
            getTopicData(true);
        } else {
            mKReyccleView.setNetworkError();
        }
    }

    @Override
    public void onScrollStatus(boolean b, int positionStart, int itemCount) {
        mTopicAdapter.loadImage(b, positionStart, itemCount);
    }
}
