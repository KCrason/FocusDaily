package site.krason.focusdaily.bean;

import java.util.List;

/**
 * @author Created by KCrason on 2017/1/17.
 * @email 535089696@qq.com
 */

public class TopicBean {

    private MBlogBean mMBlogBeen;


    public MBlogBean getMBlogBeen() {
        return mMBlogBeen;
    }

    public void setMBlogBeen(MBlogBean MBlogBeen) {
        mMBlogBeen = MBlogBeen;
    }


    public static class MBlogBean {
        private int attitudes_count;
        private int comments_count;
        private UserBean user;

        public PageInfoBean getPage_info() {
            return page_info;
        }

        public void setPage_info(PageInfoBean page_info) {
            this.page_info = page_info;
        }

        private PageInfoBean page_info;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }


        public int getAttitudes_count() {
            return attitudes_count;
        }

        public void setAttitudes_count(int attitudes_count) {
            this.attitudes_count = attitudes_count;
        }

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private String text;
        private String source;

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        private String created_at;

        public List<String> getPic_ids() {
            return pic_ids;
        }

        public void setPic_ids(List<String> pic_ids) {
            this.pic_ids = pic_ids;
        }

        public List<String> pic_ids;


        public static class UserBean {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProfile_image_url() {
                return profile_image_url;
            }

            public void setProfile_image_url(String profile_image_url) {
                this.profile_image_url = profile_image_url;
            }

            private String profile_image_url;

        }

        public static class PageInfoBean {
            public String getPage_pic() {
                return page_pic;
            }

            public void setPage_pic(String page_pic) {
                this.page_pic = page_pic;
            }

            private String page_pic;

        }
    }


    public List<ImageBean> getImageBeanList() {
        return mImageBeanList;
    }

    public void setImageBeanList(List<ImageBean> imageBeanList) {
        mImageBeanList = imageBeanList;
    }


    private List<ImageBean> mImageBeanList;


}
