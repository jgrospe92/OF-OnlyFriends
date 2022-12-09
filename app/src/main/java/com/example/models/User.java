package com.example.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Observable;

public class User extends Observable {

    private String username;
    private String password;
    private String email;
    private String userID;



    private long lastInsertedID;

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

    public long getLastInsert(){
        return  lastInsertedID;
    }

    public void setUserID(String userID){this.userID = userID;}

    public  String getUserID(){return this.userID;};

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
//       INSERT NEW USER INTO THE user table
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        try {
            lastInsertedID = sql.insertOrThrow("user", null, contentValues);
            return true;
        } catch (android.database.SQLException e){
            Log.e("DATA ERROR", e.getMessage());
            return  false;
        } finally {
            sql.close();;
        }

    }

    public boolean delete(String userID) {
//       DELETE USER USING THE USER ID
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
//        GET USER USING THE USER ID AND RETURNS A USER Object
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            Cursor c =  sql.rawQuery("SELECT * FROM user WHERE userID =?", new String[] {userID});
            c.moveToFirst();
            String id = c.getString(0);
            String username = c.getString(1);
            String password = c.getString(2);
            String email = c.getString(3);
            User user = new User(username, password, email);
            user.setUserID(id);
            c.close();
            return user;
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());

        } finally {
            sql.close();

        }
        return null;
    }

    public  User getUserByUsername(String user) {
//        RETURNS USER OBJECT USING THE USERNAME
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            Cursor c =  sql.rawQuery("SELECT * FROM user WHERE username=?", new String[] {user});
            c.moveToFirst();
            String userID = c.getString(0);
            String username = c.getString(1);
            String password = c.getString(2);
            String email = c.getString(3);
            User getUser = new User(username, password, email);
            getUser.setUserID(userID);
            Log.e("CHECKING ID", getUser.getUserID());
            c.close();
            return  getUser;
        } catch (Exception e) {
            Log.e("ERROR FROM USER MODEL: ", e.getMessage());
        } finally {
            sql.close();
        }
        return null;
    }


    public String verifyPassword(String username) {
//      CHECK IF THE PASSWORD MATCHES WITH THE USER SAVED IN THE DB
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            String query = "SELECT password FROM user WHERE username=?";
            Cursor c = sql.rawQuery(query, new String[]{username});
            if (c.moveToFirst()){
                String password = c.getString(c.getColumnIndexOrThrow("password"));
                c.close();
                return password;
            }
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());
        }
        finally {
            sql.close();
        }
        return null;
    }
}
