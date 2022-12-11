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
import com.example.models.Comment;
import com.example.models.Profile;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RVAcomment extends  RecyclerView.Adapter<RVAcomment.VIewHolder>{

    ArrayList<Comment> comments;
    private LayoutInflater mInflater;

    public RVAcomment(Context context, ArrayList<Comment> comments) {
        this.comments = comments;
        this.mInflater =  LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.comment_layout, parent, false);
        return new VIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VIewHolder holder, int position) {
        Profile profile = new Profile(mInflater.getContext());

        if(getItemCount() >= 0){
            Log.e("Profile is", String.valueOf(position));

            // SET profle Image
//            GlideUrl profileImage = new GlideUrl(profile.getImageLink(), new LazyHeaders.Builder()
//                    .addHeader("User-Agent", "profileImage")
//                    .build());
//            Glide.with(mInflater.getContext())
//                    .asBitmap()
//                    .load(profileImage)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .error(R.drawable.ic_launcher_background)
//                    .centerCrop()
//                    .into(holder.profileImage);
//
                    holder.commentProfileName.setText("Jeffrey");
                    holder.commentET.setText(comments.get(position).getCaption());
        } else {
            // SET profle Image
            GlideUrl profileImage = new GlideUrl("", new LazyHeaders.Builder()
                    .addHeader("User-Agent", "profileImage")
                    .build());
            Glide.with(mInflater.getContext())
                    .asBitmap()
                    .load(profileImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.account)
                    .error(R.drawable.account)
                    .centerCrop()
                    .into(holder.profileImage);
//
            holder.commentProfileName.setText("");
            holder.commentET.setText("No comments");
        }
    }

    @Override
    public int getItemCount() {
        if (comments != null) {
            return comments.size();
        }
        return -1;
    }

    public class VIewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        CircleImageView profileImage;
        TextView commentProfileName;
        TextView commentET;

        public VIewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            commentProfileName = itemView.findViewById(R.id.commentProfileName);
            commentET = itemView.findViewById(R.id.commentET);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
