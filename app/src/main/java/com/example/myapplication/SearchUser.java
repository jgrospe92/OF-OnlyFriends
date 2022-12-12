package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.models.Profile;
import com.example.models.ProfileAdapter;
import com.example.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;


public class SearchUser extends AppCompatActivity {
    EditText userFnameInput;
    RecyclerView searchUserRecyclerView;
    ArrayList<Profile> profileList;
    Profile profileHelper;
    User userHelper;
    SearchView searchFname;
    String userCurrID;
    String currUserName;

    DrawerLayout my_drawer_layout;
    NavigationView drawerNav;
    ActionBarDrawerToggle actionBarDrawerToggle;

    BottomNavigationView bottomNavigationView;

    homefragment homefragment = new homefragment();
    searchFragment searchFragment = new searchFragment();
    NotificationFragmet notificationFragmet = new NotificationFragmet();
    InboxFragment inboxFragment = new InboxFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        searchFname = findViewById(R.id.searchViewFname);
        searchFname.clearFocus(); //clears cursor inside its edit text


        searchUserRecyclerView = findViewById(R.id.searchedUserRecyclerView);
        profileHelper = new Profile(this);
        userHelper = new User(this);
        profileList = new ArrayList<>();

        //Getting the user's name and current image
        currUserName = getIntent().getStringExtra("USERNAME");
        userCurrID = userHelper.getUserByUsername(currUserName).getUserID();


        searchFname.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                profileList = new ArrayList<>();
                filterNames(newText);

                return true;
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                   // getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, homefragment).commit();
                    Toast.makeText(getApplicationContext(), "home click", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(getApplicationContext(), home.class);
                    homeIntent.putExtra("USERNAME", currUserName);
                    startActivity(homeIntent);
                    return true;

                case R.id.search:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, searchFragment).commit();
                    Toast.makeText(getApplicationContext(), "search click", Toast.LENGTH_SHORT).show();

                    break;

                case R.id.notif:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, notificationFragmet).commit();
                    Toast.makeText(getApplicationContext(), "notif click", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.inbox:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.parentFragment, inboxFragment).commit();
                    Toast.makeText(getApplicationContext(), "notif click", Toast.LENGTH_SHORT).show();
                    break;

            }
            return true;
        });

    }

    private void setAdapter(){

        ProfileAdapter profileAdapter = new ProfileAdapter( profileList,  this, userCurrID, currUserName);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        searchUserRecyclerView.setLayoutManager(layoutManager);
        searchUserRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchUserRecyclerView.setAdapter(profileAdapter);

    }

    private void filterNames(String text){

        Cursor cursor = profileHelper.displaySearchedUser(text.toUpperCase().trim());//called toUpperCase method in text & displaysearchedUser from Profile class  so that the input will be case insensitive.
        if(text.isEmpty()){
            profileList = new ArrayList<>();

        }else {
            while (cursor.moveToNext()) {
                profileList.add(new Profile(cursor.getString(1), cursor.getString(2), cursor.getString(7), cursor.getString(8)));
            }

        }
        setAdapter();

    }
}