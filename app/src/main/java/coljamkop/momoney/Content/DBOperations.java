package coljamkop.momoney.Content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * <p>Command that creates the Expense table int the database</p>
     */
    public String EXPENSE_QUERY = "CREATE TABLE "
            + DBData.Expense.TABLE_NAME
            + " ("
            + DBData.Expense.CATEGORY + " TEXT, "
            + DBData.Expense.TOTAL + " NUM, "
            + DBData.Expense.DATE + " TEXT"
            + DBData.Expense.MILLISECONDS + "NUM);";

    /**
     * <p>Command that creates the Month table in the database</p>
     */
    public String MONTH_QUERY = "CREATE TABLE "
            + DBData.Month.TABLE_NAME
            + " ("
            + DBData.Month.YEAR + " NUM, "
            + DBData.Month.MONTH + " TEXT, "
            + DBData.Month.GOAL + " NUM);";

    /**
     * <p>Command that creates the Category table in the database</p>
     */
    public String CATEGORY_QUERY = "CREATE TABLE "
            + DBData.Category.TABLE_NAME
            + " ("
            + DBData.Category.CATEGORY_NAME + " TEXT, "
            + DBData.Category.GOAL + " NUM);";
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
        db.execSQL(EXPENSE_QUERY);
        db.execSQL(MONTH_QUERY);
        db.execSQL(CATEGORY_QUERY);
        Log.i("Database operations", "Tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * The Category of the expense, the total expense and the date are stored
     * @param dop DBOperations obj
     */
    public void setExpense(DBOperations dop, Expense expense){
        SQLiteDatabase db = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat format = new SimpleDateFormat("mm yyyy");
        String date = format.format(expense.getDate().getTime());
        cv.put(DBData.Expense.CATEGORY, expense.getCategoryName());
        cv.put(DBData.Expense.TOTAL, expense.getTotal().doubleValue());
        cv.put(DBData.Expense.DATE, date);
        cv.put(DBData.Expense.MILLISECONDS, expense.getDate().getTimeInMillis());
        db.insert(DBData.Expense.TABLE_NAME, null, cv);
        Log.i("Database operations", "Expense saved");
    }

    /**
     * Returns a list of Expenses based on the category and the date
     * @param dop DBOperations object
     * @return Cursor
     */
    public List<Expense> getExpense(DBOperations dop, String catName, String Date){

        BigDecimal total;
        String name;
        String date;
        long milliseconds = 0;
        List<Expense> expenseList = new ArrayList<Expense>();
        SQLiteDatabase db = dop.getReadableDatabase();
        String[] columns = {DBData.Expense._ID, DBData.Expense.CATEGORY, DBData.Expense.TOTAL, DBData.Expense.DATE, DBData.Expense.MILLISECONDS};
        Cursor cr = db.query(DBData.Expense.TABLE_NAME, columns, null, null, null, null, null);

        while (cr.moveToNext()){
            total = new BigDecimal(cr.getString(2));
            name = cr.getString(1);
            date = cr.getString(3);

            if (catName == name && date == Date) {
                milliseconds = Long.parseLong(cr.getString(4));
                Expense dummyExpense = new Expense(total, name, milliseconds);
                expenseList.add(dummyExpense);
            }
        }
        cr.close();
        return expenseList;
    }

    /**
     * The year, the month, the total of expenses, the goal for that month, and if they met the goal are saved to the table Months
     * @param dop DBOperations object
     * @param year Year of the month
     * @param month Month in that year
     * @param goal Goal for that Month
     */
    public void putMonth(DBOperations dop, String year, String month, String goal){
        SQLiteDatabase db = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBData.Month.YEAR, year);
        cv.put(DBData.Month.MONTH, month);
        cv.put(DBData.Month.GOAL, goal);
        db.insert(DBData.Month.TABLE_NAME, null, cv);
        Log.i("Database operations", "Expense saved");
    }

    /**
     * <p>Returns a Month Object</p>
     * @param dop DBOperations object
     * @return cr
     */
    public Month getCurrentMonth(DBOperations dop, String year, String month){
        /**
        BigDecimal goal;
        SQLiteDatabase db = dop.getReadableDatabase();
        String[] columns = {DBData.Month._ID, DBData.Month.YEAR, DBData.Month.MONTH, DBData.Month.GOAL};
        Cursor cr = db.query(DBData.Month.TABLE_NAME, columns, null, null, null, null, null);

        while (cr.moveToNext()){
            if (cr.getString(1) == year && cr.getString(2) == month){
                //Year = Integer.parseInt(cr.getString(1));
                //Month = Integer.parseInt(cr.getString(2));
                //goal = new BigDecimal(cr.getString(3));
                //dummyMonth = new Month(Year, Month, null);
                //dummyMonth.setGoal(goal);
            }
        }
        cr.close();
         */

        String currentDate = month + " " + year;
        List<Category> catList = dop.getCategory(dop);
        List<Expense> expenseList;

        Month dummyMonth = new Month(Integer.parseInt(year), Integer.parseInt(month), null);

        for (Category dummyCat : catList){
            expenseList = dop.getExpense(dop, dummyCat.getCategoryName(), currentDate);
            Category newCategory = new Category(expenseList, dummyCat.getCategoryName(), dummyCat.getGoal());
            dummyMonth.addCategory(newCategory);
            Log.i("getCurrentMonth", "month loaded");
        }

        return dummyMonth;
    }

    /**
     * <p>Adds a new category to the database</p>
     * @param dop database operations object
     * @param cate category object that you want to save
     */
    public void putCategory(DBOperations dop, Category cate){
        SQLiteDatabase db = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBData.Category.CATEGORY_NAME, cate.getCategoryName());
        cv.put(DBData.Category.GOAL, String.valueOf(cate.getGoal()));
        db.insert(DBData.Category.TABLE_NAME, null, cv);
        Log.i("Database operations", "Expense saved");
    }

    /**
     * <p>Returns a list of Categories and their goals.</p>
     * @param dop database operation object to get a readable database.
     * @return returns a cursor with all the table info.
     */
    public List<Category> getCategory(DBOperations dop){

        List<Category> categoryList = new ArrayList<Category>();
        SQLiteDatabase db = dop.getReadableDatabase();
        String[] columns = {DBData.Category._ID, DBData.Category.CATEGORY_NAME, DBData.Category.GOAL};
        Cursor cr = db.query(DBData.Category.TABLE_NAME, columns, null, null, null, null, null);

        while (cr.moveToNext()){
            BigDecimal goal = new BigDecimal(cr.getString(1));
            Category dummyCat = new Category(null, cr.getString(0), goal);
            categoryList.add(dummyCat);
        }
        cr.close();
        return categoryList;
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

    /**
     * <p>This updates the goal of a month</p>
     * @param dbo database operation object
     * @param year the year
     * @param month the month
     * @param newgoal the new goal for that month
     */
    public void UpdateGoalMonth(DBOperations dbo, String year, String month, String newgoal){
        SQLiteDatabase sq = dbo.getWritableDatabase();
        String selection = DBData.Month.YEAR + " LIKE ? AND " + DBData.Month.MONTH + " LIKE ?";
        String args[] = {year, month};
        ContentValues values = new ContentValues();

        values.put(DBData.Month.GOAL, newgoal);
        sq.update(DBData.Month.TABLE_NAME, values, selection, args);
    }

    /**
     * <p>This updates the goal of a category</p>
     * @param dbo database operation object
     * @param oldName old name of the category
     * @param newcat category object with new info
     */
    public void UpdateCategory(DBOperations dbo, String oldName, Category newcat){
        SQLiteDatabase sq = dbo.getWritableDatabase();
        String selection = DBData.Category.CATEGORY_NAME + " LIKE ?";
        String args[] = {oldName};
        ContentValues values = new ContentValues();

        values.put(DBData.Category.CATEGORY_NAME, newcat.getCategoryName());
        values.put(DBData.Category.GOAL, String.valueOf(newcat.getGoal()));
        sq.update(DBData.Category.TABLE_NAME, values, selection, args);

        SimpleDateFormat format = new SimpleDateFormat("mm yyyy");
        String date = format.format(new Date());

        UpdateExpenseCategory(dbo, oldName, newcat, date);
    }

    /**
     * <p>This updates the category for an expense</p>
     * @param dbo database operation object
     */
    public void UpdateExpenseCategory(DBOperations dbo, String oldname, Category newcat, String date){
        SQLiteDatabase sq = dbo.getWritableDatabase();
        String selection = DBData.Expense.CATEGORY + " LIKE ? AND " + DBData.Expense.DATE + " LIKE ?";
        String args[] = {oldname, date};
        ContentValues values = new ContentValues();

        values.put(DBData.Expense.CATEGORY, newcat.getCategoryName());
        sq.update(DBData.Expense.TABLE_NAME, values, selection, args);
    }

    /**
     * <p>This updates the date for an expense</p>
     * @param dbo database operations object
     * @param id the id of the expense we want to update
     * @param newDate the new date for the expense.
     */
    public void UpdateExpenseDate(DBOperations dbo, String id, String newDate){
        SQLiteDatabase sq = dbo.getWritableDatabase();
        String selection = DBData.Expense._ID + " LIKE ?";
        String args[] = {id};
        ContentValues values = new ContentValues();

        values.put(DBData.Expense.DATE, newDate);
        sq.update(DBData.Expense.TABLE_NAME, values, selection, args);
    }

}
