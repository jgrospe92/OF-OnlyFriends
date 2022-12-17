package com.example.myapplication;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.models.Comment;
import com.example.models.Helper;
import com.example.models.Notification;
import com.example.models.Post;
import com.example.models.Profile;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RVAhome extends RecyclerView.Adapter<RVAhome.VIewHolder> {

    private ArrayList<Post> postsData;
    private LayoutInflater mInflater;
    private SharedPreferences sharedData;
    private ClickListener listener;
    Profile profile;
    Profile currentProfile;

    Notification notifHelper;
    Post postHelper;
    // CLICK LISTENER
    private ItemClickListener mClickListener;

//    private final View.OnClickListener mOnClickListener = new


    // CONSTRUCTOR


    public RVAhome(Context context, ArrayList<Post> postsData, SharedPreferences user, ClickListener listener) {
        this.listener = listener;
        this.sharedData = user;
        this.postsData = postsData;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VIewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_post_with_image, parent, false), listener);

    }

    @Override
    public void onBindViewHolder(@NonNull VIewHolder holder, @SuppressLint("RecyclerView") int position) {

        profile = new Profile(mInflater.getContext());
        postHelper = new Post(mInflater.getContext());
        notifHelper = new Notification(mInflater.getContext());
        profile = profile.getProfileByID(postsData.get(position).getProfileID());
        String postid = postsData.get(position).getPostID();
        // HIDE DELETE BUTTON
        holder.deleteImageButton.setVisibility(View.INVISIBLE);
        holder.deleteImageButton.setEnabled(false);
        currentProfile = new Profile(mInflater.getContext());
        currentProfile = currentProfile.get(sharedData.getString("userID",""));
        // ENABLE DELETE POST TO THE POST OWNER
        if (currentProfile.getProfileID().equals(postsData.get(position).getProfileID())){
            holder.deleteImageButton.setVisibility(View.VISIBLE);
            holder.deleteImageButton.setEnabled(true);
        }

        holder.tv_profleName.setText(profile.getProfileName());
        holder.tv_firstName.setText(profile.getFname());
        holder.tv_caption.setText(postsData.get(position).getCaption());
        holder.tv_replyCount.setText(postHelper.getNumberOfComments(postsData.get(position).getPostID()));
        holder.tv_likeCount.setText(String.valueOf(postsData.get(position).getLikes()));
        holder.tv_savedCount.setText(String.valueOf(postsData.get(position).getFavorites()));
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
                .into(holder.circleImageView);

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


        holder.imbView_likes.setOnClickListener(view -> {
            Notification notification = new Notification();
            if (notifHelper.checkIfAlreadyLiked(currentProfile.getProfileID(), "liked", postsData.get(position).getPostID())){
                // IF POST IS ALREADY LIKED UNLIKE IT.
                Post p = postHelper.get(postsData.get(position).getPostID());
                int i = p.getLikes();
                p.setLikes(--i);
                updatePost(p);
                holder.tv_likeCount.setText(String.valueOf(p.getLikes()));
                notification = notifHelper.getNotifByCurrentProfile(currentProfile.getProfileID());
                notifHelper.delete(notification.getNotifID());

            } else {
                // IF POST IS NOT LIKED
                Post p = postHelper.get(postsData.get(position).getPostID());
                int i = p.getLikes();
                p.setLikes(++i);
                updatePost(p);
                holder.tv_likeCount.setText(String.valueOf(p.getLikes()));
                notification.setDescription("liked");
                notification.setProfileID(profile.getProfileID()); // post owner profileID
                notification.setPostID(postsData.get(position).getPostID());
                notification.setCurrentProfileId(currentProfile.getProfileID()); // profile who liked the post
                notifHelper.insert(notification);
            }
        });
        holder.imgView_saved.setOnClickListener(view -> {
            Notification notification = new Notification();

            if (notifHelper.checkIfAlreadySaved(currentProfile.getProfileID(), "saved", postsData.get(position).getPostID())) {
                // IF POST IS ALREADY SAVED, UNSAVED IT.
                Post p = postHelper.get(postsData.get(position).getPostID());
                int i = p.getFavorites();
                p.setFavorites(--i);
                updatePost(p);
                holder.tv_savedCount.setText(String.valueOf(p.getFavorites()));
                notification = notifHelper.getNotifByCurrentProfile(currentProfile.getProfileID());
                notifHelper.delete(notification.getNotifID());

            } else {
                Post p = postHelper.get(postsData.get(position).getPostID());
                int i = p.getFavorites();
                p.setFavorites(++i);
                updatePost(p);
                holder.tv_savedCount.setText(String.valueOf(p.getFavorites()));
                notification.setDescription("saved");
                notification.setProfileID(profile.getProfileID()); // post owner profileID
                notification.setPostID(postsData.get(position).getPostID());
                notification.setCurrentProfileId(currentProfile.getProfileID()); // profile who liked the post
                notifHelper.insert(notification);
            }
        });

        holder.imgView_replies.setOnClickListener(view -> {
            showCommentDialog(postid, holder, position);
        });

    }

    public void updatePost(Post post){
        Post postHelper = new Post(mInflater.getContext());
        postHelper.update(post);
    }

    // SHOW DIALOG FOR REPLY COMMENT
    // METHOD TO OPEN DIALOG
    private void showCommentDialog(String postID, @NonNull VIewHolder holder, int pos){
        Dialog dialog =  new Dialog(mInflater.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.comment_dialog);

        // Initializes the views
        EditText comment = dialog.findViewById(R.id.commentEditText);
        Button commentButton = dialog.findViewById(R.id.commentButton);
        Button cancelCommentButton = dialog.findViewById(R.id.cancelCommentButton);


        // WHEN POST IS CLICKED
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(comment.getText().toString())){
                    comment.setError("you forgot to write something");
                    return;
                }
                Profile profile = new Profile(view.getContext());
                profile = profile.get(sharedData.getString("userID",""));
                Comment commentHelper = new Comment(view.getContext());
                String postCaption = comment.getText().toString();
                Comment comment = new Comment();

                comment.setCaption(postCaption);
                comment.setDatePosted(Helper.getCurrentDate());
                comment.setPostID(postID);
                comment.setProfileID(profile.getProfileID());

                // TODO: INSERT COMMENT TO THE DATABASE COMMENT TABLE
                if(commentHelper.insert(comment)) {
                    Toast.makeText(view.getContext(), "comment added", Toast.LENGTH_SHORT).show();
                    Post post = new Post(view.getContext());
                    int i = Integer.parseInt(post.getNumberOfComments(postID));
                    holder.tv_replyCount.setText(String.valueOf(i));
                    dialog.dismiss();

                } else {
                    Toast.makeText(view.getContext(), "failed to add comment", Toast.LENGTH_SHORT).show();
                }
            }
        });


        cancelCommentButton.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return postsData.size();
    }
    // View Holder class
    public class VIewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        private WeakReference<ClickListener> listenerRef;

        CircleImageView circleImageView;
        ImageView imbView_likes;
        ImageView imgView_saved;
        ImageView imgView_replies;
        TextView tv_firstName;
        TextView tv_profleName;
        TextView tv_caption;
        ImageView postImage;
        ImageButton deleteImageButton;
        TextView tv_replyCount;
        TextView tv_likeCount;
        TextView tv_savedCount;
        TextView tv_datePosted;

        public VIewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profileCircleImage);
            tv_firstName = itemView.findViewById(R.id.tv_firstName);
            tv_profleName = itemView.findViewById(R.id.tv_profleName);
            tv_caption = itemView.findViewById(R.id.tv_caption);
            postImage = itemView.findViewById(R.id.postImage);
            deleteImageButton = itemView.findViewById(R.id.deleteImageButton);
            tv_replyCount = itemView.findViewById(R.id.tv_replyCount);
            tv_likeCount = itemView.findViewById(R.id.tv_likeCount);
            tv_savedCount = itemView.findViewById(R.id.tv_savedCount);
            tv_datePosted = itemView.findViewById(R.id.tv_datePosted);
            imbView_likes = itemView.findViewById(R.id.imbView_likes);
            imgView_saved = itemView.findViewById(R.id.imgView_saved);
            imgView_replies = itemView.findViewById(R.id.imgView_replies);
            deleteImageButton.setOnClickListener(this);
            postImage.setOnClickListener(this);
            listenerRef = new WeakReference<>(listener);

//          itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null ) mClickListener.onItemClick(view, getAdapterPosition());
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

    }

    // convenience method for getting data at click position
    Post getItem(int id) {
        return postsData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
