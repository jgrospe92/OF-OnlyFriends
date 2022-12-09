package com.example.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Observable;

public class Post extends Observable {

      //  db.execSQL("create table post (postID integer primary key autoincrement, caption text, datePosted text, likes varchar , favorites varchar , profileID integer, foreign key (profileID) references profile (profileID) on delete cascade)");

     private String caption;
     private String datePosted;
     private String likes;
     private String favorites;
     private String profileID;
     dbConnector con;

    public Post(String caption, String datePosted, String likes, String favorites, String profileID) {

        this.caption = caption;
        this.datePosted = datePosted;
        this.likes = likes;
        this.favorites = favorites;
        this.profileID = profileID;
    }

    public Post(Context context){
        con = dbConnector.getInstance(context);
    }





    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }
   //inserting a post method
    public boolean insert(Post post, String profileID){
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("caption", post.getCaption());
        contentValues.put("datePosted", post.getDatePosted());
        contentValues.put("likes", post.getLikes());
        contentValues.put("favorites", post.getFavorites());
        contentValues.put("profileID", profileID);
        try {
            long success = sql.insertOrThrow("post", null, contentValues);
            return true;
        } catch (android.database.SQLException e){
            Log.e("DATA ERROR", e.getMessage());
            return  false;
        } finally {
            sql.close();;
        }
    }
}
