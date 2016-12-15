package site.krason.focusdaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public class KNewBean implements Parcelable {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * newsId : 4250
         * newsType : it
         * source : 网易IT
         * newsUrl : http://tech.163.com/16/1123/07/C6HPT8QJ00097U7T.html
         * title : 超级高铁全尺寸模型开工建设 明年有望全面测试
         * cTime : 2016-11-23 07:21:00.0
         * newsHtml : http://www.krason.site/newshtml/e2e1f8f9-f3b4-47b9-bba4-fd028a4be03d.html
         * coverUrl : http://cms-bucket.nosdn.127.net/fa25af780b51495b9012c114ee93724220161123080351.jpeg?imageView&thumbnail=550x0
         * imageCount : 5
         * images : [{"imageUrl":"http://cms-bucket.nosdn.127.net/fa25af780b51495b9012c114ee93724220161123080351.jpeg?imageView&thumbnail=550x0","imageId":7459,"newsId":4250},{"imageUrl":"http://cms-bucket.nosdn.127.net/916edacda86043d4b32f46b84aa2173b20161123080351.jpeg?imageView&thumbnail=550x0","imageId":7460,"newsId":4250},{"imageUrl":"http://cms-bucket.nosdn.127.net/cd07aaed83464fc49032a1e44d210ccf20161123080351.jpeg?imageView&thumbnail=550x0","imageId":7461,"newsId":4250},{"imageUrl":"http://cms-bucket.nosdn.127.net/7db7021eefc94c5897ef27b9b925b11420161123080351.jpeg?imageView&thumbnail=550x0","imageId":7462,"newsId":4250},{"imageUrl":"http://cms-bucket.nosdn.127.net/791323d39bb042cc94ecb1f4d56e4b3a20161123080351.jpeg?imageView&thumbnail=550x0","imageId":7463,"newsId":4250}]
         */

        private int newsId;
        private String newsType;
        private String source;
        private String newsUrl;
        private String title;
        private String cTime;
        private String newsHtml;
        private String coverUrl;
        private int imageCount;
        private List<ImagesBean> images;

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }

        public String getNewsType() {
            return newsType;
        }

        public void setNewsType(String newsType) {
            this.newsType = newsType;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getNewsUrl() {
            return newsUrl;
        }

        public void setNewsUrl(String newsUrl) {
            this.newsUrl = newsUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCTime() {
            return cTime;
        }

        public void setCTime(String cTime) {
            this.cTime = cTime;
        }

        public String getNewsHtml() {
            return newsHtml;
        }

        public void setNewsHtml(String newsHtml) {
            this.newsHtml = newsHtml;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getImageCount() {
            return imageCount;
        }

        public void setImageCount(int imageCount) {
            this.imageCount = imageCount;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean implements Parcelable {
            /**
             * imageUrl : http://cms-bucket.nosdn.127.net/fa25af780b51495b9012c114ee93724220161123080351.jpeg?imageView&thumbnail=550x0
             * imageId : 7459
             * newsId : 4250
             */

            private String imageUrl;
            private int imageId;
            private int newsId;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public int getImageId() {
                return imageId;
            }

            public void setImageId(int imageId) {
                this.imageId = imageId;
            }

            public int getNewsId() {
                return newsId;
            }

            public void setNewsId(int newsId) {
                this.newsId = newsId;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.imageUrl);
                dest.writeInt(this.imageId);
                dest.writeInt(this.newsId);
            }

            public ImagesBean() {
            }

            protected ImagesBean(Parcel in) {
                this.imageUrl = in.readString();
                this.imageId = in.readInt();
                this.newsId = in.readInt();
            }

            public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
                @Override
                public ImagesBean createFromParcel(Parcel source) {
                    return new ImagesBean(source);
                }

                @Override
                public ImagesBean[] newArray(int size) {
                    return new ImagesBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.newsId);
            dest.writeString(this.newsType);
            dest.writeString(this.source);
            dest.writeString(this.newsUrl);
            dest.writeString(this.title);
            dest.writeString(this.cTime);
            dest.writeString(this.newsHtml);
            dest.writeString(this.coverUrl);
            dest.writeInt(this.imageCount);
            dest.writeList(this.images);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.newsId = in.readInt();
            this.newsType = in.readString();
            this.source = in.readString();
            this.newsUrl = in.readString();
            this.title = in.readString();
            this.cTime = in.readString();
            this.newsHtml = in.readString();
            this.coverUrl = in.readString();
            this.imageCount = in.readInt();
            this.images = new ArrayList<ImagesBean>();
            in.readList(this.images, ImagesBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    public KNewBean() {
    }

    protected KNewBean(Parcel in) {
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<KNewBean> CREATOR = new Parcelable.Creator<KNewBean>() {
        @Override
        public KNewBean createFromParcel(Parcel source) {
            return new KNewBean(source);
        }

        @Override
        public KNewBean[] newArray(int size) {
            return new KNewBean[size];
        }
    };
}
