package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.models.Helper;
import com.example.models.Profile;

import java.util.ArrayList;

public class searchFragment extends Fragment {


    EditText searchEditText;
    TextView textViewNoResultFound;
    ArrayList<Profile> profiles;
    RecyclerView searchRecycleView;
    Button btn_search;
    View view;
    RVAsearch searchAdapter;

    public searchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Profile profileHelper = new Profile(getContext());
        view = inflater.inflate(R.layout.fragment_search, container, false);
        searchRecycleView = view.findViewById(R.id.searchRecycleView);
        searchEditText = view.findViewById(R.id.searchEditText);
        btn_search = view.findViewById(R.id.btn_search);
        textViewNoResultFound = view.findViewById(R.id.textViewNoResultFound);
        textViewNoResultFound.setVisibility(View.GONE);
        searchEditText.setText("");

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Helper.isEditTextEmpty(searchEditText)){
                    if (profiles != null){
                        profiles.clear();
                        searchAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {Toast.makeText(getContext(), "after text changed", Toast.LENGTH_SHORT).show();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = searchEditText.getText().toString();
                if (Helper.checkInputImage(searchEditText)){
                    profiles = profileHelper.getProfilesByKeyword(word);
                    if (profiles == null){
                        textViewNoResultFound.setVisibility(View.VISIBLE);

                    } else {
                        textViewNoResultFound.setVisibility(View.GONE);
                    }
                    searchRecycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    searchAdapter = new RVAsearch(view.getContext(), profiles);
                    searchRecycleView.addItemDecoration(new DividerItemDecoration(view.getContext(),
                            DividerItemDecoration.VERTICAL));
                    searchRecycleView.setAdapter(searchAdapter);
                } else {
                    Toast.makeText(getContext(), "Please search by profile name or first name", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

}