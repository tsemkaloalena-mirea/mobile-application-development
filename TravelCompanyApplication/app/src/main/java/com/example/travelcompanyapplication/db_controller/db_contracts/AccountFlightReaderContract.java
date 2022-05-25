package com.example.travelcompanyapplication.db_controller.db_contracts;

import android.provider.BaseColumns;

public class AccountFlightReaderContract {
    public static abstract class FlightEntry implements BaseColumns {
        public static final String TABLE_NAME = "account_flight";
        public static final String FLIGHT_ID = "flight_id";
        public static final String TICKETS_NUMBER = "tickets_number";
    }
}
