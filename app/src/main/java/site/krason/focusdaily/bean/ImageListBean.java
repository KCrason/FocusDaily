package site.krason.focusdaily.bean;

import java.util.List;

/**
 * @author Created by KCrason on 2016/12/27.
 * @email 535089696@qq.com
 */

public class ImageListBean {
    private List<ItemBean> item;

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean {
        /**
         * thumbnail : http://d.ifengimg.com/w448_h248/p3.ifengimg.com/a/2016_53/dd5c759868c63e0_size183_w750_h422.jpg
         * title : 台已婚男勒死小三 载尸200公里弃空地
         * updateTime : 2016-12-27 11:49:03
         * documentId : imcp_117175591
         * commentsUrl : http://news.ifeng.com/a/20161227/50481984_0.shtml
         * links : [{"type":"slide","url":"http://api.3g.ifeng.com/ipadtestdoc?aid=117175591&channel=news&android=1"}]
         * type : photo
         * extensions : []
         * comments : 0
         * commentsall : 0
         * particpateCount : 0
         */

        private String thumbnail;
        private String title;
        private String updateTime;
        private String documentId;
        private String commentsUrl;
        private String type;
        private int comments;
        private int commentsall;
        private int particpateCount;
        private List<LinksBean> links;
        private List<?> extensions;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public String getCommentsUrl() {
            return commentsUrl;
        }

        public void setCommentsUrl(String commentsUrl) {
            this.commentsUrl = commentsUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getCommentsall() {
            return commentsall;
        }

        public void setCommentsall(int commentsall) {
            this.commentsall = commentsall;
        }

        public int getParticpateCount() {
            return particpateCount;
        }

        public void setParticpateCount(int particpateCount) {
            this.particpateCount = particpateCount;
        }

        public List<LinksBean> getLinks() {
            return links;
        }

        public void setLinks(List<LinksBean> links) {
            this.links = links;
        }

        public List<?> getExtensions() {
            return extensions;
        }

        public void setExtensions(List<?> extensions) {
            this.extensions = extensions;
        }

        public static class LinksBean {
            /**
             * type : slide
             * url : http://api.3g.ifeng.com/ipadtestdoc?aid=117175591&channel=news&android=1
             */

            private String type;
            private String url;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
