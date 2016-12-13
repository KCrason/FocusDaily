package site.krason.focusdaily.internet.http;

import retrofit2.http.POST;
import rx.Observable;
import site.krason.focusdaily.bean.KNewBean;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public interface RetrofitApi {

    @POST("/news/getNewsList.html")
    Observable<BaseResult<KNewBean>> getNewsList();
}
