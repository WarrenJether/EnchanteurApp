package com.example.enchanteur;

import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class BorrowerStudent implements Parcelable {
    private String name;
    private String studentNo;
    private String contactNo;
    private Date borrowedDate;
    private Date returnDate;
    private int borrowedCount;

    public BorrowerStudent(String name, String studentNo, String contactNo) {
        this.name = name;
        this.studentNo = studentNo;
        this.contactNo = contactNo;
    }

    protected BorrowerStudent(Parcel in) {
        name = in.readString();
        studentNo = in.readString();
        contactNo = in.readString();
        borrowedDate = new Date(in.readLong());
        returnDate = new Date(in.readLong());
    }

    public static final Creator<BorrowerStudent> CREATOR = new Creator<BorrowerStudent>() {
        @Override
        public BorrowerStudent createFromParcel(Parcel in) {
            return new BorrowerStudent(in);
        }

        @Override
        public BorrowerStudent[] newArray(int size) {
            return new BorrowerStudent[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(studentNo);
        dest.writeString(contactNo);
        dest.writeLong(borrowedDate != null ? borrowedDate.getTime() : -1);
        dest.writeLong(returnDate != null ? returnDate.getTime() : -1);
    }

    public void updateBorrowedCount(int count) {
        borrowedCount = count;
    }

    public void incrementBorrowedCount() {
        borrowedCount++;
    }

}
