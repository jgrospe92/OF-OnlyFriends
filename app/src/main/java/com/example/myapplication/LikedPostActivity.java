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
import android.widget.TextView;

import com.example.models.Notification;
import com.example.models.Post;
import com.example.models.Profile;

import java.util.ArrayList;

public class LikedPostActivity extends AppCompatActivity {


    ImageView backBtnImage;
    RecyclerView likePostRecyclerView;
    RVAlikedPost likePostAdapter;
    TextView tv_warning;
    Profile profile;
    SharedPreferences userData;

    ArrayList<String> postIDs;
    ArrayList<Post> likePostsData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_post);

        Notification notificationHelper = new Notification(getApplicationContext());
        tv_warning = findViewById(R.id.tv_warning);

        userData = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        profile = new Profile(getApplicationContext());
        profile = profile.get(userData.getString("userID",""));

        backBtnImage = findViewById(R.id.backBtnImage);
        backBtnImage.setOnClickListener(view -> finish());

        postIDs = notificationHelper.getLikeOrSavedPostsID(profile.getProfileID(), "liked");
        loadPost();
        checkForLikedPost();

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
            if(postHelper.checkIfPostExists(postID)){
                Post post = postHelper.get(postID);
                likePostsData.add(post);
                Log.e("loadPost", "inner, ID = " + postID);
            }
            Log.e("loadPost", "outside, ID = " + postID);
        }
    }
    private void checkForLikedPost()
    {
        if (likePostsData.size() == 0){
            tv_warning.setVisibility(View.VISIBLE);
        } else {
            tv_warning.setVisibility(View.GONE);
        }
    }
}