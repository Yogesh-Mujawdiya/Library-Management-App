package library.management.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import library.management.Class.Book;
import library.management.R;

public class NewBookAdapter extends RecyclerView.Adapter<NewBookAdapter.ProjectViewHolder> {

    private List<Book> BookList;
    private List<Book> FilteredList;
    Context context;

    public NewBookAdapter(Context context, List<Book> BookList) {
        this.context = context ;
        this.BookList=BookList;
        this.FilteredList = new ArrayList<>(this.BookList);
    }

    @Override
    public NewBookAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_new_book, parent, false);
        return new NewBookAdapter.ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewBookAdapter.ProjectViewHolder holder, int position) {
        Book product = FilteredList.get(position);
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewAccessNo.setText(String.valueOf(product.getAccess_No()));
        holder.textViewAvailability.setText(String.valueOf(product.getAvailability()));
        holder.textViewAuthor.setText(String.valueOf(product.getAuthor()));
        holder.textViewEdition.setText(String.valueOf(product.getEdition()));
        holder.textViewPublication.setText(String.valueOf(product.getPublication()));
    }

    @Override
    public int getItemCount() {
        return FilteredList.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewAccessNo, textViewAvailability, textViewAuthor, textViewPublication, textViewEdition;

        public ProjectViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.tvTitle);
            textViewAccessNo = itemView.findViewById(R.id.tvAccessNo);
            textViewAuthor = itemView.findViewById(R.id.tvAuthor);
            textViewAvailability = itemView.findViewById(R.id.tvAvailability);
            textViewPublication = itemView.findViewById(R.id.tvPublication);
            textViewEdition = itemView.findViewById(R.id.tvEdition);
        }
    }

}
