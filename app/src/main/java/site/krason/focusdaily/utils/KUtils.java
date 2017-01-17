package site.krason.focusdaily.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import site.krason.focusdaily.KApplication;

import static site.krason.focusdaily.common.Constants.DECIMAL_FORMAT;

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

    public static String betweenOf2Days2(String date) {
        try {
            Date date1 = new Date(date);
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


    public static int getScreenHeight() {
        return KApplication.sContext.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return KApplication.sContext.getResources().getDisplayMetrics().widthPixels;
    }

    public static void showSnackbar(String text, View parentView) {
        Snackbar snackbar = Snackbar.make(parentView, text, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.parseColor("#c8000000"));
        snackbar.show();
    }


    public static String filterStringValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        } else {
            return value;
        }
    }

    public static void showSwipeRefresh(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    public static void hideSwipeRefresh(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    public static String formatVideoDuration(int duration) {
        int minute = duration / 60;
        int seconds = duration % 60;
        return add0(minute) + ":" + add0(seconds);
    }

    private static String add0(int value) {
        if (value < 0) {
            value = 0;
        }
        if (value <= 9) {
            return "0" + value;
        }
        return String.valueOf(value);
    }

    public static String transformPlayCount(String playCount) {
        try {
            int curPlayCount = Integer.parseInt(playCount);
            if (curPlayCount >= 10000) {
                float value = (curPlayCount / 10000f);
                return DECIMAL_FORMAT.format(value) + "万次播放";
            } else {
                return playCount + "次播放";
            }
        } catch (Exception e) {
        }
        return "0";
    }

    public final static class Network {

        public static ConnectivityManager getConnectivityManager() {
            Context context = KApplication.sContext;
            return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        public static boolean isExistNetwork() {
            ConnectivityManager connectivityManager = getConnectivityManager();
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

        //判断WiFi是否打开
        public static boolean isWiFi() {
            ConnectivityManager connectivityManager = getConnectivityManager();
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
            return false;
        }

        //判断移动数据是否打开
        public static boolean isMobile() {
            ConnectivityManager connectivityManager = getConnectivityManager();
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }
            }
            return false;
        }
    }
}
