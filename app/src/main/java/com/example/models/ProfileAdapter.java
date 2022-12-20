package com.example.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.OtherProfilePage;
import com.example.myapplication.R;
import com.example.myapplication.SearchUser;
import com.example.myapplication.profilePage;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder>{

    private ArrayList<Profile> profileList;
    private Context mContext;
    private String currentUserID;
    private String currentUserName;
    public ProfileAdapter(ArrayList<Profile> profileList) {
        this.profileList = profileList;
    }

    public ProfileAdapter(ArrayList<Profile> profileList, Context mContext) {
        this.profileList = profileList;
        this.mContext = mContext;
    }

    public ProfileAdapter(ArrayList<Profile> profileList, Context mContext, String currentUserID, String currentUserName) {
        this.profileList = profileList;
        this.mContext = mContext;
        this.currentUserID = currentUserID;
        this.currentUserName = currentUserName;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView searchedUserFnameTxt, searchUserProfTagTxt;
        private CircleImageView searchedUserCircleImageView;

        public MyViewHolder(final View view) {
            super(view);

            searchedUserFnameTxt = view.findViewById(R.id.searchedUserFnameTextView);
            searchUserProfTagTxt = view.findViewById(R.id.searchUserProfTagTextView);
            searchedUserCircleImageView = view.findViewById(R.id.searchedUserCIrcleImageView);


        }
    }



    @NonNull
    @Override
    public ProfileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_recyclerview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.MyViewHolder holder, int position) {
/*      TODO: ImageLink needs to be implemented correctly.
        Glide.with(mContext)
                .asBitmap()
                .load(profileList.get(position).getImageLink())
                .into(holder.searchedUserCircleImageView);
*/


        String fName = profileList.get(position).getFname();
        String profileName = profileList.get(position).getProfileName();
        //String profileID = profileList.get(position).getProfileID();
        String userID = profileList.get(position).getUserID();
        holder.searchedUserFnameTxt.setText(fName);
        holder.searchUserProfTagTxt.setText(profileName);

        /*
        TODO: Link the current user to the other user in order to insert subcribe and follow
         */
        holder.searchedUserCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if(currentUserID.equals(userID)) {
                    i = new Intent(mContext, profilePage.class);
                    i.putExtra("USERNAME", currentUserName);
                    //Toast.makeText(mContext, userID, Toast.LENGTH_SHORT).show();
                }else{
                    i = new Intent(mContext, OtherProfilePage.class);
                    i.putExtra("userID", userID);
                }
                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
}
