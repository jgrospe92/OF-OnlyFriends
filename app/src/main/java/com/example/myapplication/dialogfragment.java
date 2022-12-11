package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.models.Comment;
import com.example.models.Post;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class dialogfragment extends Fragment{

    RecyclerView comment_recycleview;
    RVAcomment commentAdapter;
    String postID;

    public dialogfragment(String postID) {
        this.postID = postID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogfragment, container, false);
        Comment commentHelper = new Comment(view.getContext());
        ArrayList<Comment> comments = commentHelper.getAll(postID);
        comment_recycleview = view.findViewById(R.id.comment_recycleview);
        comment_recycleview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        commentAdapter = new RVAcomment(view.getContext(), comments);
        comment_recycleview.setAdapter(commentAdapter);
        return  view;
    }


}