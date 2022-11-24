package com.example.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbConnector extends SQLiteOpenHelper {

    private static dbConnector sInstance;

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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON");
        db.execSQL("create table user (userID integer primary key autoincrement, username varchar, password varchar, email varchar)");
        db.execSQL("create table profile (profileID iteger primary key autoincrement, profileName varchar, fname varcahr, lname varchar," +
                " followerCount varchar default '0', following count default '0',wallet varchar, foreign key (userID) references user (userID) on delete cascade)");
        db.execSQL("create table subscription (subID integer primary key autoincrement, subscribeTo integer, isUnluck integer, foreign key (profileID) references profile (profileID) on delete cascade )");
        db.execSQL("create table post (postID integer primary key autoincrement, caption text, datePosted text, likes varchar default '0', favorites varchar default '0'," +
                "foreign key (profileID) references profile (profileID) on delete cascade)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists profile");
        db.execSQL("drop table if exists subscription");
        db.execSQL("drop table if exists post");
        onCreate(db);

    }
}
