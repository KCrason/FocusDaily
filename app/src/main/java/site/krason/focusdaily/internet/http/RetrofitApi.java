package site.krason.focusdaily.internet.http;

import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.bean.StatementBean;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public interface RetrofitApi {

    @POST("news/getNewList.html")
    Observable<KNewBean> getNewsList();


    @GET("txapi/dictum/?key=e96c3b01eefaefa4497ff6ab7cceb719")
    Observable<StatementBean> getStatement();
}
