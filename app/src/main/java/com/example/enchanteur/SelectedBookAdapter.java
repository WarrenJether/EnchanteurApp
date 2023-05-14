package com.example.enchanteur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectedBookAdapter extends RecyclerView.Adapter<SelectedBookAdapter.SelectedBookViewHolder> {

    private List<Book> selectedBooks;

    public SelectedBookAdapter(List<Book> selectedBooks) {
        this.selectedBooks = selectedBooks;
    }

    @NonNull
    @Override
    public SelectedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_book, parent, false);
        return new SelectedBookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedBookViewHolder holder, int position) {
        Book book = selectedBooks.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return selectedBooks.size();
    }

    class SelectedBookViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvCategory;
        private TextView tvBookNumber;

        public SelectedBookViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvBookNumber = itemView.findViewById(R.id.tvBookNumber);
        }

        void bind(Book book) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvCategory.setText(book.getCategory());
            tvBookNumber.setText(String.valueOf(book.getBookNumber()));
        }
    }
}