package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.models.Helper;
import com.example.models.Profile;

public class EditProfile extends AppCompatActivity {


    // SHARED PREF
    SharedPreferences userData;
    Profile profileHelper;
    Profile currentProfile;

    EditText et_profileName, et_fname, et_lname, et_imageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Init helper class
        profileHelper = new Profile(this);
        // SHARED PREFERENCES
        userData = getSharedPreferences("user", MODE_PRIVATE);
        currentProfile = profileHelper.get(userData.getString("userID",""));
        loadViewComponent();

    }

    public void cancelBTN(View veiw){
        finish();
    }

    public void loadViewComponent(){
        et_profileName = findViewById(R.id.et_profileName);
        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_imageLink = findViewById(R.id.et_imageLink);

        // FETCH DEFAULT PROFILE DATA
        et_profileName.setText(currentProfile.getProfileName());
        et_fname.setText(currentProfile.getFname());
        et_lname.setText(currentProfile.getLname());
    }

    public void UpdateProfile(View view){
        // VALIDATION
        boolean validName = Helper.checkInput(et_profileName, "Pleaser enter your profile name");
        boolean validFname = Helper.checkInput(et_fname, "Pleaser enter your first name");
        boolean validLname = Helper.checkInput(et_lname, "Pleaser enter your last name");

        if (!validName || !validFname || !validLname){return;}

        if (Helper.checkInputImage(et_imageLink)){

            currentProfile.setImageLink(et_imageLink.getText().toString());
        }
        // UPDATE PROFILE DATA
        currentProfile.setProfileName(et_profileName.getText().toString());
        currentProfile.setFname(et_fname.getText().toString());
        currentProfile.setLname(et_lname.getText().toString());
        if(profileHelper.update(currentProfile)){
            finish();
            Toast.makeText(getApplicationContext(), "Profile updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Update failed", Toast.LENGTH_SHORT).show();
        }
    }
}