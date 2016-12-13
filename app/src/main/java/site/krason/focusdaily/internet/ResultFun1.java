package site.krason.focusdaily.internet;

import rx.functions.Func1;
import site.krason.focusdaily.internet.http.BaseResult;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public class ResultFun1<T> implements Func1<BaseResult<T>, T> {
    @Override
    public T call(BaseResult<T> tBaseResult) {
        if (tBaseResult.getResultCode() != 0) {
            throw new ApiExcepiton(tBaseResult.getResultCode());
        }
        return tBaseResult.getData();
    }
}
