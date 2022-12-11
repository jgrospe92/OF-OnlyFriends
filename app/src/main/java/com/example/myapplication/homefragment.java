package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.models.Comment;
import com.example.models.Post;

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
    public homefragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // SHARED PREFERENCES
        userData = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        view = inflater.inflate(R.layout.fragment_homefragment, container, false);
        ArrayList<Post> posts = new ArrayList<>();
        Post postHelper = new Post(view.getContext());
        posts = postHelper.getAllPosts();
        home_recycleView = view.findViewById(R.id.home_recycleView);
        home_recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recycleViewAdapterHome = new RVAhome(view.getContext(), posts, userData);
        home_recycleView.addItemDecoration(new DividerItemDecoration(view.getContext(),
                DividerItemDecoration.VERTICAL));
        recycleViewAdapterHome.notifyDataSetChanged();
        recycleViewAdapterHome.setClickListener(this);
        home_recycleView.setAdapter(recycleViewAdapterHome);

        return view;
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
        recycleViewAdapterHome.notifyItemChanged(position);
    }
}