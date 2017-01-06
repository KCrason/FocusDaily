package site.krason.focusdaily.utils;

import android.text.TextUtils;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.Call;

/**
 * @author Created by KCrason on 2016/12/20.
 * @email 535089696@qq.com
 */

public class HtmlUtils {


    public static void createNewsOfIFeng(final String url, final WebView webView, final String type) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (type != null) {
                    if (type.equals("doc")) {
                        webView.loadData(createHtml(response), "text/html; charset=UTF-8", null);
                    } else if (type.equals("web")) {
                        Document document = Jsoup.parse(response);
                        HtmlUtils.removeElementOfId(document, "header-wrap");
                        HtmlUtils.removeElementOfId(document, "recommend_area");
                        HtmlUtils.removeElementOfId(document, "comment_count");
                        HtmlUtils.removeElementOfId(document, "related_area");
                        HtmlUtils.removeElementOfClass(document, "footer_fixed");
                        HtmlUtils.removeElementOfClass(document, "hotcomment");
                        webView.loadData(document.toString(), "text/html; charset=UTF-8", null);
                    }
                }
            }
        });
    }


    public static String createHtml(String response) {
        JSONObject jsonObject = JSON.parseObject(response);
        JSONObject jsonObject1 = jsonObject.getJSONObject("body");
        String text = jsonObject1.getString("text");
        String title = jsonObject1.getString("title");
        String source = jsonObject1.getString("source");
        String editTime = jsonObject1.getString("editTime");
        String updateTime = jsonObject1.getString("updateTime");
        if (!TextUtils.isEmpty(updateTime)) {
            editTime = updateTime;
        }
        if (!TextUtils.isEmpty(editTime)) {
            editTime = editTime.replace("/", "-");
            editTime = editTime.substring(5, editTime.length() - 3);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<!DOCTYPE html>");
        stringBuffer.append("<html>");
        stringBuffer.append("<head>");
        stringBuffer.append("<title>" + title + "</title>");
        stringBuffer.append("<meta charset=\"utf-8\">");
        stringBuffer.append("<meta name=\"viewport\"\n" +
                "      content=\"width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>");
        stringBuffer.append("<style type=\"text/css\">");
        stringBuffer.append(" p {  line-height: 1.5;  letter-spacing: 1px; color:#030303; font-size:18px; }");
        stringBuffer.append(
                " .k-title {  line-height: 1.5;  letter-spacing: 1px; color:#000000; font-size:22px; }");
        stringBuffer.append(" .k-time{  line-height: 1.2;  letter-spacing: 1px;  color: #757575;font-size:13px;  }");
        stringBuffer.append(" #k-source{  color: #757575; font-size:13px; }");
        stringBuffer.append("body {  margin: 14px;  }");
        stringBuffer.append(" img {  display: block;  width: 100%;  height: auto;  margin: auto 0;  }");
        stringBuffer.append("a { text-decoration:none; }");
        stringBuffer.append("</style>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<p class=\"k-title\">" + title + "</p>");
        stringBuffer.append("<div><span id=\"k-source\">" + source + "</span>" + "&nbsp;&nbsp;&nbsp;" + "<span class=\"k-time\">" + editTime + "</span></div>");
        stringBuffer.append(text);
        stringBuffer.append("</body>");
        stringBuffer.append("</html>");
        return stringBuffer.toString();
    }

    /**
     * 用过元素名称清除元素
     *
     * @param element
     * @param elementName
     */
    public static void removeElementOfName(Element element, String elementName) {
        Elements elements = element.select(elementName);
        if (elements != null && elements.size() > 0) {
            elements.remove();
        }
    }

    /**
     * 通过class清除元素
     *
     * @param element
     * @param className
     */
    public static void removeElementOfClass(Element element, String className) {
        Elements elements = element.getElementsByClass(className);
        if (elements != null && elements.size() > 0) {
            elements.first().remove();
        }
    }

    /**
     * 清除固定id的元素
     *
     * @param element
     * @param id
     */
    public static void removeElementOfId(Element element, String id) {
        Element element2 = element.getElementById(id);
        if (element2 != null) {
            element2.remove();
        }
    }
}
