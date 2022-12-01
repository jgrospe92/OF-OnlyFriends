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
        db.execSQL("create table profile (profileID integer primary key autoincrement, profileName varchar, fname varchar, lname varchar, followerCount varchar, followingCount varchar, subscriberCount varchar , subscribedCount varchar, wallet varchar, imageLink varchar, userID integer, foreign key (userID) references user (userID) on delete cascade)");
//      SUBSCRIPTION TABLE
        db.execSQL("create table subscription (subID integer primary key autoincrement, subscribeTo integer, isUnluck integer, profileID integer , foreign key (profileID) references profile (profileID) on delete cascade )");
//      POST TABLEdb
        db.execSQL("create table post (postID integer primary key autoincrement, caption text, datePosted text, likes varchar , favorites varchar , profileID integer, foreign key (profileID) references profile (profileID) on delete cascade)");

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
