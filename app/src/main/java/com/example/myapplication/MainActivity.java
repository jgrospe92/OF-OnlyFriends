package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.models.User;
import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button signIn;
    EditText eText_username, eText_password;

    User userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userHelper = new User(this);
        eText_username = findViewById(R.id.eText_username);
        eText_password = findViewById(R.id.eText_password);


        signIn = findViewById(R.id.btn_signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = eText_username.getText().toString();
                String password = eText_password.getText().toString();
                String pass = userHelper.verifyPassword(username);
                if (password.equals(pass)) {
                    Intent intent  = new Intent(getApplicationContext(), home.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void signUP(View view ) {
        HashMap<String, String> data = new HashMap<>();
        Intent intent = new Intent(this.getApplicationContext(), register.class);
        startActivity(intent);
    }
}