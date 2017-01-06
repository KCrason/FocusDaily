package site.krason.focusdaily.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import site.krason.focusdaily.KApplication;

/**
 * @author Created by KCrason on 2017/1/5.
 * @email 535089696@qq.com
 */

public class KDataBase extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "kcrason.db";
    private final static String SQL_CREATE_COMMENT_TABLE = "CREATE TABLE IF NOT EXISTS " +
            FeedEntry.FeedEntryColumn.TABLE_NAME +
            " (" + FeedEntry.FeedEntryColumn._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FeedEntry.FeedEntryColumn.KEY_NAME + " VARCHAR(20)," +
            FeedEntry.FeedEntryColumn.KEY_IS_RECOMMEND + " INT)";

    public KDataBase() {
        super(KApplication.sContext, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_COMMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
