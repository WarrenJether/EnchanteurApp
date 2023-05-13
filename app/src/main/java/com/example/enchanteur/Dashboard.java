package com.example.enchanteur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    TextView tvUser;
    Button btnAdd, btnUpdate, btnDelete, btnIssue;
    RecyclerView recyclerView;
    private List<Book> bookList;
    private BookAdapter bookAdapter;
    private BookDataSource bookDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnIssue = findViewById(R.id.btnIssue);

        String username = getIntent().getStringExtra("username");

        tvUser = findViewById(R.id.tvUser);
        String welcomeMessage = getString(R.string.welcome_user, username);
        tvUser.setText(welcomeMessage);

        recyclerView = findViewById(R.id.myRecycleView);
        bookDataSource = BookDataSource.getInstance();
        bookList = bookDataSource.getBookList();
        bookAdapter = new BookAdapter(bookList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);

        btnAdd.setOnClickListener(v -> launchAddBookActivity());

        btnUpdate.setOnClickListener(v -> launchUpdateBookActivity());

        btnDelete.setOnClickListener(v -> launchDeleteBookActivity());

        btnIssue.setOnClickListener(v -> launchIssueBookActivity());
    }

    private void launchAddBookActivity() {
        Intent intent = new Intent(Dashboard.this, AddBookActivity.class);
        startActivity(intent);
    }

    private void launchUpdateBookActivity() {
        // You can pass any required data to the UpdateBookActivity if needed
        Intent intent = new Intent(Dashboard.this, UpdateBookActivity.class);
        startActivity(intent);
    }

    private void launchDeleteBookActivity() {
        // You can pass any required data to the DeleteBookActivity if needed
        Intent intent = new Intent(Dashboard.this, DeleteBookActivity.class);
        startActivity(intent);
    }

    private void launchIssueBookActivity() {
        // You can pass any required data to the IssueBookActivity if needed
        Intent intent = new Intent(Dashboard.this, IssueBookActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update the book list when the activity resumes
        bookAdapter.notifyDataSetChanged();
    }
}
