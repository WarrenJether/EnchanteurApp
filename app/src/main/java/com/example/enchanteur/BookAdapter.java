package com.example.enchanteur;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private OnBookClickListener onBookClickListener;
    private List<Integer> selectedPositions;

    public BookAdapter(List<Book> bookList, OnBookClickListener onBookClickListener) {
        this.bookList = bookList;
        this.onBookClickListener = onBookClickListener;
        this.selectedPositions = new ArrayList<>();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView, onBookClickListener);
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

    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView bookNumberTextView;
        private TextView titleTextView;
        private TextView authorTextView;
        private TextView categoryTextView;
        private CheckBox checkboxBook;
        private OnBookClickListener onBookClickListener;

        public BookViewHolder(View itemView, OnBookClickListener onBookClickListener) {
            super(itemView);
            bookNumberTextView = itemView.findViewById(R.id.tvBookNumber);
            titleTextView = itemView.findViewById(R.id.tvTitle);
            authorTextView = itemView.findViewById(R.id.tvAuthor);
            categoryTextView = itemView.findViewById(R.id.tvCategory);
            checkboxBook = itemView.findViewById(R.id.checkboxBook);


            this.onBookClickListener = onBookClickListener;

            itemView.setOnClickListener(this);
            checkboxBook.setOnClickListener(this);
        }

        // Bind book data to the ViewHolder
        void bind(Book book) {
            bookNumberTextView.setText(String.valueOf(book.getBookNumber()));
            titleTextView.setText(book.getTitle());
            authorTextView.setText(book.getAuthor());
            categoryTextView.setText(book.getCategory());

            // Set the checked state of the checkbox based on the isSelected field of the Book object
            checkboxBook.setChecked(book.isSelected());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (v.getId() == R.id.checkboxBook) {
                    // Handle checkbox click event
                    boolean isSelected = checkboxBook.isChecked();
                    Book book = bookList.get(position);
                    book.setSelected(isSelected);
                    onBookClickListener.onBookCheckboxClick(position, isSelected);
                } else {
                    // Handle item click event
                    onBookClickListener.onBookClick(position);
                }
            }
        }
    }

    public interface OnBookClickListener {
        void onBookClick(int position);
        void onBookCheckboxClick(int position, boolean selected);
    }
}