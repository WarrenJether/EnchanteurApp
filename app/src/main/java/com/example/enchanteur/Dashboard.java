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

    TextView  tvUser;
    Button btnAdd, btnUpdate, btnDelete, btnIssue;
    RecyclerView recyclerView;
    private List<Book> bookList;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String username = getIntent().getStringExtra("username");

        // Set the welcome message using the formatted string resource
        String welcomeMessage = getString(R.string.welcome_user, username);
        tvUser.setText(welcomeMessage);


        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnIssue = findViewById(R.id.btnIssue);
        tvUser = findViewById(R.id.tvUser);


        recyclerView = findViewById(R.id.myRecycleView);
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList);

        // Set up the RecyclerView with the BookAdapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);
        populateBookList();

        btnAdd.setOnClickListener(v -> launchAddBookActivity());

        btnUpdate.setOnClickListener(v -> launchUpdateBookActivity());

        btnDelete.setOnClickListener(v -> launchDeleteBookActivity());

        btnIssue.setOnClickListener(v -> launchIssueBookActivity());

    }
    private void populateBookList() {
        // Retrieve book data from a database or API and add it to the bookList
        // For demonstration, let's add some dummy book data
        bookList.add(new Book("Book 1", "Author 1", "Category 1"));
        bookList.add(new Book("Book 2", "Author 2", "Category 2"));
        bookList.add(new Book("Book 3", "Author 3", "Category 3"));

        // Notify the adapter that the data set has changed
        bookAdapter.notifyDataSetChanged();
    }

    private void launchAddBookActivity() {
        Intent intent = new Intent(Dashboard.this, AddBookActivity.class);
        startActivity(intent);
    }

    private void launchUpdateBookActivity() {
        Intent intent = new Intent(Dashboard.this, UpdateBookActivity.class);
        startActivity(intent);
    }

    private void launchDeleteBookActivity() {
        Intent intent = new Intent(Dashboard.this, DeleteBookActivity.class);
        startActivity(intent);
    }

    private void launchIssueBookActivity() {
        Intent intent = new Intent(Dashboard.this, IssueBookActivity.class);
        startActivity(intent);
    }


}
