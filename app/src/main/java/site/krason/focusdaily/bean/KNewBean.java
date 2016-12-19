package site.krason.focusdaily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public class KNewBean implements Serializable {


    /**
     * thumbnail : http://d.ifengimg.com/w134_h96_q80/p3.ifengimg.com/yidian/2016_40/8c3501e26d3393e_w491_h418.jpg
     * online : 1
     * title : 拥有漂亮老婆却依旧偷腥的八大男星，最后一个遭报应了吧！
     * showType : 0
     * source : 娱乐螺创
     * subscribe : {"cateid":"","type":"source","catename":"娱乐螺创","logo":null,"description":""}
     * updateTime : 2016/12/18 17:09:50
     * id : http://api.iclient.ifeng.com/ipadtestdoc?aid=imcp_116299977&perfectNew=1
     * documentId : imcp_116299977
     * type : doc
     * style : {"type":"slides","images":["http://d.ifengimg.com/w164_h113_q80/p3.ifengimg.com/yidian/2016_40/109197f5f6aada9_w500_h606.jpg","http://d.ifengimg.com/w164_h113_q80/p2.ifengimg.com/yidian/2016_40/322c74fb4c4c080_w435_h518.jpg","http://d.ifengimg.com/w164_h113_q80/p2.ifengimg.com/yidian/2016_40/71c0f5e46c415d2_w640_h410.jpg","http://d.ifengimg.com/w164_h113_q80/p3.ifengimg.com/yidian/2016_40/ec724afea17df05_w498_h711.jpg","http://d.ifengimg.com/w164_h113_q80/p3.ifengimg.com/yidian/2016_40/0dbfa1a76913c2d_w630_h410.jpg"],"slideCount":7}
     * hasSlide : true
     * commentsUrl : http://t.ifeng.com/appshare/7374564.shtml
     * comments : 7
     * commentsall : 9
     * link : {"type":"doc","url":"http://api.iclient.ifeng.com/ipadtestdoc?aid=imcp_116299977&perfectNew=1","weburl":"http://share.iclient.ifeng.com/sharenews.f?aid=116299977"}
     * simId : clusterId_2611184
     * reftype : remcommend
     * recomToken : 14821371698976345
     */

    private String thumbnail;
    private String online;
    private String title;
    private String showType;
    private String source;
    private SubscribeBean subscribe;
    private String updateTime;
    private String id;
    private String documentId;
    private String type;
    private StyleBean style;
    private boolean hasSlide;
    private String commentsUrl;
    private String comments;
    private String commentsall;
    private LinkBean link;

    public PHVideoBean getPhvideo() {
        return phvideo;
    }

    public void setPhvideo(PHVideoBean phvideo) {
        this.phvideo = phvideo;
    }

    private PHVideoBean phvideo;
    private String simId;
    private String reftype;
    private String recomToken;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public SubscribeBean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(SubscribeBean subscribe) {
        this.subscribe = subscribe;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StyleBean getStyle() {
        return style;
    }

    public void setStyle(StyleBean style) {
        this.style = style;
    }

    public boolean isHasSlide() {
        return hasSlide;
    }

    public void setHasSlide(boolean hasSlide) {
        this.hasSlide = hasSlide;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommentsall() {
        return commentsall;
    }

    public void setCommentsall(String commentsall) {
        this.commentsall = commentsall;
    }

    public LinkBean getLink() {
        return link;
    }

    public void setLink(LinkBean link) {
        this.link = link;
    }

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public String getReftype() {
        return reftype;
    }

    public void setReftype(String reftype) {
        this.reftype = reftype;
    }

    public String getRecomToken() {
        return recomToken;
    }

    public void setRecomToken(String recomToken) {
        this.recomToken = recomToken;
    }

    public static class SubscribeBean implements Serializable{
        /**
         * cateid :
         * type : source
         * catename : 娱乐螺创
         * logo : null
         * description :
         */

        private String cateid;
        private String type;
        private String catename;
        private Object logo;
        private String description;

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCatename() {
            return catename;
        }

        public void setCatename(String catename) {
            this.catename = catename;
        }

        public Object getLogo() {
            return logo;
        }

        public void setLogo(Object logo) {
            this.logo = logo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class StyleBean implements Serializable{
        /**
         * type : slides
         * images : ["http://d.ifengimg.com/w164_h113_q80/p3.ifengimg.com/yidian/2016_40/109197f5f6aada9_w500_h606.jpg","http://d.ifengimg.com/w164_h113_q80/p2.ifengimg.com/yidian/2016_40/322c74fb4c4c080_w435_h518.jpg","http://d.ifengimg.com/w164_h113_q80/p2.ifengimg.com/yidian/2016_40/71c0f5e46c415d2_w640_h410.jpg","http://d.ifengimg.com/w164_h113_q80/p3.ifengimg.com/yidian/2016_40/ec724afea17df05_w498_h711.jpg","http://d.ifengimg.com/w164_h113_q80/p3.ifengimg.com/yidian/2016_40/0dbfa1a76913c2d_w630_h410.jpg"]
         * slideCount : 7
         */

        private String type;
        private int slideCount;
        private List<String> images;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSlideCount() {
            return slideCount;
        }

        public void setSlideCount(int slideCount) {
            this.slideCount = slideCount;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class PHVideoBean implements Serializable{
        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        private String channelName;
        private int length;
    }


    public static class LinkBean implements Serializable{
        /**
         * type : doc
         * url : http://api.iclient.ifeng.com/ipadtestdoc?aid=imcp_116299977&perfectNew=1
         * weburl : http://share.iclient.ifeng.com/sharenews.f?aid=116299977
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
