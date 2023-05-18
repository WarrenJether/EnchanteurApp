package com.example.enchanteur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpForm extends AppCompatActivity {

    TextView loginHere;
    EditText edtCreateUsername, edtCreatePassword, edtCreateFullName, edtCreateEmail;
    Button btnSignUp;
    private AuthenticationManager authManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        authManager = AuthenticationManager.getInstance();

        edtCreateUsername = findViewById(R.id.etxtUsername);
        edtCreatePassword = findViewById(R.id.etxtPassword);
        edtCreateFullName = findViewById(R.id.etxtFullName);
        edtCreateEmail = findViewById(R.id.etxtEmail);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            String createUsername = edtCreateUsername.getText().toString().trim();
            String createPassword = edtCreatePassword.getText().toString().trim();
            String createFullName = edtCreateFullName.getText().toString().trim();
            String createEmail = edtCreateEmail.getText().toString().trim();

            if (createUsername.isEmpty()) {
                Toast.makeText(SignUpForm.this, "Enter Username.", Toast.LENGTH_SHORT).show();
            } else if (createPassword.isEmpty()) {
                Toast.makeText(SignUpForm.this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else if (createFullName.isEmpty()) {
                    Toast.makeText(SignUpForm.this, "Enter Full Name", Toast.LENGTH_SHORT).show();
            } else if (createEmail.isEmpty()) {
                    Toast.makeText(SignUpForm.this, "Enter Email", Toast.LENGTH_SHORT).show();
            } else {
                addUser(createFullName, createEmail, createUsername, createPassword);
            }
        });

        loginHere = findViewById(R.id.txtSignUp);
        loginHere.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpForm.this, SignInForm.class);
            startActivity(intent);
        });
    }

    private void proceedToLoginForm() {
        Intent intent = new Intent(SignUpForm.this, SignInForm.class);
        startActivity(intent);
    }

    private void addUser(String fullName, String email, String username, String password) {
        try {
            authManager.addUser(fullName, email, username, password);
            Toast.makeText(this, "User added successfully.", Toast.LENGTH_SHORT).show();
            clearFields();
            proceedToLoginForm();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }
    private void clearFields() {
        edtCreateFullName.setText("");
        edtCreateEmail.setText("");
        edtCreateUsername.setText("");
        edtCreatePassword.setText("");
    }
}