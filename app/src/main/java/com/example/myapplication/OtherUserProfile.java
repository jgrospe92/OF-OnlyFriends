package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import com.example.models.Post;
import com.example.models.Profile;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherUserProfile extends AppCompatActivity {

    CircleImageView profileCircleImage;
    RVAhome recycleViewAdapterHome;
    TextView fullNameTextView, profileNameTextView, noPostMessage;
    Profile profile, profileHelper;
    Post postHelper;
    ArrayList<Post> posts;

    RecyclerView profile_recyclerView;

    // SHARED PREF
    SharedPreferences userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        profileHelper = new Profile(getApplicationContext());
        postHelper = new Post(getApplicationContext());

        profileCircleImage = findViewById(R.id.profileCircleImage);
        fullNameTextView = findViewById(R.id.fullNameTextView);
        profileNameTextView = findViewById(R.id.profileNameTextView);
        noPostMessage = findViewById(R.id.noPostMessage);
        profile_recyclerView = findViewById(R.id.profile_recyclerView);
        profile_recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // SHARED PREFERENCES
        userData = getSharedPreferences("user", MODE_PRIVATE);

        String profileID = getIntent().getStringExtra("PROFILE_ID");
        profile = profileHelper.getProfileByID(profileID);

        // initialize the profile
        loadProfileInfo();
        loadImage(profile.getImageLink(), getApplicationContext(), profileCircleImage);
        loadPost();

        // load post to the recycler view
        recycleViewAdapterHome = new RVAhome(this, posts, userData, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                recycleViewAdapterHome.notifyDataSetChanged();
            }
            @Override
            public void onLongClicked(int position) {
            }
        });
        profile_recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recycleViewAdapterHome.notifyDataSetChanged();
        profile_recyclerView.setAdapter(recycleViewAdapterHome);
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
        profileNameTextView.setText("@"+profile.getProfileName());
    }

    private void loadPost(){
        posts = postHelper.getAllPostByProfileId(profile.getProfileID());
        if (posts.size() == 0) {
            noPostMessage.setVisibility(View.VISIBLE);
        } else {
            noPostMessage.setVisibility(View.GONE);
        }
    }
}