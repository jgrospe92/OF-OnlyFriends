package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.models.Profile;
import com.example.models.User;

public class EditProfile extends AppCompatActivity {
    User userHelper;
    Profile profileHelper;
    EditText profNameEditTxt, fnameEditTxt, lnameEditTxt, walletEditTxt;
    Button updateButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userHelper = new User(this);
        profileHelper = new Profile(this);

        //Defining the EditText in Edit Profile Page
        profNameEditTxt = findViewById(R.id.profileNameEditText);
        fnameEditTxt = findViewById(R.id.firstNameEditText);
        lnameEditTxt = findViewById(R.id.lastNameEditText);
        walletEditTxt = findViewById(R.id.lastNameEditText);

        //Defining the buttons in Edit Profile Page
        updateButton = findViewById(R.id.updateAppCompatButton);
        cancelButton = findViewById(R.id.cancelAppCompatButton);


        String username = getIntent().getStringExtra("USERNAME");
        User user = userHelper.getUserByUsername(username);
        Profile profile = profileHelper.get(user.getUserID());

        profNameEditTxt.setText(profile.getProfileName());
        fnameEditTxt.setText(profile.getFname());
        lnameEditTxt.setText(profile.getLname());
        walletEditTxt.setText(profile.getWallet());

        /*profileImg = findViewById(R.id.profileImg);
        loadImage(profile.getImageLink(), this, profileImg);*/


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Profile profile = new Profile();

            }
        });
    }
}