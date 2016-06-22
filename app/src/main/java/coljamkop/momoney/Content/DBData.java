package coljamkop.momoney.Content;

import android.provider.BaseColumns;

/**
 * Created by emi_lion91 on 6/22/2016.
 */
public class DBData {

    public static final String DATABASE_NAME = "database_momoney";
    public DBData(){

    }

    public static abstract class CurrentInfo implements BaseColumns{

        public static final String CATEGORY = "category";
        public static final String TOTAL = "total";
        public static final String DATE = "date";
        public static final String TABLE_NAME = "CurrentMonth";

    }

    public static abstract class MonthInfo implements BaseColumns{

        public static final String YEAR = "category";
        public static final String MONTH = "month";
        public static final String TOTAL = "total";
        public static final String GOAL = "goal";
        public static final String METGOAL = "met_goal";
        public static final String TABLE_NAME = "Months";


    }
}
