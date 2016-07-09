package coljamkop.momoney.RecylerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import coljamkop.momoney.Content.Expense;
import coljamkop.momoney.ExpenseFragment.OnListFragmentInteractionListener;
import coljamkop.momoney.R;

import java.math.RoundingMode;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link coljamkop.momoney.Content.Expense} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyExpenseRecyclerViewAdapter extends RecyclerView.Adapter<MyExpenseRecyclerViewAdapter.ViewHolder> {

    private final List<Expense> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyExpenseRecyclerViewAdapter(List<Expense> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mExpense = mValues.get(position);
        holder.mExpenseDateView.setText(mValues.get(position).getDate());
        holder.mExpenseAmountView.setText("$" + String.valueOf(holder.mExpense.getTotal().setScale(2, RoundingMode.CEILING)));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mExpense);
                }
            }
        });

        holder.mDeleteExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDeleteExpenseButtonInteraction(holder.mExpense);
            }
        });
        holder.mExpenseAmountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onExpenseAmountViewInteraction(holder.mExpense);
            }
        });
        holder.mExpenseDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onExpenseDateViewInteraction(holder.mExpense);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mExpenseDateView;
        public final TextView mExpenseAmountView;
        public final ImageButton mDeleteExpenseButton;
        public Expense mExpense;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mExpenseDateView = (TextView) view.findViewById(R.id.expense_date);
            mExpenseAmountView = (TextView) view.findViewById(R.id.expense_amount);
            mDeleteExpenseButton = (ImageButton) view.findViewById(R.id.delete_expense_button);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mExpenseAmountView.getText() + "'";
        }
    }
}
