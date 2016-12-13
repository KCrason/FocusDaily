package site.krason.focusdaily.internet.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.common.Constants;
import site.krason.focusdaily.internet.ObserverFilter;
import site.krason.focusdaily.internet.ResultFun1;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public class RetrofitManage<S> {

    public static GsonConverterFactory sGsonConverterFactory = GsonConverterFactory.create();
    public static RxJavaCallAdapterFactory sRxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static Retrofit getRetrofit() {
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(Constants.BAES_URL)
                .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                .addConverterFactory(sGsonConverterFactory)
                .build();
        return mRetrofit;
    }

    public static <S> S createObservable() {
        Retrofit retrofit = getRetrofit();
        return (S) retrofit.create(RetrofitApi.class);
    }

    public void getNewsList(ObserverFilter<KNewBean> beanObserverFilter) {
        Observable<BaseResult<KNewBean>> observable = createObservable();
        observable.map(new ResultFun1<KNewBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KNewBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(KNewBean kNewBean) {

                    }
                });
    }

}
