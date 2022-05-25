package com.example.travelcompanyapplication.db_controller.db_contracts;

import android.provider.BaseColumns;

public class GuideReaderContract {
    public static abstract class GuideEntry implements BaseColumns {
        public static final String TABLE_NAME = "city_description";
        public static final String CITY_TITLE = "city_title";
        public static final String DESCRIPTION = "description";
        public static final String LINK = "link";
    }
}
