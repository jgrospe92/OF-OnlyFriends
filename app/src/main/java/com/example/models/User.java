package com.example.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

public class User extends Observable {

    private String username;
    private String password;
    private String email;

    dbConnector con;

    public User(String username, String password, String email, Context context) {
        this.username = username;
        this.password = password;
        this.email = email;
        con = dbConnector.getInstance(context);
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public  User(Context context) {
        con = dbConnector.getInstance(context);
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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

//    NOTE: THIS SECTION IS FOR DATABASE CALLS

    public boolean insert(User user) {
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        try {
            long success = sql.insertOrThrow("user", null, contentValues);
            return true;
        } catch (android.database.SQLException e){
            Log.e("DATA ERROR", e.getMessage());
            return  false;
        } finally {
            sql.close();;
        }

    }

    public boolean delete(String userID) {
        SQLiteDatabase sql = con.getWritableDatabase();
        int res = sql.delete("user", "userID = ?", new String [] {userID});
        if (res < 0) {
            sql.close();
            return  false;
        }
        sql.close();
        return true;

    }


    public User get(String userID){
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            Cursor c =  sql.rawQuery("SELECT * FROM user WHERE userID = ?", new String[] {userID});
            c.moveToFirst();
            String username = c.getString(1);
            String password = c.getString(2);
            String email = c.getString(3);
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());
            return null;
        } finally {
            sql.close();
        }
        return new User(username, password, email);
    }
}
