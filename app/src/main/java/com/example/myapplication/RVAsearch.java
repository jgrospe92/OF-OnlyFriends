package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Profile;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RVAsearch extends  RecyclerView.Adapter<RVAsearch.ViewHolder>{

    ArrayList<Profile> profiles;
    private LayoutInflater mInflater;

    // Constructor

    public RVAsearch(Context context, ArrayList<Profile> profiles) {
        this.profiles = profiles;
        this.mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVAsearch.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Profile profile = new Profile(mInflater.getContext());
        profile = profile.getProfileByID(profiles.get(position).getProfileID());


        if (getItemCount() >= 0) {
            // LOAD PROFILE IMAGE
            GlideUrl profileImage = new GlideUrl(profile.getImageLink(), new LazyHeaders.Builder()
                    .addHeader("User-Agent", "profileImage")
                    .build());

            Glide.with(mInflater.getContext())
                    .asBitmap()
                    .load(profileImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(holder.profileCircleImageSearch);

            holder.profileNameTextViewSearch.setText("@"+profile.getProfileName());
            holder.firstNameTextView.setText(profile.getFname());
        }

    }

    @Override
    public int getItemCount() {
        if (profiles != null){
            return profiles.size();
        }
        return  -1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        CircleImageView profileCircleImageSearch;
        TextView firstNameTextView;
        TextView profileNameTextViewSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileCircleImageSearch = itemView.findViewById(R.id.profileCircleImageSearch);
            firstNameTextView = itemView.findViewById(R.id.firstNameTextView);
            profileNameTextViewSearch = itemView.findViewById(R.id.profileNameTextViewSearch);

        }

        @Override
        public void onClick(View view) {


        }
    }
}
