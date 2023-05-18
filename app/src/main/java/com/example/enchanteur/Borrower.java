package com.example.enchanteur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Borrower extends AppCompatActivity {
    private EditText edtStudentName, edtStudentNo, edtContactNo;
    private Button issueBookBtn;
    private AuthenticationManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower);

        edtStudentName = findViewById(R.id.edtStudentName);
        edtStudentNo = findViewById(R.id.edtStudentNo);
        edtContactNo = findViewById(R.id.edtContactNo);
        issueBookBtn = findViewById(R.id.issueBookButton);

        authManager = AuthenticationManager.getInstance();

        issueBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentName = edtStudentName.getText().toString().trim();
                String studentNo = edtStudentNo.getText().toString().trim();
                String contactNo = edtContactNo.getText().toString().trim();

                if (!studentName.isEmpty() && !studentNo.isEmpty() && !contactNo.isEmpty()) {
                    addBorrower(studentName, studentNo, contactNo);
                } else {
                    Toast.makeText(Borrower.this, "Please fill up form.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addBorrower(String studentName, String studentNo, String contactNo) {
        try {
            authManager.addBorrower(studentName, studentNo, contactNo);
            Toast.makeText(this, "User added successfully.", Toast.LENGTH_SHORT).show();
            clearFields();
            proceedToDashboard();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void proceedToDashboard() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("borrowerStudentList", new ArrayList<>(authManager.getBorrowerStudents()));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void clearFields() {
        edtStudentName.setText("");
        edtStudentNo.setText("");
        edtContactNo.setText("");
    }
}