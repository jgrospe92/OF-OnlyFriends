package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Post;
import com.example.models.Profile;
import com.example.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class home extends AppCompatActivity {

    CircleImageView profileImage;
    FloatingActionButton btn_fab;

    DrawerLayout my_drawer_layout;
    NavigationView drawerNav;
    ActionBarDrawerToggle actionBarDrawerToggle;

    BottomNavigationView bottomNavigationView;

    homefragment homefragment;
    searchFragment searchFragment = new searchFragment();
    NotificationFragmet notificationFragmet;
    InboxFragment inboxFragment = new InboxFragment();

    Profile profileHelper;
    User userHelper;
    Post postHelper;

    Profile currentProfile;

    // DRAWER ITEMS
    TextView navFname, navProfileName, navSubscribedNum, navSubscriberNum,
            navFollowingNum, navFollowerNum, noPostTextView;

    // SHARED PREF
    SharedPreferences userData;

    // POST DIALOG
    boolean hasImage;
    ArrayList<Post> posts;
    ArrayList<Post> checkPost = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userHelper = new User(this);
        profileHelper = new Profile(this);
        // SHARED PREFERENCES
        userData = getSharedPreferences("user", MODE_PRIVATE);
        // HIDE NO POST WARNING
        noPostTextView = findViewById(R.id.noPostTextView);
        noPostTextView.setVisibility(View.GONE);
        // POST DIALOG INIT
        postHelper = new Post(getApplicationContext());
        posts = postHelper.getAllPosts();
        checkIfPostExists();
        homefragment = new homefragment(posts, noPostTextView);
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
                        switchActivity(UserProfile.class);
                        break;
                    case R.id.nav_likes:
                        Toast.makeText(getApplicationContext(), "likes clicked", Toast.LENGTH_SHORT).show();break;
                    case  R.id.nav_secrets:
                        Toast.makeText(getApplicationContext(), "Secrets clicked", Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_saved:
                        Toast.makeText(getApplicationContext(), "Saved Post clicked", Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_credit:
                        Toast.makeText(getApplicationContext(), "Credit  clicked", Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_settings:
                        Toast.makeText(getApplicationContext(), "Settings  clicked", Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_help:
                        switchActivity(Helpmenu.class);
                        break;
                    case R.id.nav_logout:
                        userData.edit().putString("username", "");
                        userData.edit().putString("userID", "");
                        userData.edit().putBoolean("isLoggedIn",false);
                        finish();break;
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
                showPostDialog();
            }
        });
        // DRAWER ENDS

        User user = userHelper.get(userData.getString("userID",""));
        currentProfile = profileHelper.get(user.getUserID());
        profileImage = findViewById(R.id.profileImage);
        loadImage(currentProfile.getImageLink(), this, profileImage);
        initProfile(currentProfile);
        // Initialize notification
        notificationFragmet = new NotificationFragmet(currentProfile);

        getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, homefragment).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, homefragment).commit();
                    checkIfPostExists();
                    return true;

                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, searchFragment).commit();
                    break;

                case R.id.notif:
                    noPostTextView.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, notificationFragmet).commit();
                    break;

                case R.id.inbox:
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, inboxFragment).commit();
                    Toast.makeText(getApplicationContext(), "inbox click", Toast.LENGTH_SHORT).show();
                    break;

            }
            return true;
        });

        // DRAWER listener
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentProfile = profileHelper.get(userData.getString("userID",""));
        profileImage = findViewById(R.id.profileImage);
        loadImage(currentProfile.getImageLink(), this, profileImage);
        reloadNavProfile(currentProfile);
        reloadPosts();
        checkIfPostExists();
    }

    public void checkIfPostExists(){
        checkPost = postHelper.getAllPosts();
        if (checkPost.isEmpty()) {
            // IF NO POST, SET NO POST WARNING TO TRUE
            noPostTextView.setVisibility(View.VISIBLE);
        } else {
            noPostTextView.setVisibility(View.GONE);
        }
        checkPost = null;
    }

    public void reloadPosts(){
        posts.clear();
        posts = postHelper.getAllPosts();
        homefragment = new homefragment(posts, noPostTextView);
        getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, homefragment).commit();

    }

    // METHOD TO SWITCH TO A NEW ACTIVITY
    private void switchActivity(Class activityClass){
        Intent switchActivity = new Intent(getApplicationContext(), activityClass);
        startActivity(switchActivity);
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

    public void reloadNavProfile(Profile profile){
        View headerView = drawerNav.getHeaderView(0);
        navFname = (TextView) headerView.findViewById(R.id.navFname);
        navProfileName = (TextView) headerView.findViewById(R.id.navProfileName);
        navFollowerNum = (TextView) headerView.findViewById(R.id.followerNum);
        navFollowingNum = (TextView) headerView.findViewById(R.id.followingNum);
        navSubscriberNum = (TextView) headerView.findViewById(R.id.subscriberNum);
        navSubscribedNum = (TextView) headerView.findViewById(R.id.subscribedNum);

        String firstName = capitalizeFirstLetter(profile.getFname());
        String profileName = "@" + profile.getProfileName();
        // TODO: a profile method to retrieve these data
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



    // METHOD TO OPEN DIALOG
    private void showPostDialog(){
        Dialog dialog =  new Dialog(home.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.addpost);

        // Initializes the views
        EditText caption = dialog.findViewById(R.id.captionPostEditText);
        CheckBox checkBox = dialog.findViewById(R.id.postCheckbox);
        EditText postImageURL = dialog.findViewById(R.id.postImageURL);
        Button postButton = dialog.findViewById(R.id.postButton);
        Button cancelPostButton = dialog.findViewById(R.id.cancelPostButton);


        // WHEN POST IS CLICKED
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageURL = "";
                if (TextUtils.isEmpty(caption.getText().toString())){
                    caption.setError("you forgot to write something");
                    return;
                }
                String postCaption = caption.getText().toString();
                if(hasImage){
                     imageURL = postImageURL.getText().toString();
                }

                int likes = 0;
                int favorites = 0;
                Post post = new Post(getApplicationContext());

                String profileID = currentProfile.getProfileID();
                String datePosted = getCurrentDate();
                post.setCaption(postCaption);
                post.setDatePosted(datePosted);
                post.setLikes(likes);
                post.setImageURL(imageURL);
                post.setFavorites(favorites);
                post.setProfileID(profileID);

                if(post.insert(post, "")) {
                    Toast.makeText(getApplicationContext(), "Post added", Toast.LENGTH_SHORT).show();
                    reloadPosts();
                    checkIfPostExists();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "FAILED TO  CREATE POST", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // WHEN CANCEL IS CLICKED
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    postImageURL.setVisibility(View.VISIBLE);
                    hasImage = true;

                } else {
                    postImageURL.setVisibility(View.GONE);
                    hasImage = false;
                }
            }
        });

        cancelPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    // GET CURRENT DATE
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}