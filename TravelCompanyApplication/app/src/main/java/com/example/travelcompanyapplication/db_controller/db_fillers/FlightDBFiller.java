package com.example.travelcompanyapplication.db_controller.db_fillers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.travelcompanyapplication.db_controller.db_contracts.FlightReaderContract;
import com.example.travelcompanyapplication.db_controller.db_helpers.FlightDBHelper;

public class FlightDBFiller {
    private FlightDBHelper mFlightDBHelper;
    private String[][] flightsData = {
            {"Moscow, Russia", "Paris, France", "23.08.2022 02:08", "23.08.2022 05:08", "Transavia", "4 555"},
            {"Moscow, Russia", "Paris, France", "23.08.2022 10:08", "23.08.2022 13:28", "Air France", "3 559"},
            {"Moscow, Russia", "Paris, France", "15.08.2022 15:37", "15.08.2022 18:48", "S7", "2 558"},
            {"Moscow, Russia", "Paris, France", "15.04.2022 15:37", "15.04.2022 18:48", "S7", "4 198"},
            {"Paris, France", "Moscow, Russia", "23.09.2022 02:08", "23.09.2022 05:08", "Transavia", "4 555"},
            {"Paris, France", "Moscow, Russia", "23.09.2022 10:08", "23.09.2022 13:28", "Air France", "3 559"},
            {"Paris, France", "Moscow, Russia", "15.09.2022 15:37", "15.09.2022 18:48", "S7", "2 558"},
            {"Paris, France", "Moscow, Russia", "15.05.2022 15:37", "15.05.2022 18:48", "S7", "4 198"},
            {"Saint Petersburg, Russia", "Paris, France", "24.09.2022 02:08", "24.09.2022 05:08", "Transavia", "5 169"},
            {"Saint Petersburg, Russia", "Paris, France", "24.08.2022 10:08", "24.08.2022 13:28", "Air France", "3 559"},
            {"Saint Petersburg, Russia", "Paris, France", "17.08.2022 15:37", "17.08.2022 18:48", "S7", "5 100"},
            {"Paris, France", "Saint Petersburg, Russia", "24.10.2022 02:08", "24.10.2022 05:08", "Transavia", "5 169"},
            {"Paris, France", "Saint Petersburg, Russia", "24.09.2022 10:08", "24.09.2022 13:28", "Air France", "3 559"},
            {"Paris, France", "Saint Petersburg, Russia", "17.09.2022 15:37", "17.09.2022 18:48", "S7", "5 100"},
            {"Moscow, Russia", "Rome, Italy", "18.07.2022 12:54", "18.07.2022 16:04", "Air Europa", "4 555"},
            {"Moscow, Russia", "Rome, Italy", "18.07.2022 17:54", "18.07.2022 20:07", "KLM", "6 415"},
            {"Moscow, Russia", "Rome, Italy", "15.08.2022 15:37", "15.08.2022 18:48", "S7", "4 756"},
            {"Rome, Italy", "Moscow, Russia", "18.08.2022 12:54", "18.08.2022 16:04", "Air Europa", "4 555"},
            {"Rome, Italy", "Moscow, Russia", "18.08.2022 17:54", "18.08.2022 20:07", "KLM", "6 415"},
            {"Rome, Italy", "Moscow, Russia", "15.09.2022 15:37", "15.09.2022 18:48", "S7", "4 756"},
            {"Saint Petersburg, Russia", "Rome, Italy", "24.09.2022 02:08", "24.09.2022 05:08", "KLM", "3 596"},
            {"Saint Petersburg, Russia", "Rome, Italy", "24.08.2022 10:08", "24.08.2022 13:28", "British Airlines", "6 127"},
            {"Saint Petersburg, Russia", "Rome, Italy", "17.08.2022 15:37", "17.08.2022 18:48", "S7", "3 649"},
            {"Rome, Italy", "Saint Petersburg, Russia", "24.09.2022 02:08", "24.09.2022 05:08", "KLM", "3 596"},
            {"Rome, Italy", "Saint Petersburg, Russia", "24.08.2022 10:08", "24.08.2022 13:28", "British Airlines", "6 127"},
            {"Rome, Italy", "Saint Petersburg, Russia", "17.08.2022 15:37", "17.08.2022 18:48", "S7", "3 649"},
            {"Moscow, Russia", "Tomsk, Russia", "23.08.2022 21:15", "24.08.2022 10:55", "Pobeda", "12 751"},
            {"Moscow, Russia", "Tomsk, Russia", "23.08.2022 23:25", "24.08.2022 07:30", "S7", "13 478"},
            {"Moscow, Russia", "Tomsk, Russia", "15.08.2022 23:55", "14.08.2022 10:55", "S7", "14 350"},
            {"Tomsk, Russia", "Moscow, Russia", "30.08.2022 17:10", "31.08.2022 10:55", "Pobeda", "13 916"},
            {"Tomsk, Russia", "Moscow, Russia", "30.08.2022 23:25", "31.08.2022 07:28", "S7", "13 559"},
            {"Tomsk, Russia", "Moscow, Russia", "29.08.2022 23:37", "31.08.2022 10:48", "S7", "12 558"}
    };

    public FlightDBFiller(FlightDBHelper flightDBHelper) {
        mFlightDBHelper = flightDBHelper;
    }

    private void addRow(String[] data) {
        SQLiteDatabase database = mFlightDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FlightReaderContract.FlightEntry.DEPARTURE_CITY, data[0]);
        values.put(FlightReaderContract.FlightEntry.ARRIVAL_CITY, data[1]);
        values.put(FlightReaderContract.FlightEntry.DEPARTURE_DATE, data[2]);
        values.put(FlightReaderContract.FlightEntry.ARRIVAL_DATE, data[3]);
        values.put(FlightReaderContract.FlightEntry.AIRLINE, data[4]);
        values.put(FlightReaderContract.FlightEntry.COST, data[5]);

        long newRowId = database.insert(
                FlightReaderContract.FlightEntry.TABLE_NAME,
                null,
                values);
    }

    public void fillDB() {
        for (int i = 0; i < flightsData.length; i++) {
            addRow(flightsData[i]);
        }
    }
}