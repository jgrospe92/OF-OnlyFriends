package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        walletEditTxt = findViewById(R.id.walletEditText);

        //Defining the buttons in Edit Profile Page
        updateButton = findViewById(R.id.updateAppCompatButton);
        cancelButton = findViewById(R.id.cancelAppCompatButton);

        //Fetching existing user information
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
                String userID = profile.getUserID();
                boolean isUpdated =   profileHelper.update(
                        profNameEditTxt.getText().toString(),
                        fnameEditTxt.getText().toString(),
                        lnameEditTxt.getText().toString(),
                        walletEditTxt.getText().toString(),userID);
                if(isUpdated == true) {
                    Toast.makeText(getApplicationContext(),"Profile updated ",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), profilePage.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Profile not updated ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}