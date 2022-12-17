package com.example.myapplication;

import android.content.Context;
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
import com.example.models.Notification;
import com.example.models.Profile;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RVAnotification extends RecyclerView.Adapter<RVAnotification.ViewHolder>{

    ArrayList<Notification> notifications;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    public RVAnotification(Context context, ArrayList<Notification> notifications) {
        this.notifications = notifications;
        this.mInflater = LayoutInflater.from(context);;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVAnotification.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Profile profile = new Profile(mInflater.getContext());
        // Profile of the user  who like or saved your post
        profile = profile.getProfileByID(notifications.get(position).getCurrentProfileId());


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
                    .into(holder.profileCircleImageNotif);

            String profileName = profile.getProfileName();
            String userAction = notifications.get(position).getDescription();
            holder.firstNameTextView.setText(profileName);
            String message = userAction + " your post";
            holder.messageNotif.setText(message);

        }
    }

    @Override
    public int getItemCount() {
        if (notifications != null){
            return notifications.size();
        }
        return  -1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        CircleImageView profileCircleImageNotif;
        TextView firstNameTextView;
        TextView messageNotif;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileCircleImageNotif = itemView.findViewById(R.id.profileCircleImageNotif);
            firstNameTextView = itemView.findViewById(R.id.firstNameTextView);
            messageNotif = itemView.findViewById(R.id.messageNotif);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Notification getItem(int id) {
        return notifications.get(id);
    }
    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
