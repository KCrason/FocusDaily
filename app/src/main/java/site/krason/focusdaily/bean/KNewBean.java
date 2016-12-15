package site.krason.focusdaily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public class KNewBean implements Serializable {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
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

        public static class ImagesBean implements Serializable {
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

        }

    }

}
