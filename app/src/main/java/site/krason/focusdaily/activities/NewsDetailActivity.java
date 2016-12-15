package site.krason.focusdaily.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.bean.StatementBean;
import site.krason.focusdaily.common.Constants;
import site.krason.focusdaily.internet.http.RetrofitApi;
import site.krason.focusdaily.internet.http.RetrofitManage;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class NewsDetailActivity extends BaseActivity {

    public final static String KEY_NEWS = "key_news";

    CollapsingToolbarLayout collapsingToolbarLayout;

    ImageView iv;

    private TextView txtStatement;
    private WebView mWebView;

    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mWebView = (WebView) findViewById(R.id.webview);
        if (getIntent() != null) {
            KNewBean.DataBean dataBean = getIntent().getParcelableExtra(KEY_NEWS);
            mWebView.setWebViewClient(new MyWebViewClient());
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(dataBean.getNewsHtml());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void createStatement() {
        RetrofitManage.getRetrofit(Constants.BAES_URL_TIANXING).create(RetrofitApi.class).getStatement().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatementBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StatementBean statementBean) {
//                        txtStatement.setText(statementBean.getNewslist().get(0).getContent());
                    }
                });
    }


    public final class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
