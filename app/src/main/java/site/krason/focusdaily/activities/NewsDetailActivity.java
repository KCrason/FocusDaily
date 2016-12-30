package site.krason.focusdaily.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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


    private WebView mWebView;

    @Override
    protected boolean isExistToolbar() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void initViews() {
        mWebView = (WebView) findViewById(webview);
        if (getIntent() != null) {
            KNewBean.ItemBean dataBean = (KNewBean.ItemBean) getIntent().getSerializableExtra(RecommendedFragment.KEY_NEWS);
            mWebView.setWebViewClient(new MyWebViewClient());
            mWebView.setWebChromeClient(new MyWebChromeClient());
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.addJavascriptInterface(new ImageListenerInterface(), "imageListener");
            String url = dataBean.getLink().getUrl();
            String type = dataBean.getType();
            if (type != null) {
                HtmlUtils.createNewsOfIFeng(url, mWebView, type);
            }
        }
    }

    public class ImageListenerInterface {

        @JavascriptInterface
        public void openBigImage(String i, String[] urls) {
            Intent intent = new Intent(NewsDetailActivity.this, PreviewImagesActivty.class);
            intent.putExtra("key_urls", urls);
            intent.putExtra("key_position", getIndex(urls, i));
            startActivity(intent);
        }
    }


    public int getIndex(String urls[], String url) {
        int curPosition = 0;
        for (int i = 0; i < urls.length; i++) {
            if (url != null && url.equals(urls[i])) {
                curPosition = i;
                break;
            }
        }
        return curPosition;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
            String script = "var imgs = document.getElementsByTagName(\"img\");\n" +
                    "    var urls = new Array();\n" +
                    "    for (var i = 0; i < imgs.length; i++) {\n" +
                    "        urls[i] = imgs[i].getAttribute(\"src\");\n" +
                    "        imgs[i].onclick = function () {\n" +
                    "            imageListener.openBigImage(this.src, urls);\n" +
                    "        }\n" +
                    "    }";
            view.loadUrl("javascript:" + script);
        }
    }
}
