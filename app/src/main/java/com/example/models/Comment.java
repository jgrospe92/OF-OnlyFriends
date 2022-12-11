package com.example.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
public class Comment extends  Observable{

    dbConnector con;

    private String commentID;
    private String caption;
    private String datePosted;
    private String profileID;
    private String postID;

    // CONSTRUCTORS
    public Comment(){} // EMPTY CONSTRUCTOR

    public Comment(String caption, String datePosted, String profileID, String postID) {
        this.caption = caption;
        this.datePosted = datePosted;
        this.profileID = profileID;
        this.postID = postID;
    }
    // HELPER CLASS
    public Comment(Context context){
        con = dbConnector.getInstance(context);
    }

    // GETTERS AND SETTERS


    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
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

    // CRUD OPERATIONS
    public boolean insert(Comment comment){
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("caption", comment.getCaption());
        cv.put("datePosted", comment.getDatePosted());
        cv.put("profileID", comment.getProfileID());
        cv.put("postID", comment.getPostID());
        try {
            long success = sql.insertOrThrow("comment", null, cv);
            return true;
        } catch (android.database.SQLException e){
            Log.e("DATA ERROR", e.getMessage());

        } finally {
            sql.close();;
        }
        return  false;
    }

    // RETURNS AN ARRAYLIST OF TYPE COMMENT
    public ArrayList<Comment> getAll(String postID){
        SQLiteDatabase sql = con.getWritableDatabase();
        ArrayList<Comment> comments = new ArrayList<>();
        // OPEN CURSOR
        Cursor c = sql.rawQuery("SELECT * FROM comment where postID = ?", new String[]{postID});

        if (c.moveToFirst()){
            do{
                Comment comment = new Comment();
                comment.setCaption(c.getString(c.getColumnIndexOrThrow("caption")));
                comment.setDatePosted(c.getString(c.getColumnIndexOrThrow("datePosted")));
                comment.setProfileID(c.getString(c.getColumnIndexOrThrow("profileID")));
                comment.setPostID(c.getString(c.getColumnIndexOrThrow("postID")));
                comments.add(comment);

            }while (c.moveToNext());
            c.close();
            sql.close();
            return  comments;
        }
        c.close();
        sql.close();
        return  null;
    }

    // GET AND RETURN SINGLE COMMENT OBJECT
    public Comment get(String commentID){
        SQLiteDatabase sql = con.getWritableDatabase();
        try{
            Cursor c = sql.rawQuery("SELECT * FROM comment WHERE commentID = ?", new String[]{commentID});
            if(c.moveToFirst()){
                Comment comment = new Comment();
                comment.setCommentID(c.getString(c.getColumnIndexOrThrow("commentID")));
                comment.setCaption(c.getString(c.getColumnIndexOrThrow("caption")));
                comment.setDatePosted(c.getString(c.getColumnIndexOrThrow("datePosted")));
                comment.setProfileID(c.getString(c.getColumnIndexOrThrow("profileID")));
                comment.setPostID(c.getString(c.getColumnIndexOrThrow("postID")));
                c.close();
                return  comment;
            }
        }catch (Exception e){
            Log.e("ERROR MESSAGE: ", e.getMessage());
        } finally {
            sql.close();
        }
        return null;
    }

    // UPDATES COMMENT CAPTION
    public boolean update(Comment comment) {
        SQLiteDatabase sql = con.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("caption", comment.getCaption());

        try {
            sql.update("comment", cv, "commentID = ?", new String[]{comment.getCommentID()});
            return true;

        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());
        } finally {
            sql.close();
        }
        return false;
    }

    // DELETE COMMENT
    public boolean delete(String commentID){
        SQLiteDatabase sql = con.getWritableDatabase();
        try {
            return sql.delete("comment", "commentID = ?", new String[]{commentID}) > 0;
        } catch (Exception e) {
            Log.e("ERROR MESSAGE: ", e.getMessage());
        } finally {
            sql.close();
        }
        return false;
    }
}
