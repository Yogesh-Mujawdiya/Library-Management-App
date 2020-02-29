package library.management.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import library.management.Class.Book;
import library.management.R;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.ProjectViewHolder> implements Filterable {

    private List<Book> BookList;
    private List<Book> FilteredList;
    Context context;

    public SearchBookAdapter(Context context, List<Book> BookList) {
        this.context = context ;
        this.BookList=BookList;
        this.FilteredList = new ArrayList<>(this.BookList);
    }

    @Override
    public SearchBookAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_book, null);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchBookAdapter.ProjectViewHolder holder, int position) {
        Book product = FilteredList.get(position);
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewAccessNo.setText(String.valueOf(product.getAccess_No()));
        holder.textViewCallNo.setText(String.valueOf(product.getCall_No()));
        holder.textViewAvailability.setText(String.valueOf(product.getAvailability()));
        holder.textViewAuthor.setText(String.valueOf(product.getAuthor()));
    }


    @Override
    public int getItemCount() {
        return FilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Book> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(BookList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Book item : BookList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern) || item.getAccess_No().toLowerCase().contains(filterPattern) || item.getAuthor().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            FilteredList.clear();
            FilteredList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewAccessNo, textViewCallNo, textViewAvailability, textViewAuthor;

        public ProjectViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.tvTitle);
            textViewAccessNo = itemView.findViewById(R.id.tvAccessNo);
            textViewCallNo = itemView.findViewById(R.id.tvCallNo);
            textViewAvailability = itemView.findViewById(R.id.tvAvailability);
            textViewAuthor = itemView.findViewById(R.id.tvAuthor);
        }
    }

}
