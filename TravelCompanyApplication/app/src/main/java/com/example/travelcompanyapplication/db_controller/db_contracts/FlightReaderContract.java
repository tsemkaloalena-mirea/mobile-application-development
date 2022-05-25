package com.example.travelcompanyapplication.db_controller.db_contracts;

import android.provider.BaseColumns;

public class FlightReaderContract {
    public static abstract class FlightEntry implements BaseColumns {
        public static final String TABLE_NAME = "flights";
        public static final String DEPARTURE_CITY = "departure_city";
        public static final String ARRIVAL_CITY = "arrival_city";
        public static final String DEPARTURE_DATE = "departure_date";
        public static final String ARRIVAL_DATE = "arrival_date";
        public static final String AIRLINE = "airline";
        public static final String COST = "cost";
    }
}
