package com.example.enchanteur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    private EditText edtBookNumber ,edtTitle, edtAuthor, edtCategory;
    private Button btnAddBook;
    private BookDataSource bookDataSource;
    private TextView tvFillInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        edtBookNumber = findViewById(R.id.edtTextBookNumber);
        edtTitle = findViewById(R.id.edtTextTitle);
        edtAuthor = findViewById(R.id.edtTextAuthor);
        edtCategory = findViewById(R.id.edtTextCategory);
        btnAddBook = findViewById(R.id.btnAddBooks);
        tvFillInfo = findViewById(R.id.tvFillInformation);

        bookDataSource = BookDataSource.getInstance();

        btnAddBook.setOnClickListener(v -> {
            String bookNumberStr = edtBookNumber.getText().toString().trim();
            String title = edtTitle.getText().toString().trim();
            String author = edtAuthor.getText().toString().trim();
            String category = edtCategory.getText().toString().trim();

            if (!bookNumberStr.isEmpty() && !title.isEmpty() && !author.isEmpty() && !category.isEmpty()) {
                int bookNumber = Integer.parseInt(bookNumberStr);
                addBook(bookNumber, title, author, category);
            } else {
                Toast.makeText(AddBookActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBook(int bookNumber, String title, String author, String category) {
        if (isBookNumberUnique(bookNumber)) {
            Book book = new Book(bookNumber, title, author, category);
            bookDataSource.addBook(book);
            Toast.makeText(this, "Book added successfully.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "A book with the same number already exists.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isBookNumberUnique(int bookNumber) {
        List<Book> bookList = bookDataSource.getBookList();
        for (Book book : bookList) {
            if (book.getBookNumber() == bookNumber) {
                return false; // Book number already exists
            }
        }
        return true; // Book number is unique
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}