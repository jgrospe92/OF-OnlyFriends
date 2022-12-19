package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.models.Profile;
import com.example.models.User;

public class Settings extends AppCompatActivity {

    Button cancelButtonSettings, deactivateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences userData = getSharedPreferences("user", MODE_PRIVATE);
        User userHelper = new User(getApplicationContext());

        cancelButtonSettings = findViewById(R.id.cancelButtonSettings);
        cancelButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        deactivateButton = findViewById(R.id.deactivateButton);
        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                userData.edit().putString("username", "");
                                userData.edit().putString("userID", "");
                                userData.edit().putBoolean("isLoggedIn",false);
                                userHelper.delete(userData.getString("userID", ""));
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("This will delete your account permanently, would you like to continue").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }
}