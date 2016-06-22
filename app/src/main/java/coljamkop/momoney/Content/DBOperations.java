package coljamkop.momoney.Content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.PublicKey;

/**
 * Created by emi_lion91 on 6/22/2016.
 */
public class DBOperations extends SQLiteOpenHelper {

    public static final int database_version = 1;

    public String CURRENT_QUERY = "CREATE TABLE "
            + DBData.CurrentInfo.TABLE_NAME
            + " ("
            + DBData.CurrentInfo.CATEGORY + " TEXT, "
            + DBData.CurrentInfo.TOTAL + " NUM, "
            + DBData.CurrentInfo.DATE + " TEXT);";

    public String MONTH_QUERY = "CREATE TABLE "
            + DBData.MonthInfo.TABLE_NAME
            + " ("
            + DBData.MonthInfo.YEAR + " NUM, "
            + DBData.MonthInfo.MONTH + " TEXT, "
            + DBData.MonthInfo.TOTAL + " NUM, "
            + DBData.MonthInfo.GOAL + " NUM, "
            + DBData.MonthInfo.METGOAL + " BOOLEAN);";

    public DBOperations(Context context) {
        super(context, DBData.DATABASE_NAME, null, database_version);
        Log.i("Database operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CURRENT_QUERY);
        db.execSQL(MONTH_QUERY);
        Log.i("Database operations", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInfoCurrent(DBOperations dop, String cat, double total, String date){
        SQLiteDatabase db = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBData.CurrentInfo.CATEGORY, cat);
        cv.put(DBData.CurrentInfo.TOTAL, total);
        cv.put(DBData.CurrentInfo.DATE, date);
        db.insert(DBData.CurrentInfo.TABLE_NAME, null, cv);
        Log.i("Database operations", "Expense saved");
    }

    public Cursor getInfoCurrent(DBOperations dop){
        SQLiteDatabase db = dop.getReadableDatabase();
        String[] columns = {DBData.CurrentInfo.CATEGORY, DBData.CurrentInfo.TOTAL, DBData.CurrentInfo.DATE};
        Cursor cr = db.query(DBData.CurrentInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }

    public void putInfoMonth(DBOperations dop, String year, String month, double total, String goal, Boolean metGoal){
        SQLiteDatabase db = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBData.MonthInfo.YEAR, year);
        cv.put(DBData.MonthInfo.MONTH, month);
        cv.put(DBData.MonthInfo.TOTAL, total);
        cv.put(DBData.MonthInfo.GOAL, goal);
        cv.put(DBData.MonthInfo.METGOAL, metGoal);
        db.insert(DBData.CurrentInfo.TABLE_NAME, null, cv);
        Log.i("Database operations", "Expense saved");
    }

    public Cursor getInfoMonth(DBOperations dop){
        SQLiteDatabase db = dop.getReadableDatabase();
        String[] columns = {DBData.MonthInfo.YEAR, DBData.MonthInfo.MONTH, DBData.MonthInfo.TOTAL, DBData.MonthInfo.GOAL, DBData.MonthInfo.METGOAL};
        Cursor cr = db.query(DBData.CurrentInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }

    public boolean checkDatabase(Context context){

        SQLiteDatabase db;
        try{
            db = SQLiteDatabase.openDatabase(String.valueOf(context.getDatabasePath(DBData.DATABASE_NAME)), null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e){
            Log.w("Database operations", "Database not found");
            return false;
        }
        return true;
    }
}
