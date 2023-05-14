package com.example.enchanteur;

import android.os.Parcel;
import android.os.Parcelable;

class Book implements Parcelable {
    private int bookNumber;
    private String title;
    private String author;
    private String category;
    private boolean isSelected;

    public Book(int bookNumber, String title, String author, String category) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isSelected = false; // Default value is not selected
    }

    protected Book(Parcel in) {
        bookNumber = in.readInt();
        title = in.readString();
        author = in.readString();
        category = in.readString();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookNumber);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(category);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}


