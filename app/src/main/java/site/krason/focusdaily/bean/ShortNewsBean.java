package site.krason.focusdaily.bean;

import java.util.List;

/**
 * @author Created by KCrason on 2016/12/26.
 * @email 535089696@qq.com
 */

public class ShortNewsBean {
    /**
     * id : shortNews_243018
     * type : shortNews
     * content :
     * title : 凤凰新闻搞笑段子
     * shareTitle : 凤凰新闻搞笑段子:
     * cid : 2
     * thumbnail : http://d.ifengimg.com/w166/p3.ifengimg.com/ifengiclient/ipic/2016102521/swoole_location_d1fcb0be4c3553e1d853e8cdfe5005cb_615598891_size37_w500_h412.jpg
     * cThumbnail : http://d.ifengimg.com/w166_h120/p3.ifengimg.com/ifengiclient/ipic/2016102521/swoole_location_d1fcb0be4c3553e1d853e8cdfe5005cb_615598891_size37_w500_h412.jpg
     * source :
     * status : 1
     * shareUrl : http://share.iclient.ifeng.com/news/sharenews.f?&fromType=spider&aid=243018
     * commentsUrl : http://share.iclient.ifeng.com/news/sharenews.f?&fromType=spider&aid=243018
     * ctime : 2016-10-25 21:50:09
     * utime : 2016-12-26 08:44:19
     * img : [{"url":"http://d.ifengimg.com/mw480/p3.ifengimg.com/ifengiclient/ipic/2016102521/swoole_location_d1fcb0be4c3553e1d853e8cdfe5005cb_615598891_size37_w500_h412.jpg","size":{"width":"480","height":"396"}}]
     * staticImg :
     * link : {"type":"shortNews","url":"http://api.iclient.ifeng.com/clientShortNewsDetail?id=243018"}
     * praise : 23
     * tread : 2
     * comments : 0
     * commentsall : 0
     * likes : 25
     */

    private String id;
    private String type;
    private String content;
    private String title;
    private String shareTitle;
    private int cid;
    private String thumbnail;
    private String cThumbnail;
    private String source;
    private int status;
    private String shareUrl;
    private String commentsUrl;
    private String ctime;
    private String utime;
    private String staticImg;
    private LinkBean link;
    private String praise;
    private String tread;
    private int comments;
    private int commentsall;
    private int likes;
    private List<ImgBean> img;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCThumbnail() {
        return cThumbnail;
    }

    public void setCThumbnail(String cThumbnail) {
        this.cThumbnail = cThumbnail;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public String getStaticImg() {
        return staticImg;
    }

    public void setStaticImg(String staticImg) {
        this.staticImg = staticImg;
    }

    public LinkBean getLink() {
        return link;
    }

    public void setLink(LinkBean link) {
        this.link = link;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public static class LinkBean {
        /**
         * type : shortNews
         * url : http://api.iclient.ifeng.com/clientShortNewsDetail?id=243018
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

    public static class ImgBean {
        /**
         * url : http://d.ifengimg.com/mw480/p3.ifengimg.com/ifengiclient/ipic/2016102521/swoole_location_d1fcb0be4c3553e1d853e8cdfe5005cb_615598891_size37_w500_h412.jpg
         * size : {"width":"480","height":"396"}
         */

        private String url;
        private SizeBean size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public SizeBean getSize() {
            return size;
        }

        public void setSize(SizeBean size) {
            this.size = size;
        }

        public static class SizeBean {
            /**
             * width : 480
             * height : 396
             */

            private String width;
            private String height;

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }
    }
}
