package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.models.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfilePage extends AppCompatActivity {
    Profile profileHelper;
    TextView otherFnameTxt, otherProfileNameTxt;
    ImageView subUnsubImageViewBtn, folllowUnfollowImageViewBtn;
    CircleImageView otherProfileCircleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile_page);

        otherFnameTxt = findViewById(R.id.otherBoldFName);
        otherProfileNameTxt = findViewById(R.id.otherProfileNameTag);
        profileHelper = new Profile(this);

        String otherUserID = getIntent().getStringExtra("userID");
        //Toast.makeText(this, otherUserID, Toast.LENGTH_SHORT).show();
        String otherProfileName = profileHelper.get(otherUserID).getProfileName();
        String otherFname = profileHelper.get(otherUserID).getFname();

        otherFnameTxt.setText(otherFname);
        otherProfileNameTxt.setText(otherProfileName);
    }
}