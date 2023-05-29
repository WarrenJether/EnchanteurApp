package com.example.enchanteur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewBorrower extends AppCompatActivity {
    private ListView listView;
    private List<BorrowerStudent> borrowerStudentList;
    private BorrowerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_borrower);

        // Retrieve the borrowerStudentList from the extras
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("borrowerStudentList")) {
            borrowerStudentList = extras.getParcelableArrayList("borrowerStudentList");
        } else {
            borrowerStudentList = new ArrayList<>(); // Default empty list
        }

        listView = findViewById(R.id.mListView);
        adapter = new BorrowerAdapter(this, borrowerStudentList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            BorrowerStudent selectedItem = (BorrowerStudent) parent.getItemAtPosition(position);
            selectedItem.incrementBorrowedCount();

            Date borrowedDate = selectedItem.getBorrowedDate(); // Replace with your actual borrowed date
            Date returnDate = selectedItem.getReturnDate(); // Replace with your actual return date

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBorrower.this);
            builder.setTitle("Borrower Details");
            builder.setMessage("Name: " + selectedItem.getName() +
                    "\nBorrowed Date: " + formatDate(borrowedDate) +
                    "\nReturn Date: " + formatDate(returnDate) +
                    "\nBorrowed Item: " + selectedItem.getBorrowedCount() +
                    "\nPenalty Fine: $" + calculatePenalty(borrowedDate, returnDate));
            builder.setPositiveButton("OK", null);
            builder.setNegativeButton("Return", (dialog, which) -> {
                borrowerStudentList.remove(selectedItem);
                Toast.makeText(this, "Borrowed item returned.", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    // Method to calculate penalty based on borrowed date and return date
    private double calculatePenalty(Date borrowedDate, Date returnDate) {
        // Get the current date
        Date currentDate = new Date();

        // Check if the return date is before the current date
        if (returnDate.before(currentDate)) {
            // Calculate the difference in days between the return date and current date
            long difference = currentDate.getTime() - returnDate.getTime();
            int daysLate = (int) (difference / (24 * 60 * 60 * 1000)); // Convert milliseconds to days

            double penaltyRatePerDay = 2.5; // Example penalty rate per day
            double penaltyFine = penaltyRatePerDay * daysLate;
            return penaltyFine;
        }

        return 0.0; // No penalty if the return date is not passed yet
    }

    // Method to format the date as a string
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    private void saveUpdatedBorrowerList() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("borrowerStudentList", new ArrayList<>(borrowerStudentList));
        setResult(RESULT_OK, intent);
    }
    @Override
    public void onBackPressed() {
        saveUpdatedBorrowerList();
        super.onBackPressed();
    }
}







