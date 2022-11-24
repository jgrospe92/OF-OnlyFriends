package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    homefragment homefragment = new homefragment();
    searchFragment searchFragment = new searchFragment();
    NotificationFragmet notificationFragmet = new NotificationFragmet();
    InboxFragment inboxFragment = new InboxFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


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
}