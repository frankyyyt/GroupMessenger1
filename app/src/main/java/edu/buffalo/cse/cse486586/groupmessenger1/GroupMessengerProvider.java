package edu.buffalo.cse.cse486586.groupmessenger1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

import android.database.sqlite.SQLiteDatabase;

/**
 * GroupMessengerProvider is a key-value table. Once again, please note that we do not implement
 * full support for SQL as a usual ContentProvider does. We re-purpose ContentProvider's interface
 * to use it as a key-value table.
 * 
 * Please read:
 * 
 * http://developer.android.com/guide/topics/providers/content-providers.html
 * http://developer.android.com/reference/android/content/ContentProvider.html
 * 
 * before you start to get yourself familiarized with ContentProvider.
 * 
 * There are two methods you need to implement---insert() and query(). Others are optional and
 * will not be tested.
 * 
 * @author stevko
 *
 */
public class GroupMessengerProvider extends ContentProvider {

    GroupMessageSQLiteOpenHelper mDbHelper;
    private static final String DATABASE_AUTHORITY = "edu.buffalo.cse.cse486586.groupmessenger1.provider";
    private static final String DATABASE_SCHEME = "content";
    private final Uri DATABASE_CONTENT_URL = buildUri(DATABASE_SCHEME, DATABASE_AUTHORITY);

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // You do not need to implement this.
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // You do not need to implement this.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /*
         * TODO: You need to implement this method. Note that values will have two columns (a key
         * column and a value column) and one row that contains the actual (key, value) pair to be
         * inserted.
         * 
         * For actual storage, you can use any option. If you know how to use SQL, then you can use
         * SQLite. But this is not a requirement. You can use other storage options, such as the
         * internal storage option that we used in PA1. If you want to use that option, please
         * take a look at the code for PA1.
         */
        Log.v("insert", values.toString());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId;
        try {
            newRowId = db.insertOrThrow(
                    MessagesSchema.MessageEntry.TABLE_NAME,
                    null,
                    values);
            Log.d(MessagesSchema.MessageEntry.TABLE_NAME, "inserted into row:" + newRowId);
            return Uri.withAppendedPath(DATABASE_CONTENT_URL, String.valueOf(newRowId));
        } catch (SQLiteException e) {
            Log.e("error", "already inserted, try to update");
            String selection = MessagesSchema.MessageEntry.COLUMN_NAME_KEY + " = ?";
            String[] selectionArgs = { (String)values.get(MessagesSchema.MessageEntry.COLUMN_NAME_KEY) };
            newRowId = db.update(
                    MessagesSchema.MessageEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
            );
            Log.d(MessagesSchema.MessageEntry.TABLE_NAME, "inserted into row:" + newRowId);
            return uri;
        }

    }

    @Override
    public boolean onCreate() {
        // If you need to perform any one-time initialization task, please do it here.
        mDbHelper = new GroupMessageSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        /*
         * TODO: You need to implement this method. Note that you need to return a Cursor object
         * with the right format. If the formatting is not correct, then it is not going to work.
         * 
         * If you use SQLite, whatever is returned from SQLite is a Cursor object. However, you
         * still need to be careful because the formatting might still be incorrect.
         * 
         * If you use a file storage option, then it is your job to build a Cursor * object. I
         * recommend building a MatrixCursor described at:
         * http://developer.android.com/reference/android/database/MatrixCursor.html
         */
        Log.v("query", selection);
        // Gets the data repository in read mode
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectionClause = MessagesSchema.MessageEntry.COLUMN_NAME_KEY + " = \"" + selection + "\"";
        Log.d(selectionClause,selectionClause);
        Cursor c = db.query(
                MessagesSchema.MessageEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selectionClause,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // You do not need to implement this.
        return 0;
    }

    /**
     * buildUri() demonstrates how to build a URI for a ContentProvider.
     *
     * @param scheme
     * @param authority
     * @return the URI
     * @author stevko
     */
    private Uri buildUri(String scheme, String authority) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        return uriBuilder.build();
    }
}
