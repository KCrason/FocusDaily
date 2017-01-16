package site.krason.focusdaily.bean;

import java.util.List;

/**
 * @author Created by KCrason on 2016/12/28.
 * @email 535089696@qq.com
 */

public class LiveContentBean {


    private String create_date;
    private String lcat_id;
    private String lcat_name;
    private String uid;
    private String id;
    private String name;
    private String start_date;
    private String end_date;
    private String order;
    private String online_num;
    private String host_name;
    private String is_available;
    private String description;
    private String r_id;
    private String server_time;
    private ExtraBean extra;

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getLcat_id() {
        return lcat_id;
    }

    public void setLcat_id(String lcat_id) {
        this.lcat_id = lcat_id;
    }

    public String getLcat_name() {
        return lcat_name;
    }

    public void setLcat_name(String lcat_name) {
        this.lcat_name = lcat_name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOnline_num() {
        return online_num;
    }

    public void setOnline_num(String online_num) {
        this.online_num = online_num;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getIs_available() {
        return is_available;
    }

    public void setIs_available(String is_available) {
        this.is_available = is_available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public String getServer_time() {
        return server_time;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public static class ExtraBean {
        /**
         * guests_name :
         * vurl :
         * hasvideo : true
         * isvr : false
         * videoLive : http://ips.ifeng.com/video19.ifeng.com/video09/2016/12/28/4437758-102-086-0913.mp4##http://ips.ifeng.com/video19.ifeng.com/video09/2016/12/28/4437758-102-086-0913.mp4
         * vpic : http://p1.ifengimg.com/a/2016_53/4f40c04f6ccda6d.png
         * videoid :
         * online_support :
         * auto_sync_lroom_id :
         * lcat_spread_txt :
         * lcat_spread_url :
         * spread_link_type :
         * custom_type :
         * other_live_url :
         * label_pic :
         * o_hosts : [{"id":"72880141","name":"风直播","avatar":"http://p2.ifengimg.com/508df9aec9e2a/face/14828871646527.jpg","check":"","type":"1"},{"id":"52747381","name":"风直播","avatar":"http://p2.ifengimg.com/508df9aec9e2a/face/14828312416991.jpg","check":"","type":"1"}]
         */

        private String guests_name;
        private String vurl;
        private boolean hasvideo;
        private boolean isvr;
        private String videoLive;
        private String vpic;
        private String videoid;
        private String online_support;
        private String auto_sync_lroom_id;
        private String lcat_spread_txt;
        private String lcat_spread_url;
        private String spread_link_type;
        private String custom_type;
        private String other_live_url;
        private String label_pic;
        private List<OHostsBean> o_hosts;

        public String getGuests_name() {
            return guests_name;
        }

        public void setGuests_name(String guests_name) {
            this.guests_name = guests_name;
        }

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }

        public boolean isHasvideo() {
            return hasvideo;
        }

        public void setHasvideo(boolean hasvideo) {
            this.hasvideo = hasvideo;
        }

        public boolean isIsvr() {
            return isvr;
        }

        public void setIsvr(boolean isvr) {
            this.isvr = isvr;
        }

        public String getVideoLive() {
            return videoLive;
        }

        public void setVideoLive(String videoLive) {
            this.videoLive = videoLive;
        }

        public String getVpic() {
            return vpic;
        }

        public void setVpic(String vpic) {
            this.vpic = vpic;
        }

        public String getVideoid() {
            return videoid;
        }

        public void setVideoid(String videoid) {
            this.videoid = videoid;
        }

        public String getOnline_support() {
            return online_support;
        }

        public void setOnline_support(String online_support) {
            this.online_support = online_support;
        }

        public String getAuto_sync_lroom_id() {
            return auto_sync_lroom_id;
        }

        public void setAuto_sync_lroom_id(String auto_sync_lroom_id) {
            this.auto_sync_lroom_id = auto_sync_lroom_id;
        }

        public String getLcat_spread_txt() {
            return lcat_spread_txt;
        }

        public void setLcat_spread_txt(String lcat_spread_txt) {
            this.lcat_spread_txt = lcat_spread_txt;
        }

        public String getLcat_spread_url() {
            return lcat_spread_url;
        }

        public void setLcat_spread_url(String lcat_spread_url) {
            this.lcat_spread_url = lcat_spread_url;
        }

        public String getSpread_link_type() {
            return spread_link_type;
        }

        public void setSpread_link_type(String spread_link_type) {
            this.spread_link_type = spread_link_type;
        }

        public String getCustom_type() {
            return custom_type;
        }

        public void setCustom_type(String custom_type) {
            this.custom_type = custom_type;
        }

        public String getOther_live_url() {
            return other_live_url;
        }

        public void setOther_live_url(String other_live_url) {
            this.other_live_url = other_live_url;
        }

        public String getLabel_pic() {
            return label_pic;
        }

        public void setLabel_pic(String label_pic) {
            this.label_pic = label_pic;
        }

        public List<OHostsBean> getO_hosts() {
            return o_hosts;
        }

        public void setO_hosts(List<OHostsBean> o_hosts) {
            this.o_hosts = o_hosts;
        }

        public static class OHostsBean {
            /**
             * id : 72880141
             * name : 风直播
             * avatar : http://p2.ifengimg.com/508df9aec9e2a/face/14828871646527.jpg
             * check :
             * type : 1
             */

            private String id;
            private String name;
            private String avatar;
            private String check;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
