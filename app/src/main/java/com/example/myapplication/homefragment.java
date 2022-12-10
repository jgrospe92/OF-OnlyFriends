package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.models.Post;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class homefragment extends Fragment {

    RecyclerView home_recycleView;
    RVAhome recycleViewAdapterHome;

    public homefragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);
        ArrayList<Post> posts = new ArrayList<>();
        Post postHelper = new Post(view.getContext());
        posts = postHelper.getAllPosts();
        home_recycleView = view.findViewById(R.id.home_recycleView);
        home_recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recycleViewAdapterHome = new RVAhome(view.getContext(), posts);
        home_recycleView.addItemDecoration(new DividerItemDecoration(view.getContext(),
                DividerItemDecoration.VERTICAL));
        recycleViewAdapterHome.notifyDataSetChanged();
        home_recycleView.setAdapter(recycleViewAdapterHome);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}