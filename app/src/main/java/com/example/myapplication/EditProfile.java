package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {
EditText profNameEditTxt, fnameEditTxt, lnameEditTxt, walletEditTxt;
Button updateButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //Defining the EditText in Edit Profile Page
        profNameEditTxt = findViewById(R.id.profileNameEditText);
        fnameEditTxt = findViewById(R.id.firstNameEditText);
        lnameEditTxt = findViewById(R.id.lastNameEditText);
        walletEditTxt = findViewById(R.id.lastNameEditText);

        //Defining the buttons in Edit Profile Page
        updateButton = findViewById(R.id.updateAppCompatButton);
        cancelButton = findViewById(R.id.cancelAppCompatButton);




    }
}