package site.krason.focusdaily.bean;

import java.util.List;

/**
 * @author Created by KCrason on 2016/12/21.
 * @email 535089696@qq.com
 */

public class RelativeVideoInfo {
    private List<GuidRelativeVideoInfoBean> guidRelativeVideoInfo;

    public List<GuidRelativeVideoInfoBean> getGuidRelativeVideoInfo() {
        return guidRelativeVideoInfo;
    }

    public void setGuidRelativeVideoInfo(List<GuidRelativeVideoInfoBean> guidRelativeVideoInfo) {
        this.guidRelativeVideoInfo = guidRelativeVideoInfo;
    }

    public static class GuidRelativeVideoInfoBean {


        private String guid;
        private String name;
        private String praise;
        private String tread;
        private String playTime;
        private String type;
        private String createDate;
        private int duration;
        private String columnName;
        private String columnId;
        private String seTitle;
        private String cpName;
        private String searchPath;
        private String shareUrl;
        private String newShareUrl;
        private String commentNo;
        private String commentsAll;
        private String itemId;
        private List<FilesBean> files;

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public String getTread() {
            return tread;
        }

        public void setTread(String tread) {
            this.tread = tread;
        }

        public String getPlayTime() {
            return playTime;
        }

        public void setPlayTime(String playTime) {
            this.playTime = playTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnId() {
            return columnId;
        }

        public void setColumnId(String columnId) {
            this.columnId = columnId;
        }

        public String getSeTitle() {
            return seTitle;
        }

        public void setSeTitle(String seTitle) {
            this.seTitle = seTitle;
        }

        public String getCpName() {
            return cpName;
        }

        public void setCpName(String cpName) {
            this.cpName = cpName;
        }

        public String getSearchPath() {
            return searchPath;
        }

        public void setSearchPath(String searchPath) {
            this.searchPath = searchPath;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getNewShareUrl() {
            return newShareUrl;
        }

        public void setNewShareUrl(String newShareUrl) {
            this.newShareUrl = newShareUrl;
        }

        public String getCommentNo() {
            return commentNo;
        }

        public void setCommentNo(String commentNo) {
            this.commentNo = commentNo;
        }

        public String getCommentsAll() {
            return commentsAll;
        }

        public void setCommentsAll(String commentsAll) {
            this.commentsAll = commentsAll;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public List<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(List<FilesBean> files) {
            this.files = files;
        }

        public static class FilesBean {
            /**
             * useType : 280
             * filesize : 13473
             * mediaUrl : http://ips.ifeng.com/video19.ifeng.com/video09/2016/09/26/595625-280-068-232858.mp4
             * spliteTime :
             */

            private int useType;
            private int filesize;
            private String mediaUrl;
            private String spliteTime;

            public int getUseType() {
                return useType;
            }

            public void setUseType(int useType) {
                this.useType = useType;
            }

            public int getFilesize() {
                return filesize;
            }

            public void setFilesize(int filesize) {
                this.filesize = filesize;
            }

            public String getMediaUrl() {
                return mediaUrl;
            }

            public void setMediaUrl(String mediaUrl) {
                this.mediaUrl = mediaUrl;
            }

            public String getSpliteTime() {
                return spliteTime;
            }

            public void setSpliteTime(String spliteTime) {
                this.spliteTime = spliteTime;
            }
        }
    }
}
