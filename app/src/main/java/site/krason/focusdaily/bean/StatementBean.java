package site.krason.focusdaily.bean;

import java.util.List;

/**
 * @author Created by KCrason on 2016/12/15.
 * @email 535089696@qq.com
 */

public class StatementBean {


    /**
     * code : 200
     * msg : success
     * newslist : [{"id":"13002","content":"君子笃于义而薄于利，敏于事而慎于言。","mrname":"陆贾"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * id : 13002
         * content : 君子笃于义而薄于利，敏于事而慎于言。
         * mrname : 陆贾
         */

        private String id;
        private String content;
        private String mrname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMrname() {
            return mrname;
        }

        public void setMrname(String mrname) {
            this.mrname = mrname;
        }
    }
}
