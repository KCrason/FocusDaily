package site.krason.focusdaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Created by KCrason on 2016/12/23.
 * @email 535089696@qq.com
 */

public class RecommendSlideBean implements Parcelable {

    private String title;
    private String id;
    private String type;
    private String links;
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.links);
        dest.writeString(this.thumbnail);
    }

    public RecommendSlideBean() {
    }

    protected RecommendSlideBean(Parcel in) {
        this.title = in.readString();
        this.id = in.readString();
        this.type = in.readString();
        this.links = in.readString();
        this.thumbnail = in.readString();
    }

    public static final Parcelable.Creator<RecommendSlideBean> CREATOR = new Parcelable.Creator<RecommendSlideBean>() {
        @Override
        public RecommendSlideBean createFromParcel(Parcel source) {
            return new RecommendSlideBean(source);
        }

        @Override
        public RecommendSlideBean[] newArray(int size) {
            return new RecommendSlideBean[size];
        }
    };
}
