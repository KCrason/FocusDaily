package site.krason.focusdaily.events;

/**
 * @author Created by KCrason on 2017/1/11.
 * @email 535089696@qq.com
 */

public class NotifyChannelRefresh extends BaseEvent<String> {

    public NotifyChannelRefresh(int eventCode, String data) {
        super(eventCode, data);
    }

}
