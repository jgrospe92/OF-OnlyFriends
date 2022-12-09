package com.example.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

public class Post extends Observable {

      //  db.execSQL("create table post (postID integer primary key autoincrement, caption text, datePosted text, likes integer , favorites integer , imageURL varchar, profileID integer, foreign key (profileID) references profile (profileID) on delete cascade)");

     private String postID;
     private String caption;
     private String datePosted;
     private int likes;
     private int favorites;
     private String imageURL;
     private String profileID;
     dbConnector con;

    // CONSTRUCTORS
    // CONSTRUCTOR FOR CREATING POST OBJECT
    public Post(){}
    public Post(String caption, String datePosted, int likes, int favorites, String imageURL, String profileID) {
        this.caption = caption;
        this.datePosted = datePosted;
        this.likes = likes;
        this.favorites = favorites;
        this.imageURL = imageURL;
        this.profileID = profileID;
    }

    // HELPER CLASS
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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    // CRUD OPERATIONS


   // INSERTING POST OBJECT TO THE DB
    public boolean insert(Post post, String profileID){
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("caption", post.getCaption());
        contentValues.put("datePosted", post.getDatePosted());
        contentValues.put("likes", post.getLikes());
        contentValues.put("favorites", post.getFavorites());
        contentValues.put("imageURL", post.getImageURL());
        contentValues.put("profileID", post.getProfileID());
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

    // SEARCH POST BY KEYWORD(s); returns an arrayList of type Post
    public ArrayList<Post> searchByKeyWord(String word){
        SQLiteDatabase sql = con.getWritableDatabase();
        ArrayList<Post> posts = new ArrayList<>();
        if (word.isEmpty()){return  null; }
        try {
            // opens a cursor
            Cursor c =  sql.rawQuery("SELECT * FROM post WHERE caption like ? ", new String[] {"%"+word+"%"});
            while(c.moveToFirst()){
                Post p = new Post();
                p.setPostID(c.getString(0)); // postID
                p.setCaption(c.getString(1)); // captionID
                p.setDatePosted(c.getString(2)); // datePosted
                p.setLikes(c.getInt(3)); // likes
                p.setFavorites(c.getInt(4)); // favorites
                p.setImageURL(c.getString(5)); // imageURL
                p.setProfileID(c.getString(6));
                posts.add(p);
            }
            c.close(); // close the cursor
            return posts;
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());

        } finally {
            sql.close();
        }
        return null;
    }

    // UPDATE POST
    public boolean update(Post post){
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("caption", post.getCaption());
        contentValues.put("datePosted", post.getDatePosted());
        contentValues.put("likes", post.getLikes());
        contentValues.put("favorites", post.getFavorites());

        try{
            sql.update("post", contentValues, "postID = ?", new String[]{post.getPostID()});
            return true;

        } catch (Exception e){
            Log.e("ERROR MESSAGE: ", e.getMessage());
        } finally {
            sql.close();
        }
        return  false;
    }

    // DELETE POST
    public boolean delete(Post post){
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            return sql.delete("post", "postID = ?", new String[]{post.getPostID()}) > 0;
        } catch (Exception e){
            Log.e("ERROR MESSAGE: ", e.getMessage());
        }
        finally {
            sql.close();
        }
        return false;
    }
}
