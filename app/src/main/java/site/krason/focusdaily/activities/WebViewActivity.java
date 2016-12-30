package site.krason.focusdaily.activities;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2016/12/29.
 * @email 535089696@qq.com
 */

public class WebViewActivity extends BaseActivity {
    @Override
    protected boolean isExistToolbar() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }


    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("key_url", url);
        context.startActivity(intent);
    }

    private WebView mWebView;

    @Override
    public void initViews() {
        String url = getIntent().getStringExtra("key_url");
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
    }


    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
