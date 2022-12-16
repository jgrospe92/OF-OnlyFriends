package com.example.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

public class Post extends Observable {


    private String postID;
    private String caption;
    private String datePosted;
    private int likes;
    private int favorites;
    private String imageURL;
    private String profileID;

    public int getNumOfReplies() {
        return numOfReplies;
    }

    public void setNumOfReplies(int numOfReplies) {
        this.numOfReplies = numOfReplies;
    }

    private int numOfReplies;
    dbConnector con;

    // CONSTRUCTORS
    // CONSTRUCTOR FOR CREATING POST OBJECT
    public Post() {
    }

    public Post(String caption, String datePosted, int likes, int favorites, String imageURL, String profileID) {
        this.caption = caption;
        this.datePosted = datePosted;
        this.likes = likes;
        this.favorites = favorites;
        this.imageURL = imageURL;
        this.profileID = profileID;
    }

    // HELPER CLASS
    public Post(Context context) {
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
    public boolean insert(Post post, String profileID) {
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
        } catch (android.database.SQLException e) {
            Log.e("DATA ERROR", e.getMessage());
            return false;
        } finally {
            sql.close();
            ;
        }
    }


    // GET ALL POST
    public ArrayList<Post> getAllPosts() {
        SQLiteDatabase sql = con.getWritableDatabase();
        ArrayList<Post> posts = new ArrayList<>();
        // opens a cursor
        Cursor c = sql.rawQuery("SELECT * FROM post ORDER by postID desc", null);
        if (c.moveToFirst()) {
            do {
                Post p = new Post();
                p.setPostID(c.getString(0)); // postID
                p.setCaption(c.getString(1)); // captionID
                p.setDatePosted(c.getString(2)); // datePosted
                p.setLikes(c.getInt(3)); // likes
                p.setFavorites(c.getInt(4)); // favorites
                p.setImageURL(c.getString(5)); // imageURL
                p.setProfileID(c.getString(6));
                posts.add(p);
            } while (c.moveToNext());
        }
        c.close(); // close the cursor
        sql.close();
        return posts;
    }

    // SEARCH POST BY KEYWORD(s); returns an arrayList of type Post
    public ArrayList<Post> searchByKeyWord(String word) {
        SQLiteDatabase sql = con.getWritableDatabase();
        ArrayList<Post> posts = new ArrayList<>();
        if (word.isEmpty()) {
            return null;
        }
        try {
            // opens a cursor
            Cursor c = sql.rawQuery("SELECT * FROM post WHERE caption like ? ", new String[]{"%" + word + "%"});
            while (c.moveToNext()) {
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
    public boolean update(Post post) {
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("caption", post.getCaption());
        contentValues.put("datePosted", post.getDatePosted());
        contentValues.put("likes", post.getLikes());
        contentValues.put("favorites", post.getFavorites());

        try {
            sql.update("post", contentValues, "postID = ?", new String[]{post.getPostID()});
            return true;

        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());
        } finally {
            sql.close();
        }
        return false;
    }

    // GET NUMBER OF COMMENTS
    public  String getNumberOfComments(String postID){
        SQLiteDatabase sql = con.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(sql, "comment", "postID=?", new String[]{postID});
        return  String.valueOf(count);
    }

    public Post get(String postID) {
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            Cursor c = sql.rawQuery("SELECT * FROM post WHERE postID =?", new String[]{postID});
            c.moveToFirst();
            Post p = new Post();
            p.setPostID(c.getString(0)); // postID
            p.setCaption(c.getString(1)); // captionID
            p.setDatePosted(c.getString(2)); // datePosted
            p.setLikes(c.getInt(3)); // likes
            p.setFavorites(c.getInt(4)); // favorites
            p.setImageURL(c.getString(5)); // imageURL
            p.setProfileID(c.getString(6));
            return p;
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());

        } finally {
            sql.close();
        }
        return null;
    }

    // DELETE POST
    public boolean delete(String postid) {
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            return sql.delete("post", "postID = ?", new String[]{postid}) > 0;
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());
        } finally {
            sql.close();
        }
        return false;
    }
}
