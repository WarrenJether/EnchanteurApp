package com.example.enchanteur;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class UpdateBookActivity extends AppCompatActivity {

    private EditText edtUpdateBookNumber, edtUpdateTitle, edtUpdateAuthor, edtUpdateCategory;
    private Button btnUpdateBook;
    private BookDataSource bookDataSource;
    private List<Book> bookList;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        edtUpdateBookNumber = findViewById(R.id.edtTextUpdateBookNumber);
        edtUpdateTitle = findViewById(R.id.edtTextUpdateTitle);
        edtUpdateAuthor = findViewById(R.id.edtTextUpdateAuthor);
        edtUpdateCategory = findViewById(R.id.edtTextUpdateCategory);
        btnUpdateBook = findViewById(R.id.btnUpdateBooks);

        bookDataSource = BookDataSource.getInstance();
        bookList = bookDataSource.getBookList();

        // Retrieve the book number passed from the Dashboard
        int bookNumber = getIntent().getIntExtra("bookNumber", -1);
        if (bookNumber != -1) {
            // Find the book in the list based on the book number
            selectedPosition = findBookPositionByNumber(bookNumber);
            if (selectedPosition != -1) {
                Book book = bookList.get(selectedPosition);

                // Populate the EditText fields with the book data
                edtUpdateBookNumber.setText(String.valueOf(book.getBookNumber()));
                edtUpdateTitle.setText(book.getTitle());
                edtUpdateAuthor.setText(book.getAuthor());
                edtUpdateCategory.setText(book.getCategory());

                btnUpdateBook.setOnClickListener(v -> updateBook());
            } else {
                Toast.makeText(this, "Book not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Invalid book number", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateBook() {
        // Retrieve the input values
        int bookNumber = Integer.parseInt(edtUpdateBookNumber.getText().toString().trim());
        String title = edtUpdateTitle.getText().toString().trim();
        String author = edtUpdateAuthor.getText().toString().trim();
        String category = edtUpdateCategory.getText().toString().trim();

        // Validate the input data
        if (isValidInput(bookNumber, title, author, category)) {
            // Find the book in the list based on the selected position
            Book book = bookList.get(selectedPosition);

            // Remove the current book from the list
            bookList.remove(selectedPosition);

            // Update the book data
            book.setBookNumber(bookNumber);
            book.setTitle(title);
            book.setAuthor(author);
            book.setCategory(category);

            // Add the updated book back to the list
            bookList.add(selectedPosition, book);

            // Display a success message to the user
            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();

            // Navigate back to the previous screen or perform any additional actions as needed
            finish();
        } else {
            // Display an error message to the user indicating the issue with the input data
            Toast.makeText(this, "Invalid input data. Please check the fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInput(int bookNumber, String title, String author, String category) {
        // Perform the required validation checks here
        // Return true if the input data is valid, false otherwise
        return (bookNumber > 0 && !title.isEmpty() && !author.isEmpty() && !category.isEmpty());
    }

    private int findBookPositionByNumber(int bookNumber) {
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book.getBookNumber() == bookNumber) {
                return i;
            }
        }
        return -1;
    }
}