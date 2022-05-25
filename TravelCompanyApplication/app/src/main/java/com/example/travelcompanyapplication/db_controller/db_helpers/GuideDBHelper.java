package com.example.travelcompanyapplication.db_controller.db_helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelcompanyapplication.db_controller.db_contracts.GuideReaderContract;
import com.example.travelcompanyapplication.db_controller.db_fillers.GuideDBFiller;

import java.util.ArrayList;

public class GuideDBHelper extends SQLiteOpenHelper {
    private GuideDBFiller GuideDBFiller;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + GuideReaderContract.GuideEntry.TABLE_NAME + " (" +
            GuideReaderContract.GuideEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            GuideReaderContract.GuideEntry.CITY_TITLE + TEXT_TYPE + COMMA_SEP +
            GuideReaderContract.GuideEntry.DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            GuideReaderContract.GuideEntry.LINK + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + GuideReaderContract.GuideEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Guides.db";

    public GuideDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (!(dataBaseExists(context))) {
            GuideDBFiller = new GuideDBFiller(this);
            GuideDBFiller.fillDB();
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
                GuideReaderContract.GuideEntry._ID,
                GuideReaderContract.GuideEntry.CITY_TITLE,
                GuideReaderContract.GuideEntry.DESCRIPTION,
                GuideReaderContract.GuideEntry.LINK
        };
        String sortOrder = GuideReaderContract.GuideEntry.CITY_TITLE + " ASC";
        Cursor cursor = database.query(
                GuideReaderContract.GuideEntry.TABLE_NAME,
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
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(GuideReaderContract.GuideEntry._ID)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(GuideReaderContract.GuideEntry.CITY_TITLE)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(GuideReaderContract.GuideEntry.DESCRIPTION)));
            row.add(cursor.getString(cursor.getColumnIndexOrThrow(GuideReaderContract.GuideEntry.LINK)));
            table.add(row);
            cursor.moveToNext();
        }
        return table;
    }
}
