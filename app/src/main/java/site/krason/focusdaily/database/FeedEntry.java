package site.krason.focusdaily.database;

import android.provider.BaseColumns;

/**
 * @author Created by KCrason on 2017/1/5.
 * @email 535089696@qq.com
 */

public class FeedEntry {

    public FeedEntry() {
    }

    public abstract static class FeedEntryColumn implements BaseColumns {
        //表名
        public final static String TABLE_NAME = "kchannel";
        //频道名称
        public final static String KEY_NAME = "channel_name";

        public final static String KEY_IS_RECOMMEND = "is_recommend";
    }
}
