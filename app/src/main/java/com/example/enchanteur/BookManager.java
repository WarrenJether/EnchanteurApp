package com.example.enchanteur;

import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private List<Book> bookList;

    public BookManager() {
        bookList = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
