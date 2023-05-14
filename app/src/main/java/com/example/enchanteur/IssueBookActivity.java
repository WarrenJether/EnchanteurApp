package com.example.enchanteur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class IssueBookActivity extends AppCompatActivity {

    private RecyclerView selectedItemsRecyclerView;
    private SelectedBookAdapter selectedBookAdapter;
    private List<Book> selectedBooks;
    private Button confirmButton;
    private String borrowData; // Variable to store the borrow date
    private String returnDate; // Variable to store the return date
    private int borrowCount; // Variable to store the count of items borrowed
    private List<Integer> selectedPositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);

        confirmButton = findViewById(R.id.btnConfirm);
        selectedItemsRecyclerView = findViewById(R.id.selectedItemsRecyclerView);

        // Retrieve the list of selected books from the intent
        List<Integer> selectedPositions = getIntent().getIntegerArrayListExtra("positions");
        List<Book> bookList = getIntent().<Book>getParcelableArrayListExtra("bookList");

        selectedBooks = new ArrayList<>();
        assert selectedPositions != null;
        for (int position : selectedPositions) {
            assert bookList != null;
            selectedBooks.add(bookList.get(position));
        }

        // Set up the RecyclerView with the SelectedBookAdapter
        selectedBookAdapter = new SelectedBookAdapter(selectedBooks);
        selectedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedItemsRecyclerView.setAdapter(selectedBookAdapter);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmBorrowAction();
            }
        });
    }

    private void confirmBorrowAction() {
        // Calculate the return date using a separate method
        String returnDate = calculateReturnDate();

        borrowData = getCurrentDate(); // Get the current date as the borrow date
        this.returnDate = returnDate; // Set the calculated return date
        borrowCount = selectedBooks.size(); // Set the count of items borrowed based on the selectedBooks list

        Intent resultIntent = new Intent();
        resultIntent.putExtra("borrowData", borrowData);
        resultIntent.putExtra("returnDate", returnDate);
        resultIntent.putExtra("borrowCount", borrowCount);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private String calculateReturnDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}