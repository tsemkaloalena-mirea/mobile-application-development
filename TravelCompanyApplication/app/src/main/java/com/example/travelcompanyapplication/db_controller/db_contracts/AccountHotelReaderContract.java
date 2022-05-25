package com.example.travelcompanyapplication.db_controller.db_contracts;

import android.provider.BaseColumns;

public class AccountHotelReaderContract {
    public static abstract class HotelEntry implements BaseColumns {
        public static final String TABLE_NAME = "account_hotels";
        public static final String DEPARTURE_DATE = "departure_date";
        public static final String ARRIVAL_DATE = "arrival_date";
        public static final String HOTEL_ID = "hotel_id";
        public static final String ADULTS_NUMBER = "adults_number";
        public static final String KIDS_NUMBER = "kids_number";
    }
}
