package coljamkop.momoney.RecylerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import coljamkop.momoney.CategoriesFragment.OnListFragmentInteractionListener;
import coljamkop.momoney.Content.Budget;
import coljamkop.momoney.Content.Category;
import coljamkop.momoney.R;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Budget} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<MyCategoriesRecyclerViewAdapter.ViewHolder> {

    private final List<Category> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyCategoriesRecyclerViewAdapter(List<Category> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mCategory = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getCategoryName());
        holder.mCategoryAmount.setText(holder.mCategory.getDollarTotal());
        holder.mAddExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddExpenseButtonInteraction(holder.mCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView mCategoryAmount;
        public final ImageButton mAddExpenseButton;
        public final ImageButton mListExpenseButton;
        public Category mCategory;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.category_name_text);
            mAddExpenseButton = (ImageButton) view.findViewById(R.id.add_expense_button);
            mListExpenseButton = (ImageButton) view.findViewById(R.id.list_expense_button);
            mCategoryAmount = (TextView) view.findViewById(R.id.category_total_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
