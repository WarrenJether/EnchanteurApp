package com.example.enchanteur;

import java.util.ArrayList;
import java.util.List;

public class BookDataSource {
    private static BookDataSource instance;
    private List<Book> bookList;

    private BookDataSource() {
        bookList = new ArrayList<>();
    }

    public static BookDataSource getInstance() {
        if (instance == null) {
            instance = new BookDataSource();
        }
        return instance;
    }

    public List<Book> getBookList() {
        return bookList;
    }
    public void addBook(Book book) {
        bookList.add(book);
    }
}
