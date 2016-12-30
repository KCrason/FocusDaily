package site.krason.focusdaily.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2016/12/30.
 * @email 535089696@qq.com
 */

public class IntersterTagAdapter extends BaseAdapter {

    private List<String> mStrings = new ArrayList<>();

    private Context mContext;

    public IntersterTagAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<String> strings) {
        if (mStrings != null) {
            mStrings.clear();
        }
        this.mStrings = strings;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mStrings != null) {
            return mStrings.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (mStrings != null) {
            return mStrings.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public final class TagViewHolder {
        private CheckBox mCheckBoxTag;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TagViewHolder tagViewHolder;
        if (view == null) {
            tagViewHolder = new TagViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_interster_tag, null);
            tagViewHolder.mCheckBoxTag = (CheckBox) view.findViewById(R.id.txt_tag);
            view.setTag(tagViewHolder);
        } else {
            tagViewHolder = (TagViewHolder) view.getTag();
        }
        tagViewHolder.mCheckBoxTag.setText(mStrings.get(i));
        return view;
    }
}
