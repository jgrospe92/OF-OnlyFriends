package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.compose.ui.platform.ViewCompositionStrategy_androidKt;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.models.Comment;
import com.example.models.Post;
import com.example.models.Profile;
import com.example.models.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class postfragment extends Fragment implements RVApost.ItemClickListener {

    RecyclerView postRecyclerView;
    RVApost recycleViewAdapterPost;
    View view;
    SharedPreferences userData;
    Profile currentProfileID;

    public postfragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // SHARED PREFERENCES
        userData = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        view = inflater.inflate(R.layout.fragment_postfragment, container, false);
        ArrayList<Post> posts = new ArrayList<>();
        User userHelper = new User(view.getContext());
        User user = userHelper.get(userData.getString("userID",""));
         Profile profileHelper = new Profile(view.getContext());
        Post postHelper = new Post(view.getContext());

        /**
         * TODO: Change getAllPosts to display posts from currentPoser Done
         */
        currentProfileID = profileHelper.get(user.getUserID());
        posts = postHelper.getCurrentUserPosts(currentProfileID.getProfileID());
        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recycleViewAdapterPost = new RVApost(view.getContext(), posts, userData);
        postRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),
                DividerItemDecoration.VERTICAL));
        recycleViewAdapterPost.notifyDataSetChanged();
        recycleViewAdapterPost.setClickListener(this);
        postRecyclerView.setAdapter(recycleViewAdapterPost);

        return view;



    }


    // METHOD TO OPEN SHOW COMMENT DIALOG
    private void showCommentSectionDialog(int position){
        Comment commentHelper = new Comment(view.getContext());
        ArrayList<Comment> comments = commentHelper.getAll(recycleViewAdapterPost.getItem(position).getPostID());
        Dialog dialog =  new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RVAcomment commentAdapter = new RVAcomment(view.getContext(), comments);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(commentAdapter);
        dialog.setContentView(recyclerView);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);

    }


    @Override
    public void onItemClick(View view, int position) {
        // TODO display all comments
        showCommentSectionDialog(position);
        recycleViewAdapterPost.notifyItemChanged(position);
    }
}