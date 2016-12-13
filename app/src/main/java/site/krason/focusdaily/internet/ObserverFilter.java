package site.krason.focusdaily.internet;

import rx.Observer;
import site.krason.focusdaily.internet.http.BaseResult;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public abstract class ObserverFilter<T> implements Observer<BaseResult<T>> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onFailure(e, 404);
    }

    public abstract void onSuccess(BaseResult<T> tBaseResult);

    public abstract void onFailure(Throwable throwable, int code);

    @Override
    public void onNext(BaseResult<T> tBaseResult) {
        if (tBaseResult.getResultCode() == 0) {
            onSuccess(tBaseResult);
        } else {
            onFailure(null, tBaseResult.getResultCode());
        }
    }
}
