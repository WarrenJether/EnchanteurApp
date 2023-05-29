package com.example.enchanteur;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements BookAdapter.OnBookClickListener {
    TextView tvUser;
    Button btnAdd, btnUpdate, btnDelete, issueBooks, viewBorrower, logout;
    RecyclerView recyclerView;
    private List<Book> bookList;
    private BookAdapter bookAdapter;
    private BookDataSource bookDataSource;
    private final List<Integer> selectedPositions = new ArrayList<>();
    // Define the request code for the issue book activity
    private static final int ADD_BORROWER_REQUEST = 2; // Define the request code for the add borrower activity
    private List<BorrowerStudent> borrowerStudentList;
    private static final int REQUEST_CODE_VIEW_BORROWER = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        issueBooks = findViewById(R.id.borrower);
        viewBorrower = findViewById(R.id.view);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        ImageButton logoutIconBtn = findViewById(R.id.logoutIconBtn);

        String username = getIntent().getStringExtra("username");

        tvUser = findViewById(R.id.tvUser);
        String welcomeMessage = getString(R.string.welcome_user, username);
        tvUser.setText(welcomeMessage);

        recyclerView = findViewById(R.id.myRecycleView);
        bookDataSource = BookDataSource.getInstance();
        bookList = bookDataSource.getBookList();
        bookAdapter = new BookAdapter(bookList, this);
        borrowerStudentList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);

        fabAdd.setOnClickListener(v -> launchAddBookActivity());

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

        issueBooks.setOnClickListener(v -> {
            if (!selectedPositions.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Issue Books");
                builder.setMessage("Do you want to issue the selected books?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(Dashboard.this, IssueBookActivity.class);
                    startActivityForResult(intent, ADD_BORROWER_REQUEST);
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
            } else {
                Toast.makeText(this, "Please select a book to issue", Toast.LENGTH_SHORT).show();
            }
        });

        viewBorrower.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ViewBorrower.class);
            intent.putParcelableArrayListExtra("borrowerStudentList", new ArrayList<>(borrowerStudentList));
            startActivityForResult(intent, REQUEST_CODE_VIEW_BORROWER);
        });

        logoutIconBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
            builder.setTitle("Logout");
            builder.setMessage("Do you want to logout this account?");
            builder.setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(Dashboard.this, SignInForm.class)));
            builder.setNegativeButton("No", null);
            builder.show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_VIEW_BORROWER && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("borrowerStudentList")) {
                borrowerStudentList = data.getParcelableArrayListExtra("borrowerStudentList");
            }
        }

        if (requestCode == ADD_BORROWER_REQUEST && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("borrowerStudentList")) {
                ArrayList<BorrowerStudent> retrievedBorrowerStudentList = data.getParcelableArrayListExtra("borrowerStudentList");
                if (retrievedBorrowerStudentList != null) {
                    borrowerStudentList.clear();
                    borrowerStudentList.addAll(retrievedBorrowerStudentList);
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
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
        builder.setPositiveButton("Delete", (dialog, which) -> performDelete());
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void performDelete() {
        List<Book> selectedBooks = new ArrayList<>();
        for (int position : selectedPositions) {
            selectedBooks.add(bookList.get(position));
        }

        for (Book book : selectedBooks) {
            bookDataSource.deleteBook(book);
        }

        // Clear the selected positions and update the book list
        selectedPositions.clear();
        bookList = bookDataSource.getBookList();
        bookAdapter.notifyDataSetChanged();
    }
}
