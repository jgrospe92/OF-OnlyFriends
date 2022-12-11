package com.example.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbConnector extends SQLiteOpenHelper {

    private static dbConnector sInstance;
    private Context sContext;

    public static final String DB_NAME = "Test.db";
    public static final int DB_VERSION = 1;



    public static synchronized dbConnector getInstance(Context context){
        if (sInstance == null) {
            sInstance = new dbConnector(context.getApplicationContext());
        }
        return sInstance;
    }

    private dbConnector(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.sContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON");
//      USER TABLE
        db.execSQL("create table user (userID integer primary key autoincrement, username varchar UNIQUE, password varchar, email varchar)");
//      PROFILE TABLE

        db.execSQL("create table profile (profileID integer primary key autoincrement, profileName varchar, fname varchar, lname varchar, isFollowed integer, isSubscribed integer, wallet varchar, imageLink varchar, userID integer, foreign key (userID) references user (userID) on delete cascade)");
//      FOLLOWER TABLE
        db.execSQL("create table follower (followerID integer primary key autoincrement, dateFollowed text, profileID integer , foreign key (profileID) references profile (profileID) on delete cascade )");
//      SUBSCRIBER TABLE
        db.execSQL("create table subscriber (subscriberID integer primary key autoincrement, dateSubscribed text, profileID integer , foreign key (profileID) references profile (profileID) on delete cascade )");
//      FOLLOWING TABLE
        db.execSQL("create table following (followingID integer primary key autoincrement, dateAdded text, profileID integer , foreign key (profileID) references profile (profileID) on delete cascade )");
//      SUBSCRIBED TABLE
        db.execSQL("create table subscribed (followingID integer primary key autoincrement, dateAdded text, profileID integer , foreign key (profileID) references profile (profileID) on delete cascade )");
//      POST TABLE
        db.execSQL("create table post (postID integer primary key autoincrement, caption text, datePosted text, likes integer , favorites integer , imageURL varchar, profileID integer, foreign key (profileID) references profile (profileID) on delete cascade)");
//      COMMENT TABLE
        db.execSQL("create table comment (commentID integer primary key autoincrement, caption text, datePosted text, profileID integer, postID integer, foreign key (profileID) references profile (profileID) on delete cascade, foreign key (postID) references post (postID) on delete cascade)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists profile");
        db.execSQL("drop table if exists subscription");
        db.execSQL("drop table if exists post");
        onCreate(db);

    }

    // USER


}
