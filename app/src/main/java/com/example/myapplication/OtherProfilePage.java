package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.models.Profile;
import com.example.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfilePage extends AppCompatActivity {
    Profile profileHelper;
    User userHelper;
    TextView otherFnameTxt, otherProfileNameTxt;
    ImageView subUnsubImageViewBtn, folllowUnfollowImageViewBtn;
    CircleImageView otherProfileCircleImageView;
    BottomNavigationView bottomNavigationView;
    String currUserName;
    postfragment postFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile_page);

        otherFnameTxt = findViewById(R.id.otherBoldFName);
        otherProfileNameTxt = findViewById(R.id.otherProfileNameTag);
        profileHelper = new Profile(this);

        //TODO: restrict a current user if he / she is not yet subscribe to this other user.
        userHelper = new User(this);
        String otherUserID = getIntent().getStringExtra("userID");
        String otherUserName = userHelper.get(otherUserID).getUsername();
        //Toast.makeText(this, otherUserID, Toast.LENGTH_SHORT).show();
        String otherProfileName = profileHelper.get(otherUserID).getProfileName();
        String otherFname = profileHelper.get(otherUserID).getFname();

        //Getting the user's name and current image
        currUserName = getIntent().getStringExtra("USERNAME");

        postFragment = new postfragment(otherUserID);

        //Other user data
       // SharedPreferences.Editor otherData = getSharedPreferences("user", MODE_PRIVATE).edit();
        //User currentUser = userHelper.getUserByUsername(username);
       // otherData.putString("userID", otherUserID);
       // otherData.putString("username", otherUserName);
       // otherData.putBoolean("isLoggedIn", false);
       // otherData.apply();



        otherFnameTxt.setText(otherFname);
        otherProfileNameTxt.setText("@"+ otherProfileName);



        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    // getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, homefragment).commit();
                    //Toast.makeText(getApplicationContext(), "home click", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(getApplicationContext(), home.class);
                    homeIntent.putExtra("USERNAME", currUserName);
                  //  User currentUser = userHelper.getUserByUsername(currUserName);
                    // String currentUserID = currentUser.getUserID();
                   // Toast.makeText(this, currentUserID, Toast.LENGTH_SHORT).show();
                   // SharedPreferences.Editor currentData = getSharedPreferences("user", MODE_PRIVATE).edit();
                   // currentData.putString("userID", currentUserID);
                   // currentData.putString("username", currUserName);
                   // currentData.putBoolean("isLoggedIn", true);
                   // currentData.apply();


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