package com.example.travelcompanyapplication.db_controller.db_helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelcompanyapplication.db_controller.db_contracts.AccountHotelReaderContract;

import java.util.ArrayList;

public class AccountHotelDBHelper extends SQLiteOpenHelper {
    private AccountHotelDBHelper accountHotelDBHelper;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + AccountHotelReaderContract.HotelEntry.TABLE_NAME + " (" +
            AccountHotelReaderContract.HotelEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            AccountHotelReaderContract.HotelEntry.HOTEL_ID + TEXT_TYPE + COMMA_SEP +
            AccountHotelReaderContract.HotelEntry.DEPARTURE_DATE + TEXT_TYPE + COMMA_SEP +
            AccountHotelReaderContract.HotelEntry.ARRIVAL_DATE + TEXT_TYPE + COMMA_SEP +
            AccountHotelReaderContract.HotelEntry.ADULTS_NUMBER + TEXT_TYPE + COMMA_SEP +
            AccountHotelReaderContract.HotelEntry.KIDS_NUMBER + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + AccountHotelReaderContract.HotelEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AccountHotels.db";

    public AccountHotelDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        if (!(dataBaseExists(context))) {
//            hotelDBFiller = new HotelDBFiller(this);
//            hotelDBFiller.fillDB();
//        }
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

    //    private Boolean dataBaseExists(Context context) {
//        try {
//            SQLiteDatabase checkDataBase = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
//            checkDataBase.close();
//        } catch (SQLiteException e) {
//            return false;
//        }
//        return true;
//    }
//
    public ArrayList<ArrayList<String>> getRecords(String selection, String[] selectionArgs) {
        SQLiteDatabase database = getReadableDatabase();
        String[] projection = {
                AccountHotelReaderContract.HotelEntry._ID,
                AccountHotelReaderContract.HotelEntry.DEPARTURE_DATE,
                AccountHotelReaderContract.HotelEntry.ARRIVAL_DATE,
                AccountHotelReaderContract.HotelEntry.HOTEL_ID,
                AccountHotelReaderContract.HotelEntry.ADULTS_NUMBER,
                AccountHotelReaderContract.HotelEntry.KIDS_NUMBER
        };
        String sortOrder = AccountHotelReaderContract.HotelEntry.HOTEL_ID + " ASC";
        Cursor cursor = database.query(
                AccountHotelReaderContract.HotelEntry.TABLE_NAME,
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
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(AccountHotelReaderContract.HotelEntry._ID)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(AccountHotelReaderContract.HotelEntry.DEPARTURE_DATE)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(AccountHotelReaderContract.HotelEntry.ARRIVAL_DATE)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(AccountHotelReaderContract.HotelEntry.HOTEL_ID)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(AccountHotelReaderContract.HotelEntry.ADULTS_NUMBER)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(AccountHotelReaderContract.HotelEntry.KIDS_NUMBER)));
            table.add(row);
            cursor.moveToNext();
        }
        return table;
    }
}
