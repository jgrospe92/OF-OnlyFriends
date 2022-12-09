package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Context;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import de.hdodenhof.circleimageview.CircleImageView;


public class home extends AppCompatActivity {

    CircleImageView profileImage;
    FloatingActionButton btn_fab;

    TextView welcomeText;

    DrawerLayout my_drawer_layout;
    NavigationView drawerNav;
    ActionBarDrawerToggle actionBarDrawerToggle;

    BottomNavigationView bottomNavigationView;

    homefragment homefragment = new homefragment();
    searchFragment searchFragment = new searchFragment();
    NotificationFragmet notificationFragmet = new NotificationFragmet();
    InboxFragment inboxFragment = new InboxFragment();

    Profile profileHelper;
    User userHelper;

    // DRAWER ITEMS
    TextView navFname, navProfileName, navSubscribedNum, navSubscriberNum,
            navFollowingNum, navFollowerNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userHelper = new User(this);
        profileHelper = new Profile(this);
        welcomeText = findViewById(R.id.welcomeText);
        // DRAWER STARTS
        my_drawer_layout = findViewById(R.id.my_drawer_layout);
        drawerNav = (NavigationView) findViewById(R.id.nav_drawer);

        drawerNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                my_drawer_layout.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.nav_profile:
                        Toast.makeText(getApplicationContext(), "Profile clicked", Toast.LENGTH_SHORT).show();break;
                    default:
                        return  true;
                }
                return true;
            }
        });
        // FLOATING ACTION BUTTON
        btn_fab = findViewById(R.id.btn_fab);
        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Fab click", Toast.LENGTH_SHORT).show();
            }
        });
        // DRAWER ENDS


        String username = getIntent().getStringExtra("USERNAME");
        User user = userHelper.getUserByUsername(username);
        Profile profile = profileHelper.get(user.getUserID());
        profileImage = findViewById(R.id.profileImage);
        loadImage(profile.getImageLink(), this, profileImage);
        initProfile(profile);
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

        drawerNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                my_drawer_layout.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.nav_profile:
                        //Toast.makeText(getApplicationContext(), "Profile clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), profilePage.class);
                        intent.putExtra("USERNAME", username);
                        startActivity(intent);

                        break;
                    default:
                        return  true;
                }
                return true;
            }
        });
        // DRAWER ENDS



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
            my_drawer_layout.openDrawer(GravityCompat.START);
        } else {
            my_drawer_layout.closeDrawer(GravityCompat.START);
        }
    }

    private String capitalizeFirstLetter(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void initProfile(Profile profile){
        // NAV DRAWER
        View headerView = drawerNav.getHeaderView(0);
        navFname = (TextView) headerView.findViewById(R.id.navFname);
        navProfileName = (TextView) headerView.findViewById(R.id.navProfileName);
        navFollowerNum = (TextView) headerView.findViewById(R.id.followerNum);
        navFollowingNum = (TextView) headerView.findViewById(R.id.followingNum);
        navSubscriberNum = (TextView) headerView.findViewById(R.id.subscriberNum);
        navSubscribedNum = (TextView) headerView.findViewById(R.id.subscribedNum);

        String firstName = capitalizeFirstLetter(profile.getFname());
        String profileName = "@" + profile.getProfileName();
        String followerNum = "0";
        String followingNum = "0";
        String subscriberNum = "0";
        String subscribedNum = "0";

        navFname.setText(firstName);
        navProfileName.setText(profileName);
        navFollowerNum.setText(followerNum);
        navFollowingNum.setText(followingNum);
        navSubscriberNum.setText(subscriberNum);
        navSubscribedNum.setText(subscribedNum);
    }
}