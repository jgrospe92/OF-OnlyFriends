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
import android.widget.Button;
import android.widget.ImageView;

import com.example.models.Notification;
import com.example.models.Post;
import com.example.models.Profile;

import java.util.ArrayList;

public class LikedPostActivity extends AppCompatActivity {


    ImageView backBtnImage;
    RecyclerView likePostRecyclerView;
    RVAlikedPost likePostAdapter;
    Profile profile;
    SharedPreferences userData;

    ArrayList<String> postIDs;
    ArrayList<Post> likePostsData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_post);
        Notification notificationHelper = new Notification(getApplicationContext());

        userData = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        profile = new Profile(getApplicationContext());
        profile = profile.get(userData.getString("userID",""));

        backBtnImage = findViewById(R.id.backBtnImage);
        backBtnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        postIDs = notificationHelper.getLikePostsID(profile.getProfileID());
        loadPost();

        likePostRecyclerView = findViewById(R.id.likePostRecyclerView);
        likePostRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        likePostAdapter = new RVAlikedPost(getApplicationContext(), likePostsData);

        likePostRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        likePostRecyclerView.setAdapter(likePostAdapter);
    }

    // DISABLED BACK BUTTON
    @Override
    public void onBackPressed() { }

    private void loadPost(){
        Post postHelper = new Post(getApplicationContext());
        for(String postID : postIDs)
        {
            Post post = postHelper.get(postID);
            likePostsData.add(post);
        }
    }
}