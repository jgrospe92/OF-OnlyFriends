package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.models.Comment;
import com.example.models.Post;
import com.example.models.Profile;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class homefragment extends Fragment implements RVAhome.ItemClickListener {

    RecyclerView home_recycleView;
    RVAhome recycleViewAdapterHome;
    View view;
    SharedPreferences userData;
    ArrayList<Post> posts;
    Post postHelper;
    TextView noPostTextView;
    ArrayList<Post> checkPost = new ArrayList<>();
    public homefragment(ArrayList<Post> posts, TextView noPostTextView) {
        // Required empty public constructor
        this.posts = posts;
        this.noPostTextView = noPostTextView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // SHARED PREFERENCES
        userData = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        view = inflater.inflate(R.layout.fragment_homefragment, container, false);
//        posts = new ArrayList<>();
        postHelper = new Post(view.getContext());
        posts = postHelper.getAllPosts();
        home_recycleView = view.findViewById(R.id.home_recycleView);
        home_recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recycleViewAdapterHome = new RVAhome(view.getContext(), posts, userData, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                recycleViewAdapterHome.notifyDataSetChanged();
            }
            @Override
            public void onLongClicked(int position) {
            }
        });

        home_recycleView.addItemDecoration(new DividerItemDecoration(view.getContext(),
                DividerItemDecoration.VERTICAL));
        recycleViewAdapterHome.notifyDataSetChanged();
        recycleViewAdapterHome.setClickListener(this);
        home_recycleView.setAdapter(recycleViewAdapterHome);

        return view;
    }
    public void checkIfPostExists(){
        checkPost = postHelper.getAllPosts();
        if (checkPost.isEmpty()) {
            // IF NO POST, SET NO POST WARNING TO TRUE
            noPostTextView.setVisibility(View.VISIBLE);
        } else {
            noPostTextView.setVisibility(View.GONE);
        }
        checkPost = null;
    }
    public void deletePost(Post postHelper, int position){
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    postHelper.delete(posts.get(position).getPostID());
                    posts.remove(position);
                    recycleViewAdapterHome.notifyItemRemoved(position);
                    checkIfPostExists();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure you want to delete your post?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // METHOD TO OPEN SHOW COMMENT DIALOG
    private void showCommentSectionDialog(int position){
        Comment commentHelper = new Comment(view.getContext());
        ArrayList<Comment> comments = commentHelper.getAll(recycleViewAdapterHome.getItem(position).getPostID());
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
        if(view.getId() == R.id.postImage){
            showCommentSectionDialog(position);
        }
        if(view.getId() == R.id.deleteImageButton){
            deletePost(postHelper, position);
        }

    }

}