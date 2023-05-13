package com.example.enchanteur;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {
    private static AuthenticationManager instance;
    private List<Student> students;

    private AuthenticationManager() {
        students = new ArrayList<>();
    }

    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    public void addUser(String username, String password) {
        for (Student student : students) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                throw new IllegalArgumentException("Username and password have already been used.");
            }
        }
        Student student = new Student(username, password);
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

    public List<Student> getStudents() {
        return students;
    }
}
