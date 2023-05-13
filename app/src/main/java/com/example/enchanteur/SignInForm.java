package com.example.enchanteur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInForm extends AppCompatActivity {

    EditText etxtUsername, etxtPassword ;
    Button btnSignIn;
    TextView signUpText;
    private AuthenticationManager authManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_form);

        authManager = AuthenticationManager.getInstance();

        etxtUsername = findViewById(R.id.edtLance);
        etxtPassword = findViewById(R.id.edtBallesteros);
        btnSignIn  = findViewById(R.id.btnLogin);
        signUpText = findViewById(R.id.txtSignUp);

        btnSignIn.setOnClickListener(v -> {
            String username = etxtUsername.getText().toString().trim();
            String password = etxtPassword.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(SignInForm.this, "Enter Username.", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(SignInForm.this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else {
                verifyStudent(username, password);
            }
        });

        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(SignInForm.this, SignUpForm.class);
            startActivity(intent);
        });
    }
    private void verifyStudent(String username, String password) {
        if (authManager.verifyUser(username, password)) {
            Toast.makeText(this, "User verified successfully.", Toast.LENGTH_SHORT).show();
            clearFields();
            loginSuccessful(username);
        } else {
           Toast.makeText(this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }
    private void loginSuccessful(String username) {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void clearFields() {
        etxtUsername.setText("");
        etxtPassword.setText("");
    }
}