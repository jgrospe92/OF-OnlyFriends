package com.example.myapplication;

import android.content.Context;
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
import com.example.models.Helper;
import com.example.models.Post;
import com.example.models.Profile;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RVAlikedPost extends  RecyclerView.Adapter<RVAlikedPost.ViewHolder>{

    private ArrayList<Post> postsData;
    private LayoutInflater mInflater;


    public RVAlikedPost(Context context, ArrayList<Post> postData)
    {
        this.postsData = postData;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVAlikedPost.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.like_post_with_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Profile profile = new Profile(mInflater.getContext());
        profile = profile.getProfileByID(postsData.get(position).getProfileID());

        holder.tv_profleName.setText(profile.getProfileName());
        holder.tv_firstName.setText(profile.getFname());
        holder.tv_caption.setText(postsData.get(position).getCaption());

        String datePosted = postsData.get(position).getDatePosted();
        // CALCULATE TIME DIFFERENCES
        holder.tv_datePosted.setText(Helper.getTimeDiff(datePosted));


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
                .into(holder.profileCircleImage);

        if (!postsData.get(position).getImageURL().isEmpty()) {

            GlideUrl postImage = new GlideUrl(postsData.get(position).getImageURL().trim(), new LazyHeaders.Builder()
                    .addHeader("User-Agent", "image")
                    .build());
            Glide.with(mInflater.getContext())
                    .asBitmap()
                    .load(postImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.defaul_image)
                    .error(R.drawable.imagerror)
                    .centerCrop()
                    .into(holder.postImage);
        } else {
            Glide.with(mInflater.getContext())
                    .asBitmap()
                    .load(R.drawable.defaul_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.defaul_image)
                    .error(R.drawable.imagerror)
                    .centerCrop()
                    .into(holder.postImage);
        }

    }

    @Override
    public int getItemCount() {
        if (postsData != null){
            return postsData.size();
        }
        return  -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profileCircleImage;
        TextView tv_firstName, tv_profleName, tv_datePosted, tv_caption;
        ImageView postImage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileCircleImage = itemView.findViewById(R.id.profileCircleImage);
            tv_firstName = itemView.findViewById(R.id.tv_firstName);
            tv_profleName = itemView.findViewById(R.id.tv_profleName);
            tv_datePosted = itemView.findViewById(R.id.tv_datePosted);
            tv_caption = itemView.findViewById(R.id.tv_caption);
            postImage = itemView.findViewById(R.id.postImage);
        }
    }
}


