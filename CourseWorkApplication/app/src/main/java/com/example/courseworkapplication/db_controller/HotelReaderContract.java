package com.example.courseworkapplication.db_controller;

import android.provider.BaseColumns;

public class HotelReaderContract {
    public static abstract class HotelEntry implements BaseColumns {
        public static final String TABLE_NAME = "hotels";
        public static final String CITY = "city";
        public static final String HOTEL_NAME = "hotel_name";
        public static final String MAP_LINK = "map_link";
        public static final String COST = "cost";
        public static final String RATING = "rating";
    }
}
