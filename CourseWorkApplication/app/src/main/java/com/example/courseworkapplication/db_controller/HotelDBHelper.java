package com.example.courseworkapplication.db_controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.courseworkapplication.db_controller.filler.HotelDBFiller;

import java.util.ArrayList;

public class HotelDBHelper extends SQLiteOpenHelper {
    private HotelDBFiller hotelDBFiller;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + HotelReaderContract.HotelEntry.TABLE_NAME + " (" +
            HotelReaderContract.HotelEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            HotelReaderContract.HotelEntry.CITY + TEXT_TYPE + COMMA_SEP +
            HotelReaderContract.HotelEntry.HOTEL_NAME + TEXT_TYPE + COMMA_SEP +
            HotelReaderContract.HotelEntry.MAP_LINK + TEXT_TYPE + COMMA_SEP +
            HotelReaderContract.HotelEntry.COST + TEXT_TYPE + COMMA_SEP +
            HotelReaderContract.HotelEntry.RATING + TEXT_TYPE + COMMA_SEP +
            HotelReaderContract.HotelEntry.IMAGE_URL + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + HotelReaderContract.HotelEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Hotels.db";

    public HotelDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (!(dataBaseExists(context))) {
            hotelDBFiller = new HotelDBFiller(this);
            hotelDBFiller.fillDB();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    private Boolean dataBaseExists(Context context) {
        try {
            SQLiteDatabase checkDataBase = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            checkDataBase.close();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public ArrayList<ArrayList<String>> getRecords(String selection, String[] selectionArgs) {
        SQLiteDatabase database = getReadableDatabase();
        String[] projection = {
                HotelReaderContract.HotelEntry._ID,
                HotelReaderContract.HotelEntry.CITY,
                HotelReaderContract.HotelEntry.HOTEL_NAME,
                HotelReaderContract.HotelEntry.MAP_LINK,
                HotelReaderContract.HotelEntry.COST,
                HotelReaderContract.HotelEntry.RATING,
                HotelReaderContract.HotelEntry.IMAGE_URL
        };
        String sortOrder = HotelReaderContract.HotelEntry.CITY + " ASC";
        Cursor cursor = database.query(
                HotelReaderContract.HotelEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        cursor.moveToFirst();
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            ArrayList<String> row = new ArrayList<>();
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(HotelReaderContract.HotelEntry._ID)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(HotelReaderContract.HotelEntry.CITY)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(HotelReaderContract.HotelEntry.HOTEL_NAME)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(HotelReaderContract.HotelEntry.MAP_LINK)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(HotelReaderContract.HotelEntry.COST)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(HotelReaderContract.HotelEntry.RATING)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(HotelReaderContract.HotelEntry.IMAGE_URL)));
            table.add(row);
            cursor.moveToNext();
        }
        return table;
    }
}
