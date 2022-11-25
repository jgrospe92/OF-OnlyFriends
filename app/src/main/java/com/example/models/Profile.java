package com.example.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Observable;

public class Profile extends Observable {

    dbConnector con;

    private String profileName, fname, lname, followerCount,
            subscriberCount, wallet , userID;

    public Profile(Context context){
        con = dbConnector.getInstance(context);
    }

    public Profile(String profileName, String fname, String lname,
                   String followerCount, String subscriberCount, String wallet, String userID) {
        this.profileName = profileName;
        this.fname = fname;
        this.lname = lname;
        this.followerCount = followerCount;
        this.subscriberCount = subscriberCount;
        this.wallet = wallet;
        this.userID = userID;
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

    public String getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(String followerCount) {
        this.followerCount = followerCount;
    }

    public String getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileName='" + profileName + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", followerCount='" + followerCount + '\'' +
                ", subscriberCount='" + subscriberCount + '\'' +
                ", wallet='" + wallet + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }

    // NOTE: DATABASE CALLS

    public boolean insert(Profile profile) {
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("profileName", profile.getProfileName());
        contentValues.put("fname", profile.getFname());
        contentValues.put("lname", profile.getLname());
        contentValues.put("followerCount", profile.getFollowerCount());
        contentValues.put("subscriberCount", profile.getSubscriberCount());
        contentValues.put("wallet", profile.getWallet());
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
        int res = sql.delete("user", "profileID = ?", new String [] {userID});
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
            String profileName = c.getString(1);
            String fname = c.getString(2);
            String lname = c.getString(3);
            String followerCount = c.getString(4);
            String subscriberCount = c.getString(5);
            String wallet = c.getString(6);
            String userID = c.getString(7);
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());
            return null;
        } finally {
            sql.close();
        }
        return new Profile(profileName, fname, lname, followerCount, subscriberCount, wallet, userID);
    }

}
