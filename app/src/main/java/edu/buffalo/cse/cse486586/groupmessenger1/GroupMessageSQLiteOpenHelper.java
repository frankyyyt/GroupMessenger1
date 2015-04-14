package edu.buffalo.cse.cse486586.groupmessenger1;

/**
 * Created by darrenxyli on 2/10/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Reference from: http://developer.android.com/training/basics/data-storage/databases.html
 */
public class GroupMessageSQLiteOpenHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GroupMessenger";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MessagesSchema.MessageEntry.TABLE_NAME + " (" +
                    MessagesSchema.MessageEntry.COLUMN_NAME_KEY + " TEXT PRIMARY KEY," +
                    MessagesSchema.MessageEntry.COLUMN_NAME_VALUE + " " + TEXT_TYPE  + ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MessagesSchema.MessageEntry.TABLE_NAME;

    public GroupMessageSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d(GroupMessageSQLiteOpenHelper.class.getName(),
                MessagesSchema.MessageEntry.TABLE_NAME + "table created");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
        Log.d(GroupMessageSQLiteOpenHelper.class.getName(),
                MessagesSchema.MessageEntry.TABLE_NAME + "upgraded from " + oldVersion + " to " +
                newVersion);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        Log.d(GroupMessageSQLiteOpenHelper.class.getName(),
                MessagesSchema.MessageEntry.TABLE_NAME + "degraded from " + oldVersion + " to " +
                        newVersion);
    }
}
