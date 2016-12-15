package site.krason.focusdaily.internet.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public class RetrofitManage<S> {

    public static GsonConverterFactory sGsonConverterFactory = GsonConverterFactory.create();
    public static RxJavaCallAdapterFactory sRxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static Retrofit getRetrofit(String baseUrl) {
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                .addConverterFactory(sGsonConverterFactory)
                .build();
        return mRetrofit;
    }


}
