package site.krason.focusdaily.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import site.krason.focusdaily.R;
import site.krason.focusdaily.bean.KNewBean;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.utils.HtmlUtils;

import static site.krason.focusdaily.R.id.webview;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public class NewsDetailActivity extends BaseActivity {


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
        mWebView = (WebView) findViewById(webview);
        if (getIntent() != null) {
            KNewBean.ItemBean dataBean = (KNewBean.ItemBean) getIntent().getSerializableExtra(RecommendedFragment.KEY_NEWS);
            mWebView.setWebViewClient(new MyWebViewClient());
            mWebView.setWebChromeClient(new MyWebChromeClient());
            mWebView.getSettings().setJavaScriptEnabled(true);
            String url = dataBean.getLink().getUrl();
            String type = dataBean.getType();
            if (type != null) {
                HtmlUtils.createNewsOfIFeng(url, mWebView, type);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    public final class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    public final class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
