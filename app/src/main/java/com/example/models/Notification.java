package com.example.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class Notification {

    private String notifID;
    private String description;
    private String profileID;
    private String postID;
    private String currentProfileId;


    dbConnector con;

    public Notification(){} // EMPTY CONSTRUCTOR

    public Notification(Context context){con =  dbConnector.getInstance(context); }//  CONSTRUCTOR FOR MAKING A HELPER CLASS

    public Notification(String notifID, String description, String profileID, String postID, String currentProfileId) {
        this.notifID = notifID;
        this.description = description;
        this.profileID = profileID;
        this.postID = postID;
        this.currentProfileId = currentProfileId;
    }


    public String getNotifID() {
        return notifID;
    }

    public void setNotifID(String notifID) {
        this.notifID = notifID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getCurrentProfileId() {
        return currentProfileId;
    }

    public void setCurrentProfileId(String currentProfileId) {
        this.currentProfileId = currentProfileId;
    }

    // CRUD OPERATION
    public boolean insert(Notification notification) {
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", notification.getDescription());
        contentValues.put("profileID", notification.getProfileID());
        contentValues.put("postID", notification.getPostID());
        contentValues.put("currentProfileId", notification.getCurrentProfileId());
        try {
            long success = sql.insertOrThrow("notification", null, contentValues);
            return true;
        } catch (android.database.SQLException e) {
            Log.e("DATA ERROR", e.getMessage());
            return false;
        } finally {
            sql.close();
        }
    }

    public Notification getNotifByCurrentProfile(String currentProfileId){
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            Cursor c = sql.rawQuery("SELECT * FROM notification WHERE currentProfileId =?", new String[]{currentProfileId});
            c.moveToFirst();
            Notification n=  new Notification();
            n.setNotifID(c.getString(0)); // notifID
            n.setDescription(c.getString(1)); // description
            n.setProfileID(c.getString(2)); // post owner profile
            n.setPostID(c.getString(3)); // post ID
            n.setCurrentProfileId(c.getString(4)); //  current user profile
            return n;
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());

        } finally {
            sql.close();
        }
        return null;
    }


    public boolean checkIfAlreadyLiked(String profileID, String description, String postID){
        SQLiteDatabase sql = con.getWritableDatabase();

        try {
            Cursor c = sql.rawQuery("SELECT * FROM notification WHERE currentProfileId = ? AND description = ? AND postID = ?", new String[]{profileID, description, postID});
            if (c.moveToFirst()){
                c.close();
                return true;
            }
            else {
                c.close();
                return false;
            }
        } catch (Exception e){

        } finally {
            sql.close();
        }
        return false;
    }

    public boolean checkIfAlreadySaved(String profileID, String description, String postID){
        SQLiteDatabase sql = con.getWritableDatabase();

        try {
            Cursor c = sql.rawQuery("SELECT * FROM notification WHERE currentProfileId = ? AND description = ? AND postID = ?", new String[]{profileID, description, postID});
            if (c.moveToFirst()){
                c.close();
                return true;
            }
            else {
                c.close();
                return false;
            }
        } catch (Exception e){

        } finally {
            sql.close();
        }
        return false;
    }

    public ArrayList<Notification> getAll(String profileID){

        SQLiteDatabase sql = con.getWritableDatabase();
        ArrayList<Notification> notifications = new ArrayList<>();
        // opens a cursor
        Cursor c = sql.rawQuery("SELECT * FROM notification WHERE profileID = ? ORDER by notifID desc", new String[]{profileID});
        if (c.moveToFirst()) {
            do {
                Notification n = new Notification();
                n.setNotifID(c.getString(0));
                n.setDescription(c.getString(1));
                n.setProfileID(c.getString(2));
                n.setPostID(c.getString(3));
                n.setCurrentProfileId(c.getString(4));
                notifications.add(n);
                n = null;
            } while (c.moveToNext());
        } else {
            return  null;
        }
        c.close(); // close the cursor
        sql.close();
        return notifications;
    }

    public  boolean delete(String notifID){
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            return sql.delete("notification", "notifID = ?", new String[]{notifID}) > 0;
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());
        } finally {
            sql.close();
        }
        return false;
    }

    // GET All Like Post
    public ArrayList<String> getLikeOrSavedPostsID(String id, String desc) {
        SQLiteDatabase sql = con.getWritableDatabase();
        ArrayList<String> postsID = new ArrayList<>();
        // opens a cursor
        Cursor c = sql.rawQuery("SELECT * FROM Notification WHERE currentProfileId = ? AND description = ?", new String[]{id, desc});
        if (c.moveToFirst()) {
            do {
                postsID.add(c.getString(c.getColumnIndexOrThrow("postID")));
            } while (c.moveToNext());
        }
        c.close(); // close the cursor
        sql.close();
        return postsID;
    }

}
