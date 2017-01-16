package site.krason.focusdaily.adapters.channel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import site.krason.focusdaily.R;
import site.krason.focusdaily.sortdrag.AbsTileAdapter;
import site.krason.focusdaily.sortdrag.IDragEntity;
import site.krason.focusdaily.widgets.TileView;

/**
 * @author Created by KCrason on 2017/1/5.
 * @email 535089696@qq.com
 */

public class MyChannelGirdAdapter extends AbsTileAdapter {

    private TileView.OnSelectedListener mListener;
    private TextView mTextViewEdit;

    public MyChannelGirdAdapter(Context context, AbsTileAdapter.DragDropListener dragDropListener,
                                TileView.OnSelectedListener listener,
                                TextView textViewEdit) {
        super(context, dragDropListener);
        this.mListener = listener;
        this.mTextViewEdit = textViewEdit;
    }

    private boolean mIsEdit;

    public void setIsEdit(boolean isEdit) {
        this.mIsEdit = isEdit;
    }

    public boolean getEditStatus() {
        return mIsEdit;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        TileView tileView = null;

        if (convertView != null && convertView instanceof TileView) {
            tileView = (TileView) convertView;
        }

        if (tileView == null) {
            tileView = (TileView) View.inflate(mContext,
                    R.layout.item_grid_channel, null);
        }

        tileView.setListener(mListener, this, mTextViewEdit,position);
        tileView.renderData(getItem(position), mIsEdit);
        return tileView;
    }

    @Override
    protected IDragEntity getDragEntity(View view) {
        return ((TileView) view).getDragEntity();
    }
}
