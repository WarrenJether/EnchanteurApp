package com.example.enchanteur;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewBorrower extends AppCompatActivity {
    private ListView listView;
    private List<BorrowerStudent> borrowerStudentList;

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
        BorrowerAdapter adapter = new BorrowerAdapter(this, borrowerStudentList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            BorrowerStudent selectedItem = (BorrowerStudent) parent.getItemAtPosition(position);
            Toast.makeText(ViewBorrower.this, "Selected Borrower: " + selectedItem.getName(), Toast.LENGTH_SHORT).show();
        });
    }
}






