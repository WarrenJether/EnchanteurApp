package com.example.enchanteur;

import android.os.Parcel;
import android.os.Parcelable;

public class BorrowerStudent implements Parcelable {
    private String name;
    private String studentNumber;
    private String contactNumber;

    public BorrowerStudent(String name, String studentNumber, String contactNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.contactNumber = contactNumber;
    }

    protected BorrowerStudent(Parcel in) {
        name = in.readString();
        studentNumber = in.readString();
        contactNumber = in.readString();
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

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(studentNumber);
        dest.writeString(contactNumber);
    }
}