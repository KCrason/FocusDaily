package site.krason.focusdaily.adapters.channel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.adapters.channel.viewholder.ChannelViewHolder;
import site.krason.focusdaily.bean.ChannelBean;

/**
 * @author Created by KCrason on 2017/1/5.
 * @email 535089696@qq.com
 */

public class RecommendChannelGirdAdapter extends BaseAdapter {

    private List<ChannelBean> mChannelBeen = new ArrayList<>();

    private Context mContext;

    public RecommendChannelGirdAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ChannelBean> channelBeen) {
        this.mChannelBeen = channelBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mChannelBeen != null) {
            return mChannelBeen.size();
        }
        return 0;

    }

    @Override
    public Object getItem(int i) {
        if (mChannelBeen != null) {
            return mChannelBeen.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChannelBean channelBean =mChannelBeen.get(i);
        ChannelViewHolder channelViewHolder;
        if (view == null) {
            channelViewHolder = new ChannelViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_channel_recommed, null);
            channelViewHolder.setTextViewName((TextView) view.findViewById(R.id.tile_name));
            view.setTag(channelViewHolder);
        } else {
            channelViewHolder = (ChannelViewHolder) view.getTag();
        }
        channelViewHolder.getTextViewName().setText(channelBean.getChannelName());
        return view;
    }
}
