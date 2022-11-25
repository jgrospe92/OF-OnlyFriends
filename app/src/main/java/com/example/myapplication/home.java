package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Profile;
import com.example.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import de.hdodenhof.circleimageview.CircleImageView;


public class home extends AppCompatActivity {

    CircleImageView profileImage;

    BottomNavigationView bottomNavigationView;

    homefragment homefragment = new homefragment();
    searchFragment searchFragment = new searchFragment();
    NotificationFragmet notificationFragmet = new NotificationFragmet();
    InboxFragment inboxFragment = new InboxFragment();

    Profile profileHelper;
    User userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userHelper = new User(this);
        profileHelper = new Profile(this);

        String username = getIntent().getStringExtra("USERNAME");
        User user = userHelper.getUserByUsername(username);
        Profile profile = profileHelper.get(user.getUserID());
        Log.e("PROFILE DEBUG", profile.toString());

        profileImage = findViewById(R.id.profileImage);

        loadImage(profile.getImageLink(), this, profileImage);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, homefragment).commit();
                    Toast.makeText(getApplicationContext(), "home click", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, searchFragment).commit();
                    Toast.makeText(getApplicationContext(), "search click", Toast.LENGTH_SHORT).show();

                    break;

                case R.id.notif:
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, notificationFragmet).commit();
                    Toast.makeText(getApplicationContext(), "notif click", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.inbox:
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, inboxFragment).commit();
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
}