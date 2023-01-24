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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Notification;
import com.example.models.Post;
import com.example.models.Profile;

import java.util.ArrayList;

public class SavedPostActivity extends AppCompatActivity {

    ImageView backBtnImage;
    RecyclerView savedPostRecycler;
    RVAlikedPost rvAlikedPost;
    TextView tv_warning;
    Profile profile;
    SharedPreferences userData;

    ArrayList<String> postIDs;
    ArrayList<Post> savedPostData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_post);

        Notification notificationHelper = new Notification(getApplicationContext());
        tv_warning = findViewById(R.id.tv_warning);

        userData = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        profile = new Profile(getApplicationContext());
        profile = profile.get(userData.getString("userID",""));

        postIDs = notificationHelper.getLikeOrSavedPostsID(profile.getProfileID(), "saved");

        loadPost();
        checkForLikedPost();

        // Back button
        backBtnImage = findViewById(R.id.backBtnImage);
        backBtnImage.setOnClickListener(view -> finish());

        savedPostRecycler = findViewById(R.id.likePostRecyclerView);
        savedPostRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvAlikedPost = new RVAlikedPost(getApplicationContext(), savedPostData);

        savedPostRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        savedPostRecycler.setAdapter(rvAlikedPost);
    }

    // disable back bottom nav
    @Override
    public void onBackPressed() { }

    private void loadPost(){
        Post postHelper = new Post(getApplicationContext());
        for(String postID : postIDs)
        {
            if(postHelper.checkIfPostExists(postID)){
                Post post = postHelper.get(postID);
                savedPostData.add(post);
                Log.e("loadPost", "inner, ID = " + postID);
            }
            Log.e("loadPost", "outside, ID = " + postID);
        }
    }
    private void checkForLikedPost()
    {
        if (savedPostData.size() == 0){
            tv_warning.setVisibility(View.VISIBLE);
        } else {
            tv_warning.setVisibility(View.GONE);
        }
    }

}