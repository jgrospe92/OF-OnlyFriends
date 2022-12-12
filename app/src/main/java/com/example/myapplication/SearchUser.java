package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.models.Profile;
import com.example.models.ProfileAdapter;
import com.example.models.User;

import java.util.ArrayList;
import java.util.Locale;


public class SearchUser extends AppCompatActivity {
    EditText userFnameInput;
    RecyclerView searchUserRecyclerView;
    ArrayList<Profile> profileList, filteredNames;
    Profile profileHelper;
    User userHelper;
    SearchView searchFname;
    String userCurrID;
    String currUserName;
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




       // userFnameInput = findViewById(R.id.searchUserEditText);


        /*
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchInput = userFnameInput.getText().toString();
                Profile fetchedProfile = profileHelper.fetchFirstName(searchInput);

                profileList = new ArrayList<>();

                if(fetchedProfile != null) {
                    String fetchedFirstName = fetchedProfile.getFname();
                        if(searchInput.equals(fetchedFirstName)) {
                            Toast.makeText(SearchUser.this, "It exist" + fetchedFirstName, Toast.LENGTH_SHORT).show();

                          Cursor cursor = profileHelper.displaySearchedUser(searchInput);

                            while(cursor.moveToNext()) {

                                profileList.add(new Profile(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getString(7)));
                            }
                            setAdapter();

                        }
                }else{
                    Toast.makeText(SearchUser.this, "It does not exist" + searchInput, Toast.LENGTH_SHORT).show();
                }
            }
        });
*/






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




       // for(Profile profile : profileList){
          // if(profilelist.getFname().toLowerCase().contains(text.toLowerCase())){
               //filteredNames.add(new Profile(profile.getProfileID(), profile.getProfileName(),profile.getFname(), profile.getImageLink()));
               //filteredNames.add(profile);
               //Toast.makeText(this,  profile.getFname().toLowerCase(), Toast.LENGTH_SHORT).show();


          // }else{
               //profileList = new ArrayList<>();
               //setAdapter();
          // }

           //profile.getFname().toLowerCase()
       //}
     //  if(profileList.isEmpty()){
       //    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
      // }else{
       //     profileAdapter.setFilteredNames(profileList);
      // }
   }
}