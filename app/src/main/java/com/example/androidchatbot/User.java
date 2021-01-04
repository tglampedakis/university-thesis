package com.example.androidchatbot;

public class User {
    public String firstName, lastName, username, email;

    public User(){

    }

    public User(String firstName, String lastName, String username, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }
}
