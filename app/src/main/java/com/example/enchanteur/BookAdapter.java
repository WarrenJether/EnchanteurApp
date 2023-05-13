package com.example.enchanteur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
    // Constructor, methods to update the bookList and bind data to the ViewHolder

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView authorTextView;
        private TextView categoryTextView;

        public BookViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvTitle);
            authorTextView = itemView.findViewById(R.id.tvAuthor);
            categoryTextView = itemView.findViewById(R.id.tvCategory);
        }

        // Constructor to initialize the ViewHolder

        // Bind book data to the ViewHolder
        void bind(Book book) {
            titleTextView.setText(book.getTitle());
            authorTextView.setText(book.getAuthor());
            categoryTextView.setText(book.getCategory());
        }
    }
}
