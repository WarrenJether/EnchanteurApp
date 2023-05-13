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
    EditText edtCreateUsername, edtCreatePassword;
    Button btnSignUp;
    private AuthenticationManager authManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        authManager = AuthenticationManager.getInstance();

        edtCreateUsername = findViewById(R.id.etxtUsername);
        edtCreatePassword = findViewById(R.id.etxtPassword);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            String createUsername = edtCreateUsername.getText().toString().trim();
            String createPassword = edtCreatePassword.getText().toString().trim();

            if (createUsername.isEmpty()) {
                Toast.makeText(SignUpForm.this, "Enter Username.", Toast.LENGTH_SHORT).show();
            } else if (createPassword.isEmpty()) {
                Toast.makeText(SignUpForm.this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else {
                addUser(createUsername, createPassword);
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

    private void addUser(String username, String password) {
        try {
            authManager.addUser(username, password);
            Toast.makeText(this, "User added successfully.", Toast.LENGTH_SHORT).show();
            clearFields();
            proceedToLoginForm();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }
    private void clearFields() {
        edtCreateUsername.setText("");
        edtCreatePassword.setText("");
    }
}