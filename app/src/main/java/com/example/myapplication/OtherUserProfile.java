package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherUserProfile extends AppCompatActivity {

    CircleImageView profileCircleImage;
    TextView fullNameTextView, profileNameTextView;
    Profile profile, profileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        profileHelper = new Profile(getApplicationContext());

        profileCircleImage = findViewById(R.id.profileCircleImage);
        fullNameTextView = findViewById(R.id.fullNameTextView);
        profileNameTextView = findViewById(R.id.profileNameTextView);

        String profileID = getIntent().getStringExtra("PROFILE_ID");
        profile = profileHelper.getProfileByID(profileID);

        // initialize the profile
        loadProfileInfo();
        loadImage(profile.getImageLink(), getApplicationContext(), profileCircleImage);



    }

    public void btn_cancel(View view){
        finish();
    }

    private void loadImage(String imageLink, Context context, CircleImageView image) {
        GlideUrl setImage = new GlideUrl(imageLink, new LazyHeaders.Builder()
                .addHeader("User-Agent", "image")
                .build());

        Glide.with(context)
                .asBitmap()
                .load(setImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .override(250,250)
                .centerCrop()
                .into(image);
    }

    private void loadProfileInfo(){
        fullNameTextView.setText(profile.getFname() + " " + profile.getLname());
        profileNameTextView.setText(profile.getProfileName());
    }

}