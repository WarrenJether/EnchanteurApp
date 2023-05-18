package com.example.enchanteur;

public class Student {
    private String fullName;
    private String email;
    private String username;
    private String password;

    public Student(String fullName, String email, String username, String password) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
