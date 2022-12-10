package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.example.models.Profile;
import com.example.models.ProfileAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchUser extends AppCompatActivity {
    EditText userFnameInput;
    RecyclerView searchUserRecyclerVIew;
    ArrayList<Profile> profileList;
    Profile profileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        userFnameInput = findViewById(R.id.searchUserEditText);
        searchUserRecyclerVIew = findViewById(R.id.searchedUserRecyclerView);
        profileHelper = new Profile(this);
        profileList = new ArrayList<>();





    }

    private void setAdapter(){
        ProfileAdapter profileAdapter = new ProfileAdapter(profileList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        searchUserRecyclerVIew.setLayoutManager(layoutManager);
        searchUserRecyclerVIew.setItemAnimator(new DefaultItemAnimator());
        searchUserRecyclerVIew.setAdapter(profileAdapter);

    }

    private void getUserProfile(){

    }
}