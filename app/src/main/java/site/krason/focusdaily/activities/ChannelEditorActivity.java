package site.krason.focusdaily.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.channel.MyChannelGirdAdapter;
import site.krason.focusdaily.adapters.channel.RecommendChannelGirdAdapter;
import site.krason.focusdaily.bean.ChannelBean;
import site.krason.focusdaily.common.Constants;
import site.krason.focusdaily.events.NotifyChannelRefresh;
import site.krason.focusdaily.sortdrag.AbsTileAdapter;
import site.krason.focusdaily.sortdrag.DragDropListView;
import site.krason.focusdaily.sortdrag.IDragEntity;
import site.krason.focusdaily.utils.ACache;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.NoScrollGridView;
import site.krason.focusdaily.widgets.TileView;

/**
 * @author Created by KCrason on 2017/1/5.
 * @email 535089696@qq.com
 */

public class ChannelEditorActivity extends BaseActivity implements AbsTileAdapter.DragDropListener, View.OnClickListener, AdapterView.OnItemClickListener {

    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_channel_editor;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChannelEditorActivity.class);
        context.startActivity(intent);
    }


    private NoScrollGridView mRecommendNoScrollGridView;
    private DragDropListView mDragDropListView;

    private MyChannelGirdAdapter mMyChannelGirdAdapter;
    private RecommendChannelGirdAdapter mRecommendChannelGirdAdapter;

    private TextView mTxtEdit;

    private FrameLayout mFrameLayout;
    private ImageView mDragShadowOverlay;

    @Override
    public void initViews() {
        mDragDropListView = (DragDropListView) findViewById(R.id.drag_drop_list_view);

        mTxtEdit = (TextView) findViewById(R.id.txt_edit);
        mTxtEdit.setOnClickListener(this);

        mMyChannelGirdAdapter = new MyChannelGirdAdapter(this, this, new TileView.OnSelectedListener() {
            @Override
            public void onTileSelected(int position, IDragEntity entity) {
                if (mMyChannelGirdAdapter.getEditStatus()) {
                    dealWithChannel(position, false);
                } else {
                    EventBus.getDefault().post(new NotifyChannelRefresh(Constants.EVENT_CODE_SUCCESS, String.valueOf(position)));
                    finish();
                }
            }
        }, mTxtEdit);
        mDragDropListView.setVerticalScrollBarEnabled(false);
        mDragDropListView.setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
        mDragDropListView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        mDragDropListView.getDragDropController().addOnDragDropListener(mMyChannelGirdAdapter);


        mFrameLayout = (FrameLayout) findViewById(R.id.activity_overlay);

        mDragShadowOverlay = (ImageView) findViewById(R.id.tile_drag_shadow_overlay);
        mDragDropListView.setDragShadowOverlay(mDragShadowOverlay);
        mMyChannelGirdAdapter.setData(getChannelBeen());

        mDragDropListView.setAdapter(mMyChannelGirdAdapter);
        refreshDragShadowOverlayHeight(mFrameLayout);

        mRecommendNoScrollGridView = (NoScrollGridView) findViewById(R.id.noscroll_grid_view_recommend);
        mRecommendNoScrollGridView.setOnItemClickListener(this);
        mRecommendChannelGirdAdapter = new RecommendChannelGirdAdapter(this);
        mRecommendNoScrollGridView.setAdapter(mRecommendChannelGirdAdapter);
        mRecommendChannelGirdAdapter.setData(getChannelBeenRecommend());
    }

    private void refreshDragShadowOverlayHeight(final FrameLayout frameLayout) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) frameLayout.getLayoutParams();
                layoutParams.height = KUtils.getScreenHeight();
                layoutParams.width = KUtils.getScreenWidth();
                frameLayout.setLayoutParams(layoutParams);
            }
        });
    }

    private List<ChannelBean> channelBeanArrayListRecommend;
    private List<IDragEntity> channelBeanArrayListMe;

    private List<IDragEntity> getChannelBeen() {
        List<ChannelBean> channelBeanList = JSON.parseArray(ACache.get(this).getAsString(Constants.MY_CHANNEL_LIST), ChannelBean.class);
        channelBeanArrayListMe = new ArrayList<>();
        for (int i = 0; i < channelBeanList.size(); i++) {
            ChannelBean channelBean = new ChannelBean();
            channelBean.setId(channelBeanList.get(i).getId());
            channelBean.setChannelName(channelBeanList.get(i).getChannelName());
            channelBeanArrayListMe.add(channelBean);
        }
        return channelBeanArrayListMe;
    }

    private List<ChannelBean> getChannelBeenRecommend() {
        channelBeanArrayListRecommend = JSON.parseArray(ACache.get(this).getAsString(Constants.RECOMMEND_CHANNEL_LIST), ChannelBean.class);
        return channelBeanArrayListRecommend;
    }

    @Override
    public DragDropListView getDragDropListView() {
        return mDragDropListView;
    }


    @Override
    public void onDataSetChangedForResult(ArrayList<IDragEntity> list) {
//        this.channelBeanArrayListMe = list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_edit:
                if (mMyChannelGirdAdapter.getEditStatus()) {
                    mTxtEdit.setText("编辑");
                    mMyChannelGirdAdapter.setIsEdit(false);
                    mMyChannelGirdAdapter.notifyDataSetChanged();
                    savaChannelData(mMyChannelGirdAdapter.getEntityList(), channelBeanArrayListRecommend);
                } else {
                    mTxtEdit.setText("完成");
                    mMyChannelGirdAdapter.setIsEdit(true);
                    mMyChannelGirdAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    private void savaChannelData(List<IDragEntity> iMyDragEntities, List<ChannelBean> channelBeanList) {
        List<IDragEntity> mMyIDragEntities = new ArrayList<>();
        List<IDragEntity> mRecommendIDragEntities = new ArrayList<>();
        for (int i = 0; i < iMyDragEntities.size(); i++) {
            ChannelBean channelBean = new ChannelBean();
            channelBean.setId(iMyDragEntities.get(i).getId());
            channelBean.setChannelName(((ChannelBean) iMyDragEntities.get(i)).getChannelName());
            mMyIDragEntities.add(channelBean);
        }
        ACache.get(this).put(Constants.MY_CHANNEL_LIST, JSON.toJSONString(mMyIDragEntities));

        for (int i = 0; i < channelBeanList.size(); i++) {
            ChannelBean channelBean = new ChannelBean();
            channelBean.setId(channelBeanList.get(i).getId());
            channelBean.setChannelName(channelBeanList.get(i).getChannelName());
            mRecommendIDragEntities.add(channelBean);
        }
        ACache.get(this).put(Constants.RECOMMEND_CHANNEL_LIST, JSON.toJSONString(mRecommendIDragEntities));

        EventBus.getDefault().post(new NotifyChannelRefresh(Constants.EVENT_CODE_SUCCESS, "LONGCLICK"));
        finish();
    }

    private void dealWithChannel(int i, boolean isClickRecommend) {
        if (isClickRecommend) {
            channelBeanArrayListMe.add(channelBeanArrayListRecommend.get(i));
            channelBeanArrayListRecommend.remove(i);
        } else {
            channelBeanArrayListRecommend.add((ChannelBean) channelBeanArrayListMe.get(i));
            channelBeanArrayListMe.remove(i);
        }

        mMyChannelGirdAdapter.setData(channelBeanArrayListMe);
        mRecommendChannelGirdAdapter.setData(channelBeanArrayListRecommend);
    }

    @Override
    public void onItemClick(AdapterView<?> adaperView, View view, int i, long l) {
        if (adaperView.getAdapter() instanceof RecommendChannelGirdAdapter && mMyChannelGirdAdapter.getEditStatus()) {
            if (channelBeanArrayListRecommend != null && i < channelBeanArrayListRecommend.size()) {
                dealWithChannel(i, true);
            }
        }
    }
}
