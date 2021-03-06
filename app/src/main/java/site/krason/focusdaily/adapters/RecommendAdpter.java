package site.krason.focusdaily.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.interfaces.OnIntersterClickListener;
import site.krason.focusdaily.utils.KUtils;
import site.krason.focusdaily.widgets.DeletePopupWindow;
import site.krason.focusdaily.widgets.recyclerview.interfaces.OnRealItemClickCallBack;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class
RecommendAdpter extends RecyclerView.Adapter implements OnIntersterClickListener {

    private final static int VIEW_TYPE_NO_PIC = 0;
    private final static int VIEW_TYPE_1_PIC = 1;
    private final static int VIEW_TYPE_2_PIC = 2;
    private final static int VIEW_TYPE_3_PIC = 3;
    private final static int VIEW_TYPE_VIDEO = 4;


    private Context mContext;

    private List<KNewBean.ItemBean> mStrings = new ArrayList<>();

    private OnRealItemClickCallBack<KNewBean.ItemBean> mDataBeanOnRealItemClickCallBack;
    private String mType;

    public RecommendAdpter(Context context, OnRealItemClickCallBack<KNewBean.ItemBean> dataBeanOnRealItemClickCallBack, String type) {
        this.mContext = context;
        this.mType = type;
        this.mDataBeanOnRealItemClickCallBack = dataBeanOnRealItemClickCallBack;
    }


    public void setData(KNewBean kNewBean) {
        if (mType.equals("科技") ||
                mType.equals("娱乐") ||
                mType.equals("时尚") ||
                mType.equals("旅游") ||
                mType.equals("国学") ||
                mType.equals("时政") ||
                mType.equals("青年") ||
                mType.equals("评论") ||
                mType.equals("政能量") ||
                mType.equals("智库") ||
                mType.equals("公益") ||
                mType.equals("体育") ||
                mType.equals("汽车") ||
                mType.equals("财经")) {
            this.mStrings.clear();
        }
        this.mStrings.addAll(0, kNewBean.getItem());
        notifyDataSetChanged();
    }

    public void addData(KNewBean kNewBean) {
        this.mStrings.addAll(kNewBean.getItem());
        notifyDataSetChanged();
    }

    @Override
    public void onIntersterClick(int position) {
        if (position < mStrings.size()) {
            mStrings.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mStrings.size() - position);
        }
    }


    public final class OnRecyclerItemClick implements View.OnClickListener {

        private KNewBean.ItemBean mDataBean;

        public OnRecyclerItemClick(KNewBean.ItemBean dataBean) {
            this.mDataBean = dataBean;
        }

        @Override
        public void onClick(View view) {
            if (mDataBeanOnRealItemClickCallBack != null) {
                mDataBeanOnRealItemClickCallBack.onRealItemClick(view, mDataBean);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NO_PIC:
                return new NoPicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_not_pic, parent, false));
            case VIEW_TYPE_1_PIC:
                return new OnePicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_1_pic, parent, false));
            case VIEW_TYPE_2_PIC:
                return new TwoPicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_2_pic, parent, false));
            case VIEW_TYPE_3_PIC:
                return new ThreePicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_3_pic, parent, false));
            case VIEW_TYPE_VIDEO:
                return new VideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_video, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            KNewBean.ItemBean dataBean = mStrings.get(position);
            holder.itemView.setOnClickListener(new OnRecyclerItemClick(dataBean));
            OnDeleteClickListener onDeleteClickListener;
            if (dataBean.getStyle() == null) {
                onDeleteClickListener = new OnDeleteClickListener(position, dataBean.getSource());
            } else {
                if (dataBean.getStyle().getBackreason() == null) {
                    onDeleteClickListener = new OnDeleteClickListener(position, dataBean.getSource());
                } else {
                    onDeleteClickListener = new OnDeleteClickListener(position, dataBean.getStyle().getBackreason());
                }
            }
            if (dataBean != null) {
                if (holder instanceof NoPicViewHolder) {
                    ((NoPicViewHolder) holder).mNoInterest.setOnClickListener(onDeleteClickListener);
                    ((NoPicViewHolder) holder).mTitle.setText(dataBean.getTitle());
                    ((NoPicViewHolder) holder).mBaseInfo.setText(KUtils.filterStringValue(dataBean.getSource()) + "  " + KUtils.betweenOf2Days(dataBean.getUpdateTime()));
                } else if (holder instanceof OnePicViewHolder) {
                    ((OnePicViewHolder) holder).mNoInterest.setOnClickListener(onDeleteClickListener);
                    ((OnePicViewHolder) holder).mTitle.setText(dataBean.getTitle());
                    if (dataBean.getType().equals("topic2")) {
                        ((OnePicViewHolder) holder).mBaseInfo.setText("专题");
                    } else {
                        ((OnePicViewHolder) holder).mBaseInfo.setText(KUtils.filterStringValue(dataBean.getSource()) + "  " + KUtils.betweenOf2Days(dataBean.getUpdateTime()));
                    }
                    Glide.with(mContext).load(dataBean.getThumbnail()).asBitmap().into(((OnePicViewHolder) holder).imgOnePic);
                } else if (holder instanceof TwoPicViewHolder) {
                    ((TwoPicViewHolder) holder).mNoInterest.setOnClickListener(onDeleteClickListener);
                    ((TwoPicViewHolder) holder).mTitle.setText(dataBean.getTitle());
                    ((TwoPicViewHolder) holder).mBaseInfo.setText(KUtils.filterStringValue(dataBean.getSource()) + "  " + KUtils.betweenOf2Days(dataBean.getUpdateTime()));
                    Glide.with(mContext).load(dataBean.getThumbnail()).asBitmap().into(((TwoPicViewHolder) holder).imgBigPic);
                } else if (holder instanceof ThreePicViewHolder) {
                    ((ThreePicViewHolder) holder).mNoInterest.setOnClickListener(onDeleteClickListener);
                    ((ThreePicViewHolder) holder).mTitle.setText(dataBean.getTitle());
                    String time = dataBean.getUpdateTime();
                    if (dataBean.getType().equals("slide")) {
                        if (TextUtils.isEmpty(time)) {
                            ((ThreePicViewHolder) holder).mBaseInfo.setText(KUtils.filterStringValue(dataBean.getSource()) + "  图集");
                        } else {
                            ((ThreePicViewHolder) holder).mBaseInfo.setText(KUtils.filterStringValue(dataBean.getSource()) + "  图集  " + KUtils.betweenOf2Days(time));
                        }
                    } else {
                        if (TextUtils.isEmpty(time)) {
                            ((ThreePicViewHolder) holder).mBaseInfo.setText(KUtils.filterStringValue(dataBean.getSource()));
                        } else {
                            ((ThreePicViewHolder) holder).mBaseInfo.setText(KUtils.filterStringValue(dataBean.getSource()) + "  " + KUtils.betweenOf2Days(time));
                        }
                    }
                    if (dataBean.getStyle().getImages().size() >= 3) {
                        Glide.with(mContext).load(dataBean.getStyle().getImages().get(0)).asBitmap().into(((ThreePicViewHolder) holder).imgOnePic);
                        Glide.with(mContext).load(dataBean.getStyle().getImages().get(1)).asBitmap().into(((ThreePicViewHolder) holder).imgTwoPic);
                        Glide.with(mContext).load(dataBean.getStyle().getImages().get(2)).asBitmap().into(((ThreePicViewHolder) holder).imgThreePic);
                    }
                } else if (holder instanceof VideoViewHolder) {
                    ((VideoViewHolder) holder).mNoInterest.setOnClickListener(onDeleteClickListener);
                    ((VideoViewHolder) holder).mTitle.setText(dataBean.getTitle());
                    if (dataBean.getPhvideo() != null) {
                        if (dataBean.getPhvideo().getChannelName() != null) {
                            ((VideoViewHolder) holder).mBaseInfo.setText(dataBean.getPhvideo().getChannelName());
                        }
                        ((VideoViewHolder) holder).mLength.setText(KUtils.formatVideoDuration(dataBean.getPhvideo().getLength()));
                    }
                    Glide.with(mContext).load(dataBean.getThumbnail()).asBitmap().into(((VideoViewHolder) holder).imgBigPic);
                }
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position < mStrings.size()) {
            KNewBean.ItemBean dataBean = mStrings.get(position);
            if (dataBean != null) {
                String type = dataBean.getType();
                KNewBean.ItemBean.StyleBean styleBean = dataBean.getStyle();
                if (type != null) {
                    if (type.equals("doc")) {
                        if (styleBean == null) {
                            if (TextUtils.isEmpty(dataBean.getThumbnail())) {
                                return VIEW_TYPE_NO_PIC;
                            } else {
                                return VIEW_TYPE_1_PIC;
                            }
                        } else {
                            int slideCount = styleBean.getSlideCount();
                            if (slideCount >= 3) {
                                return VIEW_TYPE_3_PIC;
                            } else {
                                if (TextUtils.isEmpty(dataBean.getThumbnail())) {
                                    return VIEW_TYPE_NO_PIC;
                                } else {
                                    return VIEW_TYPE_1_PIC;
                                }
                            }
                        }
                    } else if (type.equals("phvideo")) {
                        return VIEW_TYPE_VIDEO;
                    } else if (type.equals("topic2")) {
                        return VIEW_TYPE_1_PIC;
                    } else if (type.equals("slide")) {
                        if (styleBean == null) {
                            return VIEW_TYPE_1_PIC;
                        } else {
                            int slideCount = styleBean.getSlideCount();
                            switch (slideCount) {
                                case 0:
                                    return VIEW_TYPE_NO_PIC;
                                case 1:
                                    return VIEW_TYPE_1_PIC;
                                case 2:
                                    return VIEW_TYPE_2_PIC;
                                default:
                                    return VIEW_TYPE_3_PIC;
                            }
                        }
                    } else {
                        if (TextUtils.isEmpty(dataBean.getThumbnail())) {
                            return VIEW_TYPE_NO_PIC;
                        } else {
                            return VIEW_TYPE_1_PIC;
                        }
                    }
                }
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (mStrings == null) {
            return 0;
        }
        return mStrings.size();
    }

    public class OnDeleteClickListener implements View.OnClickListener {

        private int position;
        private List<String> tags;


        public OnDeleteClickListener(int position, List<String> tags) {
            this.position = position;
            this.tags = tags;
        }

        public OnDeleteClickListener(int position, String source) {
            this.position = position;
            tags = new ArrayList<>();
            tags.add(source);
            tags.add("内容质量差");
            tags.add("旧闻、重复");
            tags.add("标题党");
        }

        @Override
        public void onClick(View view) {
            new DeletePopupWindow(mContext)
                    .setOnIntersterClickListener(RecommendAdpter.this)
                    .setPosition(position)
                    .setTags(tags).showPopupWindow(view);
        }
    }


    public final static class NoPicViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mBaseInfo;
        private ImageView mNoInterest;

        public NoPicViewHolder(View itemView) {
            super(itemView);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            mNoInterest = (ImageView) itemView.findViewById(R.id.img_no_interest);
        }

    }

    public final static class OnePicViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mBaseInfo;
        public ImageView imgOnePic;
        private ImageView mNoInterest;

        public OnePicViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            imgOnePic = (ImageView) itemView.findViewById(R.id.img_pic);
            mNoInterest = (ImageView) itemView.findViewById(R.id.img_no_interest);
        }

    }

    public final static class TwoPicViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mBaseInfo;
        public ImageView imgBigPic;
        private ImageView mNoInterest;

        public TwoPicViewHolder(View itemView) {
            super(itemView);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            imgBigPic = (ImageView) itemView.findViewById(R.id.img_pic);
            mNoInterest = (ImageView) itemView.findViewById(R.id.img_no_interest);
        }

    }


    public final static class ThreePicViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mBaseInfo;
        private ImageView mNoInterest;
        public ImageView imgOnePic, imgTwoPic, imgThreePic;

        public ThreePicViewHolder(View itemView) {
            super(itemView);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            imgOnePic = (ImageView) itemView.findViewById(R.id.img_pic_1);
            imgTwoPic = (ImageView) itemView.findViewById(R.id.img_pic_2);
            imgThreePic = (ImageView) itemView.findViewById(R.id.img_pic_3);
            mNoInterest = (ImageView) itemView.findViewById(R.id.img_no_interest);
        }

    }


    public final static class VideoViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mBaseInfo, mLength;
        public ImageView imgBigPic;
        private ImageView mNoInterest;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mBaseInfo = (TextView) itemView.findViewById(R.id.txt_baseinfo);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            mLength = (TextView) itemView.findViewById(R.id.txt_length);
            imgBigPic = (ImageView) itemView.findViewById(R.id.img_pic);
            mNoInterest = (ImageView) itemView.findViewById(R.id.img_no_interest);
        }

    }
}
