package com.example.models;

import android.database.sqlite.SQLiteDatabase;

import java.util.Observable;

public class User extends Observable {

    private String username;
    private String password;
    private String email;

    dbConnector con;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean create(User user) {
        SQLiteDatabase sql = con.getWritableDatabase();

        return false;
    }
}
