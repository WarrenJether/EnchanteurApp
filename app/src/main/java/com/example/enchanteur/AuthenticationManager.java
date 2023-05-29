package com.example.enchanteur;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {
    private static AuthenticationManager instance;
    private List<Student> students;
    private List<BorrowerStudent> borrowerStudents;

    private AuthenticationManager() {
        students = new ArrayList<>();
        borrowerStudents = new ArrayList<>();
    }

    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    public void addUser(String fullName, String email, String username, String password) {
        for (Student student : students) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                throw new IllegalArgumentException("Username and password have already been used.");
            }
        }
        Student student = new Student(fullName, email, username, password);
        students.add(student);
    }

    public boolean verifyUser(String username, String password) {
        for (Student student : students) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                return true; // User found and verified
            }
        }
        return false; // User not found or verification failed
    }
    public void addBorrower(String studentName, String studentNo, String contactNo) {
        for (BorrowerStudent borrower : borrowerStudents) {
            if (borrower.getName().equals(studentName)) {
                throw new IllegalArgumentException("Borrower with the same name already exists.");
            }
        }

        BorrowerStudent borrower = new BorrowerStudent(studentName, studentNo, contactNo);
        borrower.setBorrowedCount(0); // Initialize borrowed count to 0
        borrowerStudents.add(borrower);
    }

    public void incrementBorrowedCount(String studentName) {
        for (BorrowerStudent borrower : borrowerStudents) {
            if (borrower.getName().equals(studentName)) {
                int currentBorrowedCount = borrower.getBorrowedCount();
                borrower.setBorrowedCount(currentBorrowedCount + 1);
                return;
            }
        }
    }

//    public void addBorrower(String studentName, String studentNo, String contactNo) {
//        for (BorrowerStudent borrower: borrowerStudents) {
//            if (borrower.getName().equals(studentName)) {
//                throw new IllegalArgumentException("Username and password have already been used.");
//            }
//        }
//        BorrowerStudent borrower = new BorrowerStudent(studentName, studentNo, contactNo);
//        borrowerStudents.add(borrower);
//    }

    public List<Student> getStudents() {
        return students;
    }

    public List<BorrowerStudent> getBorrowerStudents() {
        return borrowerStudents;
    }
}
