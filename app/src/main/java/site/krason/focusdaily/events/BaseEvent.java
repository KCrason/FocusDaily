package site.krason.focusdaily.events;

/**
 * @author Created by KCrason on 2016/12/23.
 * @email 535089696@qq.com
 */

public class BaseEvent<T> {

    private int mEventCode;

    private T mData;

    public BaseEvent(int eventCode, T data) {
        this.mEventCode = eventCode;
        this.mData = data;
    }

    public T getData() {
        return mData;
    }

    public int getEventCode() {
        return mEventCode;
    }

}
