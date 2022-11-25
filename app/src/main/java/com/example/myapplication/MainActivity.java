package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button signIn;
    EditText eText_username, eText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eText_username = findViewById(R.id.eText_username);
        eText_password = findViewById(R.id.eText_password);


        signIn = findViewById(R.id.btn_signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
            }
        });

    }

    public void signUP(View view ) {
        HashMap<String, String> data = new HashMap<>();
        Intent intent = new Intent(this.getApplicationContext(), register.class);
        Toast.makeText(getApplicationContext(), "sigh up" , Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}