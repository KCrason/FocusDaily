/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package site.krason.focusdaily.widgets;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.channel.MyChannelGirdAdapter;
import site.krason.focusdaily.bean.ChannelBean;
import site.krason.focusdaily.sortdrag.AbsTileAdapter;
import site.krason.focusdaily.sortdrag.DragDropListView;
import site.krason.focusdaily.sortdrag.IDragEntity;


/**
 * A TileView displays a picture and name
 */
public class TileView extends CardView {
    private final static String TAG = TileView.class.getSimpleName();
    private static final ClipData EMPTY_CLIP_DATA = ClipData.newPlainText("", "");
    private ImageView mPin;
    private TextView mName;
    protected OnSelectedListener mListener;
    private IDragEntity mIDragEntity;

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mName = (TextView) findViewById(R.id.tile_name);
        mPin = (ImageView) findViewById(R.id.img_edit);
        setOnClickListener(createClickListener());

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mTextViewEdit != null && mMyChannelGirdAdapter != null && !mMyChannelGirdAdapter.getEditStatus()) {
                    mTextViewEdit.setText("完成");
                    mMyChannelGirdAdapter.setIsEdit(true);
                    mMyChannelGirdAdapter.notifyDataSetChanged();
                }
                // NOTE The drag shadow is handled in the ListView.
                v.startDrag(EMPTY_CLIP_DATA, new DragShadowBuilder(),
                        DragDropListView.DRAG_FAVORITE_TILE, 0);
                return true;
            }
        });
    }


    protected OnClickListener createClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null ) {
                    return;
                }
                mListener.onTileSelected(mPosition, mIDragEntity);
            }
        };
    }

    public IDragEntity getDragEntity() {
        return mIDragEntity;
    }

    public void renderData(IDragEntity entity, boolean isEdit) {
        mIDragEntity = entity;
        if (entity != null && entity != AbsTileAdapter.BLANK_ENTRY) {
            if (entity instanceof ChannelBean) {
                mName.setText(((ChannelBean) mIDragEntity).getChannelName());
            }
            if (isEdit) {
                mPin.setVisibility(VISIBLE);
            } else {
                mPin.setVisibility(GONE);
            }
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.INVISIBLE);
        }
    }


    private MyChannelGirdAdapter mMyChannelGirdAdapter;
    private TextView mTextViewEdit;
    private int mPosition;

    public void setListener(OnSelectedListener listener, MyChannelGirdAdapter myChannelGirdAdapter,
                            TextView textViewEdit, int position) {
        this.mListener = listener;
        this.mTextViewEdit = textViewEdit;
        this.mMyChannelGirdAdapter = myChannelGirdAdapter;
        this.mPosition = position;
    }


    public interface OnSelectedListener {
        /**
         * Notification that the tile was selected; no specific action is dictated.
         */
        void onTileSelected(int position, IDragEntity entity);
    }
}
