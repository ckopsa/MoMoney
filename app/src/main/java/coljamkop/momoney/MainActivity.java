package coljamkop.momoney;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.math.BigDecimal;
import java.util.Calendar;

import coljamkop.momoney.Content.BudgetContent;
import coljamkop.momoney.Content.Category;
import coljamkop.momoney.Content.Expense;

public class MainActivity extends AppCompatActivity implements CategoriesFragment.OnListFragmentInteractionListener,
        ExpenseFragment.OnListFragmentInteractionListener {

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
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
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
                            // TODO Update databse
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
        final Calendar c = Calendar.getInstance();
        c.setTime(expense.getDate());
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
        final RecyclerView categoryRecycleView = (RecyclerView) findViewById(R.id.list);
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
                    category.addExpense(BigDecimal.valueOf(Double.parseDouble(text.trim())));
                    // TODO update database
                    categoryRecycleView.getAdapter().notifyDataSetChanged();
                }
            })
            .setNegativeButton(android.R.string.no, null)
            .show();
    }

    @Override
    public void onListExpenseButtonInteraction(Category category) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_container, ExpenseFragment.newInstance(1, category));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onDeleteCategoryButtonInteraction(Category category) {
        BudgetContent.getThisMonth().deleteCategory(category);
        // TODO Remove from database
        ((RecyclerView)findViewById(R.id.list)).getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCategoryNameInteraction(Category category) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportFragmentManager().getFragments() == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_container, CategoriesFragment.newInstance(1));
            ft.commit();
        }
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
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.add_category) {
            final RecyclerView categoryRecycleView = (RecyclerView) findViewById(R.id.list);
            String title;
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            title = "Add a category:";
            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setView(input)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String text = input.getText().toString();
                            BudgetContent.getThisMonth().addCategory(new Category(null, text, new BigDecimal(0)));
                            // TODO update database
                            categoryRecycleView.getAdapter().notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

}
