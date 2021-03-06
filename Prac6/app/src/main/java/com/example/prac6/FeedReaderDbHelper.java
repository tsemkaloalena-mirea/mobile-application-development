package com.example.task6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
            FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            FeedReaderContract.FeedEntry.NUMBER_OF_RESUME + TEXT_TYPE + COMMA_SEP +
            FeedReaderContract.FeedEntry.DATE_OF_RESUME + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
