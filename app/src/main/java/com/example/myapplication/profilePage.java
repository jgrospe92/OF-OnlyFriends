package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Profile;
import com.example.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
public class profilePage extends AppCompatActivity {
    Button searchButton;
    User userHelper;
    Profile profileHelper;
    CircleImageView profileImg;
    TextView fnameTxt, profileNameTagTxt, profDescriptionTxt, followingCountTxt, followerCountTxt, linkTxt, joinedDateTxt;
    BottomNavigationView bottomNavigationView;
    String currUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        userHelper = new User(this);
        profileHelper = new Profile(this);

        fnameTxt = findViewById(R.id.boldFName);
        profileNameTagTxt = findViewById(R.id.profileNameTag);
        profDescriptionTxt = findViewById(R.id.profileDescriptionTextView);
        followingCountTxt = findViewById(R.id.followingCountTextView);
        followerCountTxt = findViewById(R.id.followerCountTextView);



        //Getting the user's name and current image
        currUserName = getIntent().getStringExtra("USERNAME");
        User user = userHelper.getUserByUsername(currUserName);
        Profile profile = profileHelper.get(user.getUserID());
        profileImg = findViewById(R.id.profileImg);
        loadImage(profile.getImageLink(), this, profileImg);



        //initProfile(profile);
        fnameTxt.setText(profile.getFname());
        profileNameTagTxt.setText("@"+ profile.getProfileName());
        //followingCountTxt.setText(profile.getFollowingCount());
        //followerCountTxt.setText(profile.getFollowerCount());
        //welcomeText.setText("Welcome " + profile.getFname().toLowerCase());

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

    public void loadImage(String imageLink, Context context, CircleImageView image) {
        GlideUrl setImage = new GlideUrl(imageLink, new LazyHeaders.Builder()
                .addHeader("User-Agent", "image")
                .build());

        Glide.with(context)
                .asBitmap()
                .load(setImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(image);
    }

    public void editProfile(View view) {
        Intent i = new Intent(getApplicationContext(), EditProfile.class);
        i.putExtra("USERNAME", currUserName);
        startActivity(i);
    }
}