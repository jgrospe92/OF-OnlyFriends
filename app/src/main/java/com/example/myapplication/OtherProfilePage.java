package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.models.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfilePage extends AppCompatActivity {
    Profile profileHelper;
    TextView otherFnameTxt, otherProfileNameTxt;
    ImageView subUnsubImageViewBtn, folllowUnfollowImageViewBtn;
    CircleImageView otherProfileCircleImageView;
    BottomNavigationView bottomNavigationView;
    String currUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile_page);

        otherFnameTxt = findViewById(R.id.otherBoldFName);
        otherProfileNameTxt = findViewById(R.id.otherProfileNameTag);
        profileHelper = new Profile(this);

        String otherUserID = getIntent().getStringExtra("userID");
        //Toast.makeText(this, otherUserID, Toast.LENGTH_SHORT).show();
        String otherProfileName = profileHelper.get(otherUserID).getProfileName();
        String otherFname = profileHelper.get(otherUserID).getFname();

        otherFnameTxt.setText(otherFname);
        otherProfileNameTxt.setText("@"+ otherProfileName);

        //Getting the user's name and current image
        currUserName = getIntent().getStringExtra("USERNAME");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    // getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, homefragment).commit();
                    //Toast.makeText(getApplicationContext(), "home click", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(getApplicationContext(), home.class);
                    homeIntent.putExtra("USERNAME", currUserName);
                    startActivity(homeIntent);
                    return true;

                case R.id.search:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, searchFragment).commit();
                    //Toast.makeText(getApplicationContext(), "search click", Toast.LENGTH_SHORT).show();
                    Intent searchIntent = new Intent(getApplicationContext(), SearchUser.class);
                    searchIntent.putExtra("USERNAME", currUserName);
                    startActivity(searchIntent);
                    break;

                case R.id.notif:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, notificationFragmet).commit();
                    Toast.makeText(getApplicationContext(), "notif click", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.inbox:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, inboxFragment).commit();
                    Toast.makeText(getApplicationContext(), "notif click", Toast.LENGTH_SHORT).show();
                    break;

            }
            return true;
        });




    }




}