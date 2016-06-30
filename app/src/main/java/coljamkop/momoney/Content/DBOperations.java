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
 * @author Cesar de Leon
 * @version 1
 */
public class DBOperations extends SQLiteOpenHelper {
    /**
     * <p>Database version</p>
     */
    public static final int database_version = 1;

    /**
     * <p>Command that creates the Current Month table int the database</p>
     */
    public String CURRENT_QUERY = "CREATE TABLE "
            + DBData.CurrentInfo.TABLE_NAME
            + " ("
            + DBData.CurrentInfo.CATEGORY + " TEXT, "
            + DBData.CurrentInfo.TOTAL + " NUM, "
            + DBData.CurrentInfo.DATE + " TEXT);";

    /**
     * <p>Command that creates the Months table in the database</p>
     */
    public String MONTH_QUERY = "CREATE TABLE "
            + DBData.MonthInfo.TABLE_NAME
            + " ("
            + DBData.MonthInfo.YEAR + " NUM, "
            + DBData.MonthInfo.MONTH + " TEXT, "
            + DBData.MonthInfo.TOTAL + " NUM, "
            + DBData.MonthInfo.GOAL + " NUM, "
            + DBData.MonthInfo.METGOAL + " BOOLEAN);";

    /**
     * <p>DBOperations class is created and space for the database is allocated</p>
     * @param context context of the app
     */
    public DBOperations(Context context) {
        super(context, DBData.DATABASE_NAME, null, database_version);
        Log.i("Database operations", "Database created");
    }

    /**
     * <p>The tables for Current Month and Months are created.</p>
     * @param db SQLite Database Object
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CURRENT_QUERY);
        db.execSQL(MONTH_QUERY);
        Log.i("Database operations", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * The Category of the expense, the total expense and the date are stored
     * @param dop DBOperations obj
     * @param cat Name of the category
     * @param total Total of the expense
     * @param date The date of the expense
     */
    public void putInfoCurrent(DBOperations dop, String cat, double total, String date){
        SQLiteDatabase db = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBData.CurrentInfo.CATEGORY, cat);
        cv.put(DBData.CurrentInfo.TOTAL, total);
        cv.put(DBData.CurrentInfo.DATE, date);
        db.insert(DBData.CurrentInfo.TABLE_NAME, null, cv);
        Log.i("Database operations", "Expense saved");
    }

    /**
     * Returns a Cursor of all the columns
     * @param dop DBOperations object
     * @return Cursor
     */
    public Cursor getInfoCurrent(DBOperations dop){
        SQLiteDatabase db = dop.getReadableDatabase();
        String[] columns = {DBData.CurrentInfo.CATEGORY, DBData.CurrentInfo.TOTAL, DBData.CurrentInfo.DATE};
        Cursor cr = db.query(DBData.CurrentInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }

    /**
     * The year, the month, the total of expenses, the goal for that month, and if they met the goal are saved to the table Months
     * @param dop DBOperations object
     * @param year Year of the month
     * @param month Month in that year
     * @param total Total of expendures in that month
     * @param goal Goal for that Month
     * @param metGoal If the goal was met or not
     */
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

    /**
     * <p>Returns a Cursor of all the columns in the Months table.</p>
     * @param dop DBOperations object
     * @return cr
     */
    public Cursor getInfoMonth(DBOperations dop){
        SQLiteDatabase db = dop.getReadableDatabase();
        String[] columns = {DBData.MonthInfo.YEAR, DBData.MonthInfo.MONTH, DBData.MonthInfo.TOTAL, DBData.MonthInfo.GOAL, DBData.MonthInfo.METGOAL};
        Cursor cr = db.query(DBData.CurrentInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }

    /**
     * Check if a database was already created
     * @param context conext of the app
     * @return boolean true for database is there, false if it isn't.
     */
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
