package site.krason.focusdaily.bean;

import java.util.List;

/**
 * @author Created by KCrason on 2016/12/26.
 * @email 535089696@qq.com
 */

public class ProjectBean {

    private String title;
    private String view;
    private String navTitle;
    private String nameLink;
    private ContentBean content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getNavTitle() {
        return navTitle;
    }

    public void setNavTitle(String navTitle) {
        this.navTitle = navTitle;
    }

    public String getNameLink() {
        return nameLink;
    }

    public void setNameLink(String nameLink) {
        this.nameLink = nameLink;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public List<PodItemsBean> getPodItems() {
        return podItems;
    }

    public void setPodItems(List<PodItemsBean> podItems) {
        this.podItems = podItems;
    }

    private List<PodItemsBean> podItems;

    public static class PodItemsBean{

        /**
         * count : 0
         * updateTime : 2016-12-25 09:57:43
         * hasSlide : true
         * links : [{"type":"slide","url":"http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_030100050471583","weburl":"http://share.iclient.ifeng.com/sharenews.f?aid=030100050471583"}]
         * thumbnails : ["http://d.ifengimg.com/w155_h107_q75/p0.ifengimg.com/a/2016_53/99b777fc3b49fff_size61_w800_h541.jpg","http://d.ifengimg.com/w155_h107_q75/p1.ifengimg.com/a/2016_53/c1dca5dfaa1aedb_size72_w980_h653.jpg","http://d.ifengimg.com/w155_h107_q75/p1.ifengimg.com/a/2016_53/506c4c025b5ff79_size71_w980_h653.jpg"]
         * intro :
         * title : 辽宁舰领衔8艘军舰跨海训练 日舰机尾随拍摄
         * hasVideo : false
         * thumbnail : http://d.ifengimg.com/w640_h360_q75/p3.ifengimg.com/cmpp/2016/12/26/e5c61ce924385ca561ec9c60994eadcc_size334_w672_h390.jpg
         * style : doc
         * commentsUrl : http://news.ifeng.com/a/20161225/50471583_0.shtml
         * shareUrl : http://inews.ifeng.com/030100050471583/share.shtml
         * commentType : 0
         * ago : 102128887
         * documentId : news_slide_030100050471583
         * commentCount : 116
         * particpateCount : 151
         */

        private int count;
        private String updateTime;
        private boolean hasSlide;
        private String intro;
        private String title;
        private boolean hasVideo;
        private String thumbnail;
        private String style;
        private String commentsUrl;
        private String shareUrl;
        private int commentType;
        private String ago;
        private String documentId;
        private int commentCount;
        private int particpateCount;
        private List<LinksBean> links;
        private List<String> thumbnails;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isHasSlide() {
            return hasSlide;
        }

        public void setHasSlide(boolean hasSlide) {
            this.hasSlide = hasSlide;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isHasVideo() {
            return hasVideo;
        }

        public void setHasVideo(boolean hasVideo) {
            this.hasVideo = hasVideo;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getCommentsUrl() {
            return commentsUrl;
        }

        public void setCommentsUrl(String commentsUrl) {
            this.commentsUrl = commentsUrl;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public int getCommentType() {
            return commentType;
        }

        public void setCommentType(int commentType) {
            this.commentType = commentType;
        }

        public String getAgo() {
            return ago;
        }

        public void setAgo(String ago) {
            this.ago = ago;
        }

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
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

        public List<String> getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(List<String> thumbnails) {
            this.thumbnails = thumbnails;
        }

        public static class LinksBean {
            /**
             * type : slide
             * url : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_030100050471583
             * weburl : http://share.iclient.ifeng.com/sharenews.f?aid=030100050471583
             */

            private String type;
            private String url;
            private String weburl;

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

            public String getWeburl() {
                return weburl;
            }

            public void setWeburl(String weburl) {
                this.weburl = weburl;
            }
        }
    }

    public static class ContentBean {

        /**
         * subTitle :
         * title :
         * borderColor : #0094dc
         * adverPic : http://p1.ifengimg.com/a/2016_53/1d1c3ef9ac6def7_size81_w750_h106.jpg
         * altTitle :
         * topicDocAd :
         * bgImage : http://p0.ifengimg.com/a/2016_53/82e05efa7b0ec20_size71_w640_h100.jpg
         * customBanner : http://d.ifengimg.com/w640_h100/p0.ifengimg.com/a/2016_53/82e05efa7b0ec20_size71_w640_h100.jpg
         */

        private String subTitle;
        private String title;
        private String borderColor;
        private String adverPic;
        private String altTitle;
        private String topicDocAd;
        private String bgImage;
        private String customBanner;

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        private String intro;


        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(String borderColor) {
            this.borderColor = borderColor;
        }

        public String getAdverPic() {
            return adverPic;
        }

        public void setAdverPic(String adverPic) {
            this.adverPic = adverPic;
        }

        public String getAltTitle() {
            return altTitle;
        }

        public void setAltTitle(String altTitle) {
            this.altTitle = altTitle;
        }

        public String getTopicDocAd() {
            return topicDocAd;
        }

        public void setTopicDocAd(String topicDocAd) {
            this.topicDocAd = topicDocAd;
        }

        public String getBgImage() {
            return bgImage;
        }

        public void setBgImage(String bgImage) {
            this.bgImage = bgImage;
        }

        public String getCustomBanner() {
            return customBanner;
        }

        public void setCustomBanner(String customBanner) {
            this.customBanner = customBanner;
        }
    }

}
