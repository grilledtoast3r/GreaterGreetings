package com.example.ggprototype_v2.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class AlarmReminderContract {

    private AlarmReminderContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.ggprototype_v2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_VEHICLE = "reminder-path";

    public static final class AlarmReminderEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VEHICLE);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        public final static String TABLE_NAME = "events";

        public final static String _ID = BaseColumns._ID;

        public static final String KEY_TITLE = "title";
        public static final String KEY_DATE = "date";
        public static final String KEY_TIME = "time";
        public static final String KEY_REPEAT = "repeat";
        public static final String KEY_REPEAT_NO = "repeat_no";
        public static final String KEY_REPEAT_TYPE = "repeat_type";
        public static final String KEY_ACTIVE = "active";

    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
}
/**
Adapted from https://www.youtube.com/watch?v=P46LTiPlvUA&list=PLyCWpLZQcXMoti4rK1gGkRM7vXo82n4Lm&index=5&t=691s
 */