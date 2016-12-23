package site.krason.focusdaily.events;

/**
 * @author Created by KCrason on 2016/12/23.
 * @email 535089696@qq.com
 */

public class RefreshEvent extends BaseEvent<Integer> {

    public RefreshEvent(int eventCode, Integer data) {
        super(eventCode, data);
    }
}
