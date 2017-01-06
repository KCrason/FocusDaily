package site.krason.focusdaily.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.bean.ChannelBean;

/**
 * @author Created by KCrason on 2017/1/5.
 * @email 535089696@qq.com
 */

public class KDataBaseHelper {
    private static KDataBaseHelper sKDataBaseHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private KDataBase mKDataBase;

    public static KDataBaseHelper form() {
        synchronized (KDataBaseHelper.class) {
            if (sKDataBaseHelper == null) {
                sKDataBaseHelper = new KDataBaseHelper();
            }
        }
        return sKDataBaseHelper;
    }

    public KDataBaseHelper() {
        mKDataBase = new KDataBase();
    }

    public List<ChannelBean> getAllChannel(String[] selectionArgs) {
        List<ChannelBean> channelBeanArrayList = new ArrayList<>();
        mSQLiteDatabase = getSQLDataBase();
        if (mSQLiteDatabase != null) {
            Cursor cursor = mSQLiteDatabase.rawQuery("select * from kchannel where is_recommend=?", selectionArgs);
//            Cursor cursor = mSQLiteDatabase.query(FeedEntry.FeedEntryColumn.TABLE_NAME,
//                    new String[]{FeedEntry.FeedEntryColumn._ID,
//                            FeedEntry.FeedEntryColumn.KEY_NAME},
//                    null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                ChannelBean channelBean = new ChannelBean();
                channelBean.setId(cursor.getInt(cursor.getColumnIndex(FeedEntry.FeedEntryColumn._ID)));
                channelBean.setChannelName(cursor.getString(cursor.getColumnIndex(FeedEntry.FeedEntryColumn.KEY_NAME)));
                channelBean.setIsRecommend(cursor.getInt(cursor.getColumnIndex(FeedEntry.FeedEntryColumn.KEY_IS_RECOMMEND)));
                channelBeanArrayList.add(channelBean);
            }
            close(cursor);
        }
        return channelBeanArrayList;
    }

    public void insertData(ChannelBean channelBean) {
        mSQLiteDatabase = getSQLDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedEntry.FeedEntryColumn.KEY_NAME, channelBean.getChannelName());
        contentValues.put(FeedEntry.FeedEntryColumn.KEY_IS_RECOMMEND, channelBean.getIsRecommend());
        mSQLiteDatabase.insert(FeedEntry.FeedEntryColumn.TABLE_NAME, null, contentValues);
        close(null);
    }

    public void updateChannel(List<String> stringList, boolean isRecommend) {
        int size = stringList.size();
        for (int i = 0; i < size; i++) {
            ChannelBean channelBean = new ChannelBean();
            channelBean.setChannelName(stringList.get(i));
            if (isRecommend) {
                channelBean.setIsRecommend(1);
            } else {
                channelBean.setIsRecommend(0);
            }
            insertData(channelBean);
        }
    }

//    public void updateChannel(ChannelBean channelBean, String[] whereArgs) {
//        mSQLiteDatabase = getSQLDataBase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(FeedEntry.FeedEntryColumn.KEY_NAME, channelBean.getChannelName());
//        contentValues.put(FeedEntry.FeedEntryColumn.KEY_IS_RECOMMEND, channelBean.getIsRecommend());
//        mSQLiteDatabase.update(FeedEntry.FeedEntryColumn.TABLE_NAME, contentValues, "id=?", whereArgs);
//    }

    public int getCount() {
        mSQLiteDatabase = getSQLDataBase();
        Cursor cursor = mSQLiteDatabase.rawQuery("select count(*) from " + FeedEntry.FeedEntryColumn.TABLE_NAME, null);
        //cursor.moveToFirst()将cursor移动到第一条数据
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        close(cursor);
        return count;
    }

    public void deleteAllData() {
        mSQLiteDatabase = getSQLDataBase();
        mSQLiteDatabase.delete(FeedEntry.FeedEntryColumn.TABLE_NAME, null, null);
        close(null);
    }

    private SQLiteDatabase getSQLDataBase() {
        return mKDataBase.getWritableDatabase();
    }

    private void close(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
        if (mSQLiteDatabase != null) {
            mSQLiteDatabase.close();
        }
    }
}
