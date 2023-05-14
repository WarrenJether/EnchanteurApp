package com.example.enchanteur;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements BookAdapter.OnBookClickListener {

    TextView tvUser;
    Button btnAdd, btnUpdate, btnDelete, btnIssue, btnViewBorrowedBooks, btnReturnBooks;
    RecyclerView recyclerView;
    private List<Book> bookList;
    private BookAdapter bookAdapter;
    private BookDataSource bookDataSource;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private List<Integer> selectedPositions = new ArrayList<>();
    private static final int ISSUE_BOOK_REQUEST_CODE = 1; // Define the request code for the issue book activity

    private String borrowData; // Variable to store the borrow date
    private String returnDate; // Variable to store the return date
    private int borrowCount; // Variable to store the count of items borrowed
    private static final String PREF_KEY_BOOKS_EXIST = "booksExist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnIssue = findViewById(R.id.btnIssue);
        btnViewBorrowedBooks = findViewById(R.id.btnViewBorrowedBooks);
        btnReturnBooks = findViewById(R.id.btnReturn);

        String username = getIntent().getStringExtra("username");

        tvUser = findViewById(R.id.tvUser);
        String welcomeMessage = getString(R.string.welcome_user, username);
        tvUser.setText(welcomeMessage);

        recyclerView = findViewById(R.id.myRecycleView);
        bookDataSource = BookDataSource.getInstance();
        bookList = bookDataSource.getBookList();
        bookAdapter = new BookAdapter(bookList, this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean booksExist = preferences.getBoolean(PREF_KEY_BOOKS_EXIST, false);

        if (!booksExist) {
            // Add the pre-existing data only if it doesn't already exist
            bookList.add(new Book(1, "Title 1", "Author 1", "Category 1"));
            bookList.add(new Book(2, "Title 2", "Author 2", "Category 2"));
            bookList.add(new Book(3, "Title 3", "Author 3", "Category 3"));

            // Save the flag indicating that the books exist
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(PREF_KEY_BOOKS_EXIST, true);
            editor.apply();

            // Notify the adapter about the changes in the bookList
            bookAdapter.notifyDataSetChanged();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);

        btnAdd.setOnClickListener(v -> launchAddBookActivity());

        btnUpdate.setOnClickListener(v -> {
            if (!selectedPositions.isEmpty()) {
                for (int position : selectedPositions) {
                    int bookNumber = bookList.get(position).getBookNumber();
                    launchUpdateBookActivity(bookNumber);
                }
            } else {
                Toast.makeText(this, "Please select a book to update", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (!selectedPositions.isEmpty()) {
                deleteSelectedBooks();
            } else {
                Toast.makeText(this, "Please select a book to delete", Toast.LENGTH_SHORT).show();
            }
        });

        btnIssue.setOnClickListener(v -> {
            if (!selectedPositions.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Issue Books");
                builder.setMessage("Do you want to issue the selected books?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        launchIssueBookActivity(selectedPositions);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
            } else {
                Toast.makeText(this, "Please select a book to issue", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewBorrowedBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBorrowedBooks();
            }
        });

        btnReturnBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (borrowData != null && returnDate != null && borrowCount > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                    builder.setTitle("Return Books");
                    builder.setMessage("Are you sure you want to return the borrowed books?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showReturnToast();
                            clearBorrowedBooks();
                        }
                    });
                    builder.setNegativeButton("No", null);
                    builder.create().show();
                } else {
                    Toast.makeText(Dashboard.this, "No borrowed books to return", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void launchAddBookActivity() {
        Intent intent = new Intent(Dashboard.this, AddBookActivity.class);
        startActivity(intent);
    }

    private void launchUpdateBookActivity(int bookNumber) {
        Intent intent = new Intent(Dashboard.this, UpdateBookActivity.class);
        intent.putExtra("bookNumber", bookNumber);
        startActivity(intent);
    }

//    private void launchDeleteBookActivity() {
//        Intent intent = new Intent(Dashboard.this, DeleteBookActivity.class);
//        startActivity(intent);
//    }

    private void launchIssueBookActivity(List<Integer> selectedPositions) {
        Intent intent = new Intent(Dashboard.this, IssueBookActivity.class);
        intent.putExtra("positions", new ArrayList<>(selectedPositions));
        intent.putExtra("bookList", new ArrayList<>(bookList));
        startActivityForResult(intent, ISSUE_BOOK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ISSUE_BOOK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            borrowData = data.getStringExtra("borrowData");
            returnDate = data.getStringExtra("returnDate");
            borrowCount = data.getIntExtra("borrowCount", 0);
        }
    }

    private void viewBorrowedBooks() {
        // Check if borrow information is available
        if (borrowData != null && returnDate != null && borrowCount > 0) {
            // Create and show a dialog or start a new activity to display the borrow information
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Borrowed Books");
            builder.setMessage("Borrow Date: " + borrowData + "\nReturn Date: " + returnDate + "\nBorrow Count: " + borrowCount);
            builder.setPositiveButton("OK", null);
            builder.create().show();
        } else {
            Toast.makeText(this, "No borrowed books to display", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearBorrowedBooks() {
        borrowData = null;
        returnDate = null;
        borrowCount = 0;
    }

    private void showReturnToast() {
        // Show a long-duration toast indicating that the user needs to return the borrowed books
        Toast.makeText(Dashboard.this, "Please return the borrowed books to the library", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Update the book list when the activity resumes
        bookAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBookClick(int position) {
        toggleSelection(position);
    }

    @Override
    public void onBookCheckboxClick(int position, boolean selected) {
        if (selected) {
            addSelection(position);
        } else {
            removeSelection(position);
        }
    }

    private void toggleSelection(int position) {
        if (selectedPositions.contains(position)) {
            removeSelection(position);
        } else {
            addSelection(position);
        }
    }

    private void addSelection(int position) {
        selectedPositions.add(position);
        bookAdapter.notifyItemChanged(position);
    }

    private void removeSelection(int position) {
        selectedPositions.remove(Integer.valueOf(position));
        bookAdapter.notifyItemChanged(position);
    }

    private void deleteSelectedBooks() {
        if (selectedPositions.isEmpty()) {
            // No selected items
            Toast.makeText(this, "No books selected", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete the selected books?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performDelete();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void performDelete() {
        List<Book> selectedBooks = new ArrayList<>();
        for (int position : selectedPositions) {
            selectedBooks.add(bookList.get(position));
        }

        // Perform deletion logic here with the selectedBooks list

        // Example code for deleting books from the bookDataSource:
        for (Book book : selectedBooks) {
            bookDataSource.deleteBook(book);
        }

        // Clear the selected positions and update the book list
        selectedPositions.clear();
        bookList = bookDataSource.getBookList();
        bookAdapter.notifyDataSetChanged();
    }
}
