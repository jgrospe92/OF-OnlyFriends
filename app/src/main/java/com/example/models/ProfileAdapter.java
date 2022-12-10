package com.example.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder>{

    private ArrayList<Profile> profileList;

    public ProfileAdapter(ArrayList<Profile> profileList) {
        this.profileList = profileList;
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
        String fName = profileList.get(position).getFname();
        String profileName = profileList.get(position).getProfileName();
        holder.searchedUserFnameTxt.setText(fName);
        holder.searchUserProfTagTxt.setText(profileName);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
}
