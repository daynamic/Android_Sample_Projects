package com.example.asharm93.colormemory;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by asharm93 on 3/13/17.
 */

public class PlayerContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private PlayerContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";
        public static final String COLUMN_NAME_PLAYER = "playername";
        public static final String COLUMN_NAME_SCORE = "scorevalue";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlayerContract.ScoreEntry.TABLE_NAME + " (" +
                    PlayerContract.ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    PlayerContract.ScoreEntry.COLUMN_NAME_PLAYER + TEXT_TYPE + COMMA_SEP +
                    PlayerContract.ScoreEntry.COLUMN_NAME_SCORE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlayerContract.ScoreEntry.TABLE_NAME;

    public static void createDb(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d("TAG", "Database Created!");
    }

    public static void deleteDb(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}
