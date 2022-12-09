package com.example.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Observable;

public class Profile extends Observable {

    dbConnector con;

    private String profileID, profileName, fname, lname, wallet, imageLink, userID;
    private int isFollowed, isSubscribed;

//  CONSTRUCTOR
    public Profile(){} // EMPTY CONSTRUCTOR

    public Profile(Context context){
        con = dbConnector.getInstance(context); // USE TO PASS DB CONNECTION
    }

    public Profile(String profileName, String fname, String lname, int isFollowed, int isSubscribed, String wallet, String imageLink,  String userID) {
        this.profileName = profileName;
        this.fname = fname;
        this.lname = lname;
        this.wallet = wallet;
        this.imageLink = imageLink;
        this.isFollowed = isFollowed;
        this.isSubscribed = isSubscribed;
        this.userID = userID;
    }

//    GETTERS AND SETTERS


    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(int isFollowed) {
        this.isFollowed = isFollowed;
    }

    public int getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(int isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    // NOTE: DATABASE CALLS

    public boolean insert(Profile profile) {
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("profileName", profile.getProfileName());
        contentValues.put("fname", profile.getFname());
        contentValues.put("lname", profile.getLname());
        contentValues.put("isFollowed", profile.getIsFollowed());
        contentValues.put("isSubscribed", profile.getIsSubscribed());
        contentValues.put("wallet", profile.getWallet());
        contentValues.put("imageLink", profile.getImageLink());
        contentValues.put("userID", profile.getUserID());
        try {
            long success = sql.insertOrThrow("profile", null, contentValues);
            return true;
        } catch (android.database.SQLException e){
            Log.e("DATA ERROR", e.getMessage());
            return  false;
        } finally {
            sql.close();;
        }

    }

    public boolean delete(String profileID) {
        SQLiteDatabase sql = con.getWritableDatabase();
        int res = sql.delete("user", "profileID = ?", new String [] {profileID});
        if (res < 0) {
            sql.close();
            return  false;
        }
        sql.close();
        return true;

    }

    public Profile get(String fkID){
        SQLiteDatabase sql = con.getWritableDatabase();
        try {

            Cursor c =  sql.rawQuery("SELECT * FROM profile INNER JOIN user ON profile.userID = ?", new String[] {fkID});
            c.moveToFirst();
            int profileID = c.getInt(0);
            String profileName = c.getString(1);
            String fname = c.getString(2);
            String lname = c.getString(3);
            int isFollowed = c.getInt(4);
            int isSubscribed = c.getInt(5);
            String wallet = c.getString(6);
            String imageLink = c.getString(7);
            String userID = c.getString(8);
            Profile profile = new Profile(profileName, fname,lname,isFollowed,isSubscribed,wallet,imageLink,userID);
            profile.setProfileID(String.valueOf(profileID));
            return profile;
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());

        } finally {
            sql.close();
        }
        return null;
    }

}
