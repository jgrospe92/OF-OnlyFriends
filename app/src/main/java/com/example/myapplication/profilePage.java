package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Profile;
import com.example.models.User;

import de.hdodenhof.circleimageview.CircleImageView;
public class profilePage extends AppCompatActivity {

    User userHelper;
    Profile profileHelper;
    CircleImageView profileImg;
    TextView fnameTxt, profileNameTagTxt, profDescriptionTxt, followingCountTxt, followerCountTxt,linkTxt, joinedDateTxt;
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
        String username = getIntent().getStringExtra("USERNAME");
        User user = userHelper.getUserByUsername(username);
        Profile profile = profileHelper.get(user.getUserID());
        profileImg = findViewById(R.id.profileImg);
        loadImage(profile.getImageLink(), this, profileImg);

        //initProfile(profile);
        fnameTxt.setText(profile.getFname());
        profileNameTagTxt.setText("@"+profile.getProfileName());
        followingCountTxt.setText(profile.getFollowingCount());
        followerCountTxt.setText(profile.getFollowerCount());
        //welcomeText.setText("Welcome " + profile.getFname().toLowerCase());

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
        i.putExtra("USERNAME", username);
        startActivity(i);
    }
}