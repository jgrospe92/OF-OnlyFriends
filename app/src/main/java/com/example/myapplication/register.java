package com.example.myapplication;
import com.example.models.Helper;
import  com.example.models.dbConnector;
import com.example.models.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class register extends AppCompatActivity {

    dbConnector con;


    EditText et_username, et_email, et_password, et_confirmPassword;
    Button btn_save, btn_cancel;


    String username = "";
    String password = "";
    String confirmPass = "";
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        con = dbConnector.getInstance(this);

        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);

        btn_save = findViewById(R.id.btn_save);
        btn_cancel =findViewById(R.id.btn_edit);


    }

    public void saveUser(View view ) {

        username = et_username.getText().toString();
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        confirmPass = et_confirmPassword.getText().toString();

        boolean validUsername =  Helper.checkInput(et_username, "Please enter your username");
        boolean validPass = Helper.checkInput(et_password, "Please enter your password");
        boolean validConPass = Helper.checkInput(et_confirmPassword, "Please re-enter your password");
        boolean validEmail = Helper.checkValidEmail(et_email, "Please enter a valid email");

        if (!validUsername || !validPass || !validConPass || !validEmail){return;}

        if ( !password.equals(confirmPass)) {
            Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username, password, email, this);

        if (user.insert(user)) {
            Toast.makeText(getApplicationContext(), "User added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, createProfile.class);
            finish();
            intent.putExtra("USER_ID", String.valueOf(user.getLastInsert()));
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "ERROR!: " + user.getUsername() + " already exists!", Toast.LENGTH_SHORT).show();
        }

    }

    public void cancelUser(View view){
        finish();
    }

}