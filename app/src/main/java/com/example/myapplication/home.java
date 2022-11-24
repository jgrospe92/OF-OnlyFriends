package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    homefragment homefragment = new homefragment();
    searchFragment searchFragment = new searchFragment();

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
                    Toast.makeText(getApplicationContext(), "notif click", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.inbox:
                    Toast.makeText(getApplicationContext(), "notif click", Toast.LENGTH_SHORT).show();
                    break;

            }
            return true;
        });
    }
}