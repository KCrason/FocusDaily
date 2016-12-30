package site.krason.focusdaily.bean;

import java.util.List;

/**
 * @author Created by KCrason on 2016/12/28.
 * @email 535089696@qq.com
 */

public class LiveListBean {
    /**
     * uid :
     * lr_id : 102835
     * host_type : 1
     * host_name : 风直播
     * title :
     * title_link :
     * content : “祭湖醒网”的场面非常壮观，带有几分神秘与神奇。首先要在选好的场地上布置祭坛，冬季在冰面上，春季在湖岸山地上。祭坛设置方向按祭祀时间确定，一般在上午举行，面向太阳。
     * host_avatar : http://p2.ifengimg.com/508df9aec9e2a/face/14828871646527.jpg
     * create_date : 2016-12-28 09:55:32
     * img : [{"o_src":"http://p1.ifengimg.com/508df9aec9e2a/image/2016/12/28/14828901309148_686_389.png","src":"http://d.ifengimg.com/mw320/p1.ifengimg.com/508df9aec9e2a/image/2016/12/28/14828901309148_686_389.png","w":"686","h":"389"}]
     * video : []
     * audio : []
     * id : 115877
     * rltId : 115877
     * support_n : 0
     * tread_n : 0
     */

    private String uid;
    private String lr_id;
    private int host_type;
    private String host_name;
    private String title;
    private String title_link;
    private String content;
    private String host_avatar;
    private String create_date;
    private String id;
    private String rltId;
    private int support_n;
    private int tread_n;
    private List<ImgBean> img;
    private List<?> video;
    private List<?> audio;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLr_id() {
        return lr_id;
    }

    public void setLr_id(String lr_id) {
        this.lr_id = lr_id;
    }

    public int getHost_type() {
        return host_type;
    }

    public void setHost_type(int host_type) {
        this.host_type = host_type;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_link() {
        return title_link;
    }

    public void setTitle_link(String title_link) {
        this.title_link = title_link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHost_avatar() {
        return host_avatar;
    }

    public void setHost_avatar(String host_avatar) {
        this.host_avatar = host_avatar;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRltId() {
        return rltId;
    }

    public void setRltId(String rltId) {
        this.rltId = rltId;
    }

    public int getSupport_n() {
        return support_n;
    }

    public void setSupport_n(int support_n) {
        this.support_n = support_n;
    }

    public int getTread_n() {
        return tread_n;
    }

    public void setTread_n(int tread_n) {
        this.tread_n = tread_n;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public List<?> getVideo() {
        return video;
    }

    public void setVideo(List<?> video) {
        this.video = video;
    }

    public List<?> getAudio() {
        return audio;
    }

    public void setAudio(List<?> audio) {
        this.audio = audio;
    }

    public static class ImgBean {
        /**
         * o_src : http://p1.ifengimg.com/508df9aec9e2a/image/2016/12/28/14828901309148_686_389.png
         * src : http://d.ifengimg.com/mw320/p1.ifengimg.com/508df9aec9e2a/image/2016/12/28/14828901309148_686_389.png
         * w : 686
         * h : 389
         */

        private String o_src;
        private String src;
        private String w;
        private String h;

        public String getO_src() {
            return o_src;
        }

        public void setO_src(String o_src) {
            this.o_src = o_src;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getW() {
            return w;
        }

        public void setW(String w) {
            this.w = w;
        }

        public String getH() {
            return h;
        }

        public void setH(String h) {
            this.h = h;
        }
    }
}
