package coljamkop.momoney;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import coljamkop.momoney.Content.BudgetContent;
import coljamkop.momoney.Content.Category;
import coljamkop.momoney.Content.DBOperations;
import coljamkop.momoney.Content.Expense;

public class MainActivity extends AppCompatActivity implements CategoriesFragment.OnListFragmentInteractionListener,
        ExpenseFragment.OnListFragmentInteractionListener {


    Context context = this;
    SimpleDateFormat format = new SimpleDateFormat("mm yyyy");
    DBOperations dbo = new DBOperations(context);

    /*
     * ExpenseView
     */
    @Override
    public void onListFragmentInteraction(Expense expense) {

    }

    @Override
    public void onDeleteExpenseButtonInteraction(Expense expense) {
        BudgetContent.getThisMonth().getCategory(expense.getCategoryName()).deleteExpense(expense);
        ((RecyclerView)findViewById(R.id.expense_list)).getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onExpenseAmountViewInteraction(final Expense expense) {
        final RecyclerView expenseRecyclerView = (RecyclerView) findViewById(R.id.expense_list);
        String title;
        final EditText input = new EditText(this);
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setText(expense.getTotal().toString());
            title = "Edit expense amount:";
            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setIcon(android.R.drawable.ic_menu_edit)
                    .setView(input)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String text = input.getText().toString();
                            expense.setTotal(new BigDecimal(text.trim()));
                            // TODO Update database
                            expenseRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
    }

    @Override
    public void onExpenseDateViewInteraction(Expense expense) {
        final RecyclerView recyclerView = ((RecyclerView)findViewById(R.id.expense_list));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = (getSupportFragmentManager().findFragmentByTag("dialog"));
        if (prev != null) {
            ft.remove(prev);
        }
//        final DBHelper db = new DBHelper(getApplicationContext());
//        final FamilyContent.Appointment appointment = new FamilyContent.Appointment(0,0,0,0,0, Integer.parseInt(family.getID()));
        final Calendar c = expense.getDate();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

//        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                if (view.isShown()) {
//                    appointment.setYear(year);
//                    appointment.setMonth(1 + monthOfYear);
//                    appointment.setDay(dayOfMonth);
//                    Log.d("onDateChanged", "date changed");
//                    recyclerView.getAdapter().notifyDataSetChanged();
//                    db.putAppointment(appointment);
//                }
//            }
//        };
//        DatePickerDialog datePickerFragment = new DatePickerDialog(this, onDateSetListener, year, month, day);
//        datePickerFragment.show();
//        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                if (view.isShown()) {
//                    appointment.setHour(hourOfDay);
//                    appointment.setMinute(minute);
//                    Log.d("onTimeChanged", "time changed");
//                    recyclerView.getAdapter().notifyDataSetChanged();
//                    family.addAppointment(appointment);
//                    db.updateAppointment(appointment);
//                }
//            }
//        };
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hourOfDay, minute, false);
//        timePickerDialog.show();

    }

    /*
     * CategoryView
     */
    @Override
    public void onListFragmentInteraction(Category category) {

    }

    @Override
    public void onAddExpenseButtonInteraction(final Category category) {
        final RecyclerView categoryRecycleView = (RecyclerView) findViewById(R.id.category_list);
        String title;
        final EditText input = new EditText(this);
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        title = "Add an expense:";
        new AlertDialog.Builder(this)
            .setTitle(title)
            .setView(input)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String text = input.getText().toString();
                    Expense expense = new Expense(BigDecimal.valueOf(Double.parseDouble(text.trim())), category.getCategoryName());
                    category.addExpense(expense);
                    // TODO update database
//                    dbo.setExpense(dbo, expense);
                    categoryRecycleView.getAdapter().notifyDataSetChanged();
                    updateToolbarTotal(category.getTotal());
                }
            })
            .setNegativeButton(android.R.string.no, null)
            .show();
    }

    private void updateToolbarTotal(BigDecimal total) {
        ActionBar toolbar = getSupportActionBar();
        assert toolbar != null;
        toolbar.setTitle("Month Total: $" + BudgetContent.getThisMonth().getTotal().setScale(2, RoundingMode.CEILING));
    }

    @Override
    public void onListExpenseButtonInteraction(Category category) {
        if (category.getTotal().compareTo(BigDecimal.ZERO) != 0) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_container, ExpenseFragment.newInstance(1, category));
            ft.addToBackStack(null);
            ft.commit();
        }
        else {
            Toast.makeText(this, "No Expenses to Show", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteCategoryButtonInteraction(Category category) {
        BudgetContent.getThisMonth().deleteCategory(category);
        // TODO Remove from database
        ((RecyclerView)findViewById(R.id.category_list)).getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCategoryNameInteraction(final Category category) {
        final RecyclerView categoryRecycleView = (RecyclerView) findViewById(R.id.category_list);
        String title;
        final View view = View.inflate(this, R.layout.add_category_dialog, null);
        final EditText categoryName = (EditText) view.findViewById(R.id.add_category_category_name);
        final EditText categoryGoal = (EditText) view.findViewById(R.id.add_category_category_goal);
        String oldName = category.getCategoryName();
        categoryName.setText(category.getCategoryName());
        categoryGoal.setText(category.getGoal().setScale(2, RoundingMode.CEILING).toString());
        title = "Edit this category:";
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        boolean added = BudgetContent.getThisMonth().categoryExists(categoryName.getText().toString());
                        if (!added) {
                            if (!categoryGoal.getText().toString().equals(""))
                                category.setCategoryName(categoryName.getText().toString());
                                category.setGoal(new BigDecimal(categoryGoal.getText().toString()));
                            } else {
                                category.setCategoryName(categoryName.getText().toString());
                                category.setGoal(BigDecimal.ZERO);
                            }

                        if (added) {
                        // TODO Update database    dbo.updateCategory(dbo, oldName, category);
                        }
                        categoryRecycleView.getAdapter().notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    /*
     * GeneralView
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DBOperations dbo = new DBOperations(context);

        if (dbo.checkDatabase(context)) {
            Log.i("onCreate", "reads database");
            if (BudgetContent.MONTH_DEQUE.isEmpty() || true) {
                BudgetContent.addMonth(dbo.getCurrentMonth(dbo, String.valueOf(Calendar.YEAR), String.valueOf(Calendar.MONTH)));
            }

            if (getSupportFragmentManager().getFragments() == null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.content_container, CategoriesFragment.newInstance(1));
                ft.commit();
            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateToolbarTotal(BudgetContent.getThisMonth().getTotal());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.set_monthly_goal) {
//            String title;
//            final EditText input = new EditText(this);
//            input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//            title = "Set Monthly Goal:";
//            new AlertDialog.Builder(this)
//                    .setTitle(title)
//                    .setView(input)
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            String text = input.getText().toString();
//                            BudgetContent.getThisMonth().setGoal(BigDecimal.valueOf(Double.parseDouble(text)));
//                            // TODO update database
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, null)
//                    .show();        }
        if (id == R.id.add_category) {
            final RecyclerView categoryRecycleView = (RecyclerView) findViewById(R.id.category_list);
            String title;
            final View view = View.inflate(this, R.layout.add_category_dialog, null);
            title = "Add a category:";
            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setView(view)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            EditText categoryName = (EditText) view.findViewById(R.id.add_category_category_name);
                            EditText categoryGoal = (EditText) view.findViewById(R.id.add_category_category_goal);
                            boolean added = true;
                            Category newCategory = null;
                            if (!categoryGoal.getText().toString().equals("")) {
                                newCategory = new Category(null,
                                        categoryName.getText().toString(),
                                        new BigDecimal(categoryGoal.getText().toString()));
                                added = BudgetContent.getThisMonth().addCategory(newCategory);
                            }
                            else {
                                newCategory = new Category(null,
                                        categoryName.getText().toString(),
                                        BigDecimal.ZERO);
                                added = BudgetContent.getThisMonth().addCategory(newCategory);
                            }
                            categoryRecycleView.getAdapter().notifyDataSetChanged();

                            // TODO update database
                            //DBOperations dbo = new DBOperations(context);
                            if (added == true){
                                dbo.putCategory(dbo, newCategory);
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

}
