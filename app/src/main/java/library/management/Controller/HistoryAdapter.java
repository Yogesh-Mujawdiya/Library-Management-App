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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ProjectViewHolder> {

    private List<Book> BookList;
    private List<Book> FilteredList;
    Context context;

    public HistoryAdapter(Context context, List<Book> BookList) {
        this.context = context ;
        this.BookList=BookList;
        this.FilteredList = new ArrayList<>(this.BookList);
    }

    @Override
    public HistoryAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_history, parent, false);
        return new HistoryAdapter.ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ProjectViewHolder holder, int position) {
        Book product = FilteredList.get(position);
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewAccessNo.setText(String.valueOf(product.getAccess_No()));
        holder.textViewIssueDate.setText(String.valueOf(product.getIssue_Date()));
        holder.textViewReturnedOn.setText(String.valueOf(product.getReturned_On()));
    }

    @Override
    public int getItemCount() {
        return FilteredList.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewAccessNo, textViewIssueDate, textViewReturnedOn;

        public ProjectViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.tvTitle);
            textViewAccessNo = itemView.findViewById(R.id.tvAccessNo);
            textViewIssueDate = itemView.findViewById(R.id.tvIssueDate);
            textViewReturnedOn = itemView.findViewById(R.id.tvReturnOn);
        }
    }

}
