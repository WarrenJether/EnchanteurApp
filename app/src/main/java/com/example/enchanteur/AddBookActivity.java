package com.example.enchanteur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookActivity extends AppCompatActivity {

    private EditText edtTitle, edtAuthor, edtCategory;
    private Button btnAddBook;
    private BookDataSource bookDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        edtTitle = findViewById(R.id.edtTextTitle);
        edtAuthor = findViewById(R.id.edtTextAuthor);
        edtCategory = findViewById(R.id.edtTextCategory);
        btnAddBook = findViewById(R.id.btnAddBooks);

        bookDataSource = BookDataSource.getInstance();

        btnAddBook.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String author = edtAuthor.getText().toString().trim();
            String category = edtCategory.getText().toString().trim();
            addBook(title, author, category);
        });
    }

    private void addBook(String title, String author, String category) {
        Book book = new Book(title, author, category);
        bookDataSource.addBook(book);
        Toast.makeText(this, "Book added successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }
}