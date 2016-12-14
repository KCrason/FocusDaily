package site.krason.focusdaily;

import android.app.Application;
import android.content.Context;

/**
 * @author Created by KCrason on 2016/12/14.
 * @email 535089696@qq.com
 */

public class KApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
