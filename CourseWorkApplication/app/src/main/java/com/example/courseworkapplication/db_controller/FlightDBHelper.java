package com.example.courseworkapplication.db_controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.courseworkapplication.db_controller.filler.FlightDBFiller;

import java.util.ArrayList;

public class FlightDBHelper extends SQLiteOpenHelper {
    private FlightDBFiller flightDBFiller;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FlightReaderContract.FlightEntry.TABLE_NAME + " (" +
            FlightReaderContract.FlightEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            FlightReaderContract.FlightEntry.DEPARTURE_CITY + TEXT_TYPE + COMMA_SEP +
            FlightReaderContract.FlightEntry.ARRIVAL_CITY + TEXT_TYPE + COMMA_SEP +
            FlightReaderContract.FlightEntry.DEPARTURE_DATE + TEXT_TYPE + COMMA_SEP +
            FlightReaderContract.FlightEntry.ARRIVAL_DATE + TEXT_TYPE + COMMA_SEP +
            FlightReaderContract.FlightEntry.AIRLINE + TEXT_TYPE + COMMA_SEP +
            FlightReaderContract.FlightEntry.COST + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FlightReaderContract.FlightEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Flights.db";

    public FlightDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (!(dataBaseExists(context))) {
            flightDBFiller = new FlightDBFiller(this);
            flightDBFiller.fillDB();
        }
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
                FlightReaderContract.FlightEntry._ID,
                FlightReaderContract.FlightEntry.DEPARTURE_CITY,
                FlightReaderContract.FlightEntry.ARRIVAL_CITY,
                FlightReaderContract.FlightEntry.DEPARTURE_DATE,
                FlightReaderContract.FlightEntry.ARRIVAL_DATE,
                FlightReaderContract.FlightEntry.AIRLINE,
                FlightReaderContract.FlightEntry.COST
        };
        String sortOrder = FlightReaderContract.FlightEntry.DEPARTURE_DATE + " ASC";
        Cursor cursor = database.query(
                FlightReaderContract.FlightEntry.TABLE_NAME,
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
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(FlightReaderContract.FlightEntry._ID)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(FlightReaderContract.FlightEntry.DEPARTURE_CITY)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(FlightReaderContract.FlightEntry.ARRIVAL_CITY)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(FlightReaderContract.FlightEntry.DEPARTURE_DATE)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(FlightReaderContract.FlightEntry.ARRIVAL_DATE)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(FlightReaderContract.FlightEntry.AIRLINE)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(FlightReaderContract.FlightEntry.COST)));
            table.add(row);
            cursor.moveToNext();
        }
        return table;
    }
}
