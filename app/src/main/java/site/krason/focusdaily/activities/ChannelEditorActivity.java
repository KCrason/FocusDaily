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

import java.util.ArrayList;
import java.util.List;

import cc.solart.dragdrop.DragDropListView;
import cc.solart.dragdrop.IDragEntity;
import cc.solart.dragdrop.adapter.AbsTileAdapter;
import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.channel.MyChannelGirdAdapter;
import site.krason.focusdaily.adapters.channel.RecommendChannelGirdAdapter;
import site.krason.focusdaily.bean.ChannelBean;
import site.krason.focusdaily.database.KDataBaseHelper;
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

    private List<String> mMyStrings = new ArrayList<>();
    private List<String> mRecommendStrings = new ArrayList<>();

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
                dealWithChannel(position, false);
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
                layoutParams.height = mDragDropListView.getMeasuredHeight();
                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                frameLayout.setLayoutParams(layoutParams);

                mDragDropListView.setVerticalScrollBarEnabled(false);
                mDragDropListView.setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
                mDragDropListView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
                mDragDropListView.getDragDropController().addOnDragDropListener(mMyChannelGirdAdapter);
                mDragDropListView.setDragShadowOverlay(mDragShadowOverlay);
            }
        });
    }

    private List<ChannelBean> channelBeanArrayListRecommend;
    private List<IDragEntity> channelBeanArrayListMe;

    private List<IDragEntity> getChannelBeen() {
        List<ChannelBean> channelBeanList = KDataBaseHelper.form().getAllChannel(new String[]{"1"});
        channelBeanArrayListMe = new ArrayList<>();
        for (int i = 0; i < channelBeanList.size(); i++) {
            ChannelBean channelBean = new ChannelBean();
            channelBean.setId(channelBeanList.get(i).getId());
            mMyStrings.add(channelBeanList.get(i).getChannelName());
            channelBean.setChannelName(channelBeanList.get(i).getChannelName());
            channelBeanArrayListMe.add(channelBean);
        }
        return channelBeanArrayListMe;
    }

    private List<ChannelBean> getChannelBeenRecommend() {
        List<ChannelBean> channelBeanList = KDataBaseHelper.form().getAllChannel(new String[]{"0"});
        channelBeanArrayListRecommend = new ArrayList<>();
        for (int i = 0; i < channelBeanList.size(); i++) {
            ChannelBean channelBean = new ChannelBean();
            channelBean.setId(channelBeanList.get(i).getId());
            mRecommendStrings.add(channelBeanList.get(i).getChannelName());
            channelBean.setChannelName(channelBeanList.get(i).getChannelName());
            channelBeanArrayListRecommend.add(channelBean);
        }
        return channelBeanArrayListRecommend;
    }

    @Override
    public DragDropListView getDragDropListView() {
        return mDragDropListView;
    }

    @Override
    public void onDataSetChangedForResult(ArrayList<IDragEntity> list) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_edit:
                if (mMyChannelGirdAdapter.getEditStatus()) {
                    mTxtEdit.setText("编辑");
                    mMyChannelGirdAdapter.setIsEdit(false);
                } else {
                    mTxtEdit.setText("完成");
                    mMyChannelGirdAdapter.setIsEdit(true);
                }
                mMyChannelGirdAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void dealWithChannel(int i, boolean isClickRecommend) {
        if (isClickRecommend) {
            mMyStrings.add(channelBeanArrayListRecommend.get(i).getChannelName());
            mRecommendStrings.remove(i);

            ChannelBean channelBean = new ChannelBean();
            channelBean.setIsRecommend(0);
            channelBean.setChannelName(channelBeanArrayListRecommend.get(i).getChannelName());
            channelBeanArrayListMe.add(channelBean);
            channelBeanArrayListRecommend.remove(i);
        } else {
            mMyStrings.remove(i);
            mRecommendStrings.add(((ChannelBean) channelBeanArrayListMe.get(i)).getChannelName());

            ChannelBean channelBean = new ChannelBean();
            channelBean.setIsRecommend(1);
            channelBean.setChannelName(((ChannelBean) channelBeanArrayListMe.get(i)).getChannelName());
            channelBeanArrayListRecommend.add(channelBean);
            channelBeanArrayListMe.remove(i);
        }
        mMyChannelGirdAdapter.setData(channelBeanArrayListMe);
        mRecommendChannelGirdAdapter.setData(channelBeanArrayListRecommend);

        refreshDragShadowOverlayHeight(mFrameLayout);
    }

    @Override
    public void onItemClick(AdapterView<?> adaperView, View view, int i, long l) {
        if (adaperView.getAdapter() instanceof RecommendChannelGirdAdapter) {
            if (channelBeanArrayListRecommend != null && i < channelBeanArrayListRecommend.size()) {
                dealWithChannel(i, true);
            }
        }
    }
}
