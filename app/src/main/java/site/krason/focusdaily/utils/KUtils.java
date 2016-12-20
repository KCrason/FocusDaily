package site.krason.focusdaily.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import site.krason.focusdaily.KApplication;

/**
 * @author Created by KCrason on 2016/12/14.
 * @email 535089696@qq.com
 */

public class KUtils {

    private final static int SECONDS_OF_DAY = 3600 * 24;
    private final static int SECONDS_OF_HOUR = 3600;
    private final static int SECONDS_OF_MIBUTE = 60;

    public static int dip2px(int dipValue) {
        float scale = KApplication.sContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static String betweenOf2Days(String date) {
        try {
            String d = date.replace("/", "-");
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
            Date date2 = new Date();
            long ls = (date2.getTime() - date1.getTime()) / 1000;
            int days = (int) (ls / SECONDS_OF_DAY);
            int hour = (int) (ls - (days * SECONDS_OF_DAY)) / SECONDS_OF_HOUR;
            int minute = (int) ((ls - days * SECONDS_OF_DAY - hour * SECONDS_OF_HOUR) / SECONDS_OF_MIBUTE);

            if (days > 0) {
                return days + "天前";
            } else if (hour > 0) {
                return hour + "小时前";
            } else {
                if (minute >= 1) {
                    return minute + "分钟前";
                } else {
                    return "刚刚";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "刚刚";
    }

    public static void showSnackbar(String text, View parentView) {
        Snackbar snackbar = Snackbar.make(parentView, text, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.parseColor("#7d000000"));
        snackbar.show();
    }

    public final static class Network {

        public static boolean isExistNetwork() {
            Context context = KApplication.sContext;
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    // 当前网络是连接的
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        // 当前所连接的网络可用
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
