package edu.buffalo.cse.cse486586.groupmessenger1;

import android.provider.BaseColumns;

/**
 * Created by darrenxyli on 2/10/15.
 */
public final class MessagesSchema {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MessagesSchema() {}

    /* Inner class that defines the table contents */
    public static abstract class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_KEY = "key";
        public static final String COLUMN_NAME_VALUE = "value";
    }
}
