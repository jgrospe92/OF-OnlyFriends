package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {


    // SHARED PREF
    SharedPreferences userData;
    Profile profileHelper;
    Profile currentProfile;

    // View components
    CircleImageView profileCircleImage;
    TextView fullNameTextView;
    TextView profileNameTextView;
    TextView followingCountTextView;
    TextView followersCountTextView;
    TextView subscribedCountTextView;
    TextView subscriberCountTextView;
    TextView walletAmountTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Init helper class
        profileHelper = new Profile(this);
        // SHARED PREFERENCES
        userData = getSharedPreferences("user", MODE_PRIVATE);
        currentProfile = profileHelper.get(userData.getString("userID",""));
        loadViewComponents(currentProfile);

    }

    @Override
    protected void onResume() {
        super.onResume();
        currentProfile = profileHelper.get(userData.getString("userID",""));
        loadViewComponents(currentProfile);
    }

    public void loadViewComponents(Profile profile){
        profileCircleImage = findViewById(R.id.profileCircleImage);
        fullNameTextView = findViewById(R.id.fullNameTextView);
        profileNameTextView = findViewById(R.id.profileNameTextView);
        followingCountTextView = findViewById(R.id.followingCountTextView);
        followersCountTextView = findViewById(R.id.followersCountTextView);
        subscribedCountTextView = findViewById(R.id.subscribedCountTextView);
        subscriberCountTextView = findViewById(R.id.subscriberCountTextView);
        walletAmountTextView = findViewById(R.id.walletAmountTextView);

        loadImage(profile.getImageLink(), getApplicationContext(), profileCircleImage);
        String fullName = profile.getFname() + " " + profile.getLname();
        fullNameTextView.setText(fullName);
        profileNameTextView.setText("@" + profile.getProfileName());
        followersCountTextView.setText("0");
        followingCountTextView.setText("0");
        subscribedCountTextView.setText("0");
        subscriberCountTextView.setText("0");
        walletAmountTextView.setText("$" + profile.getWallet());
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

    public void btn_cancel(View view){
        finish();
    }

    public void editProfile(View view){
        Intent switchActivity = new Intent(getApplicationContext(), EditProfile.class);
        startActivity(switchActivity);
    }
}