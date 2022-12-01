package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Post;
import com.example.models.Profile;
import com.example.models.User;
import com.example.models.dbConnector;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class createPost extends AppCompatActivity {
TextView postTxt;
Button postBtn, cancelBtn;
    CircleImageView profileImage;
Post postHelper;
User userHelper;
Profile profileHelper;
dbConnector db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        postHelper = new Post(this);
        userHelper = new User(this);
        postTxt = findViewById(R.id.textView5);
        postBtn = findViewById(R.id.button2);
        cancelBtn = findViewById(R.id.button);

        String username = getIntent().getStringExtra("USERNAME");
        User user = userHelper.getUserByUsername(username);
        Profile profile = profileHelper.get(user.getUserID());
        profileImage = findViewById(R.id.imageView4);
        loadImage(profile.getImageLink(), this, profileImage);
        //initProfile(profile);
        //welcomeText.setText("Welcome " + profile.getFname().toLowerCase());



     //TO DO: once postBtn is clicked, call insert post method.
     postBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v){
             String caption = postTxt.getText().toString();

             Date calTime = Calendar.getInstance().getTime();
             SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-DD-yy", Locale.getDefault());
             String formattedDate = simpleDateFormat.format(calTime);
             Post post = new Post(caption, formattedDate, "0", "0");





         }
     });
    }

    public void loadImage(String imageLink, Context context, CircleImageView image) {
        GlideUrl setImage = new GlideUrl(imageLink, new LazyHeaders.Builder()
                .addHeader("User-Agent", "image")
                .build());

        Glide.with(context)
                .asBitmap()
                .load(setImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(image);
    }
}