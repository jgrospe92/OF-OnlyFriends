package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.example.models.Profile;

import com.example.models.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class createProfile extends AppCompatActivity {

    EditText et_profileName, et_fname, et_lname, et_amount, et_imageLink;
    String lastInsertUserID;

    User userHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        et_profileName = findViewById(R.id.et_profileName);
        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_amount = findViewById(R.id.et_amount);
        et_imageLink = findViewById(R.id.et_imageLink);
        lastInsertUserID = getIntent().getStringExtra("USER_ID");

        userHelper = new User(this);

    }

    public void create(View view){
        String profileName = et_profileName.getText().toString();
        String fName = et_fname.getText().toString();
        String lName = et_lname.getText().toString();
        String amount = et_amount.getText().toString();
        String imageLink = et_imageLink.getText().toString();

        Profile profile = new Profile(this);
        profile.setProfileName(profileName);
        profile.setFname(fName);
        profile.setLname(lName);
        profile.setWallet(amount);
        profile.setUserID(lastInsertUserID);
        profile.setIsFollowed(0);
        profile.setIsSubscribed(0);
        profile.setImageLink(imageLink);

        if (profile.insert(profile)) {
            Toast.makeText(getApplicationContext(), "Profile Created", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "FAILED TO CREATE YOUR PROFILE, TRY AGAIN!: " , Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        userHelper.delete(lastInsertUserID);
        finish();
    }
}