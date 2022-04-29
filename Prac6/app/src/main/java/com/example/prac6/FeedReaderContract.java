package com.example.task6;

import android.provider.BaseColumns;

public class FeedReaderContract {
    public FeedReaderContract() {}

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "resume_records";
        public static final String NUMBER_OF_RESUME = "number";
        public static final String DATE_OF_RESUME = "date";
    }
}
