package com.example.travelcompanyapplication.db_controller.db_helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelcompanyapplication.db_controller.db_contracts.AccountFlightReaderContract;

import java.util.ArrayList;

public class AccountFlightDBHelper extends SQLiteOpenHelper {
    private AccountFlightDBHelper accountFlightDBHelper;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + AccountFlightReaderContract.FlightEntry.TABLE_NAME + " (" +
            AccountFlightReaderContract.FlightEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            AccountFlightReaderContract.FlightEntry.FLIGHT_ID + TEXT_TYPE + COMMA_SEP +
            AccountFlightReaderContract.FlightEntry.TICKETS_NUMBER + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + AccountFlightReaderContract.FlightEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AccountFlights.db";

    public AccountFlightDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public ArrayList<ArrayList<String>> getRecords(String selection, String[] selectionArgs) {
        SQLiteDatabase database = getReadableDatabase();
        String[] projection = {
                AccountFlightReaderContract.FlightEntry._ID,
                AccountFlightReaderContract.FlightEntry.FLIGHT_ID,
                AccountFlightReaderContract.FlightEntry.TICKETS_NUMBER
        };
        String sortOrder = AccountFlightReaderContract.FlightEntry.FLIGHT_ID + " ASC";
        Cursor cursor = database.query(
                AccountFlightReaderContract.FlightEntry.TABLE_NAME,
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
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(AccountFlightReaderContract.FlightEntry._ID)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(AccountFlightReaderContract.FlightEntry.FLIGHT_ID)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(AccountFlightReaderContract.FlightEntry.TICKETS_NUMBER)));
            table.add(row);
            cursor.moveToNext();
        }
        return table;
    }
}
