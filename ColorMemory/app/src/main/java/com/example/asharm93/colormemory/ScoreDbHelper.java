package com.example.asharm93.colormemory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asharm93 on 3/13/17.
 */

public class ScoreDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ScoreReader.db";

    private static final String TAG = ScoreDbHelper.class.getName();

    public ScoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        PlayerContract.createDb(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        PlayerContract.deleteDb(db);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
