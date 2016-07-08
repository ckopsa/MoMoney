package coljamkop.momoney;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.math.BigDecimal;

import coljamkop.momoney.Content.Budget;
import coljamkop.momoney.Content.BudgetContent;
import coljamkop.momoney.Content.Category;

public class MainActivity extends AppCompatActivity implements CategoriesFragment.OnListFragmentInteractionListener {

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

        return super.onOptionsItemSelected(item);
    }

}
