package site.krason.focusdaily.bean;


import site.krason.focusdaily.sortdrag.IDragEntity;

/**
 * @author Created by KCrason on 2017/1/5.
 * @email 535089696@qq.com
 */

public class ChannelBean implements IDragEntity {

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    /**
     * 是否属于推荐0为是，1为否
     */
    private int isRecommend;


    private int id;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    private String channelName;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
