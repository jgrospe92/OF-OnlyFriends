package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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


public class home extends AppCompatActivity {

    CircleImageView profileImage;

    TextView welcomeText;

    DrawerLayout my_drawer_layout;
    ActionBarDrawerToggle actionBarDrawerToggle;

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

        welcomeText = findViewById(R.id.welcomeText);

        my_drawer_layout =findViewById(R.id.my_drawer_layout);


        String username = getIntent().getStringExtra("USERNAME");
        User user = userHelper.getUserByUsername(username);
        Profile profile = profileHelper.get(user.getUserID());

        profileImage = findViewById(R.id.profileImage);

        loadImage(profile.getImageLink(), this, profileImage);
        welcomeText.setText("Welcome " + profile.getFname().toLowerCase());

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

        // DRAWER listener

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void openDrawer(View view) {
        if (!my_drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            my_drawer_layout.openDrawer(Gravity.LEFT);
        } else {
            my_drawer_layout.closeDrawer(Gravity.LEFT);
        }
    }
}