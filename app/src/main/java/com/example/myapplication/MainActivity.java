package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.models.Helper;
import com.example.models.User;



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

                boolean validUsername = Helper.checkInput(eText_username, "Please enter your username");
                boolean validPassword  = Helper.checkInput(eText_password, "Please enter your password");

                if (!validUsername || !validPassword){return;}

                if (password.equals(pass)) {
                    Intent intent  = new Intent(getApplicationContext(), home.class);
                    SharedPreferences.Editor data = getSharedPreferences("user", MODE_PRIVATE).edit();
                    User currentUser = userHelper.getUserByUsername(username);
                    data.putString("userID", currentUser.getUserID());
                    data.putString("username", username);
                    data.putBoolean("isLoggedIn", true);
                    data.apply();
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // ON RESUME CLEAR INPUTS
    @Override
    protected void onResume() {
        super.onResume();
        eText_username.setText("");
        eText_password.setText("");
    }

    public void signUP(View view ) {

        Intent intent = new Intent(this.getApplicationContext(), register.class);
        startActivity(intent);
    }
}