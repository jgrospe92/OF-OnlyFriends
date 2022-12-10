package com.example.myapplication;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Post;
import com.example.models.Profile;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RVAhome extends RecyclerView.Adapter<RVAhome.VIewHolder> {

    private ArrayList<Post> postsData;
    private LayoutInflater mInflater;


    // CONSTRUCTOR


    public RVAhome(Context context, ArrayList<Post> postsData) {
        this.postsData = postsData;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_post_with_image, parent, false);
        return new VIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VIewHolder holder, int position) {

        Profile profile = new Profile(mInflater.getContext());
        Post postHelper = new Post(mInflater.getContext());
        profile = profile.getProfileByID(postsData.get(position).getProfileID());

        holder.tv_profleName.setText(profile.getProfileName());
        holder.tv_firstName.setText(profile.getFname());
        holder.tv_caption.setText(postsData.get(position).getCaption());
        holder.tv_replyCount.setText(postHelper.getNumberOfComments(postsData.get(position).getPostID()));
        holder.tv_likeCount.setText(String.valueOf(postsData.get(position).getLikes()));
        holder.tv_savedCount.setText(String.valueOf(postsData.get(position).getFavorites()));


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
                .into(holder.circleImageView);


        if (!postsData.get(position).getImageURL().isEmpty()) {

            GlideUrl postImage = new GlideUrl(postsData.get(position).getImageURL(), new LazyHeaders.Builder()
                    .addHeader("User-Agent", "image")
                    .build());
            Glide.with(mInflater.getContext())
                    .asBitmap()
                    .load(postImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.defaul_image)
                    .error(R.drawable.defaul_image)
                    .centerCrop()
                    .into(holder.postImage);
        }

    }

    @Override
    public int getItemCount() {
        return postsData.size();
//        return  5;
    }

    public class VIewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView tv_firstName;
        TextView tv_profleName;
        TextView tv_caption;
        ImageView postImage;
        TextView tv_replyCount;
        TextView tv_likeCount;
        TextView tv_savedCount;

        public VIewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            tv_firstName = itemView.findViewById(R.id.tv_firstName);
            tv_profleName = itemView.findViewById(R.id.tv_profleName);
            tv_caption = itemView.findViewById(R.id.tv_caption);
            postImage = itemView.findViewById(R.id.postImage);
            tv_replyCount = itemView.findViewById(R.id.tv_replyCount);
            tv_likeCount = itemView.findViewById(R.id.tv_likeCount);
            tv_savedCount = itemView.findViewById(R.id.tv_savedCount);


        }
    }
}
