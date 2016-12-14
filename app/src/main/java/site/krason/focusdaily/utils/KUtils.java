package site.krason.focusdaily.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import site.krason.focusdaily.KApplication;

/**
 * @author Created by KCrason on 2016/12/14.
 * @email 535089696@qq.com
 */

public class KUtils {

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
