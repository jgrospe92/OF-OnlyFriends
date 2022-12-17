package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.models.Helper;
import com.example.models.Notification;
import com.example.models.Profile;

import java.util.ArrayList;


public class NotificationFragmet extends Fragment implements  RVAnotification.ItemClickListener{

    ArrayList<Notification> notifications;
    ArrayList<Notification> checkNotification;
    Notification notifiHelper;
    TextView textViewNoNotif;
    RecyclerView notificationRecyclerView;
    Profile currentUserProfile;
    RVAnotification notificationAdapter;

    public NotificationFragmet(Profile currentUserProfile) {
        this.currentUserProfile = currentUserProfile;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_fragmet, container, false);
        // Initialize notification helper
        notifiHelper = new Notification(view.getContext());
        textViewNoNotif = view.findViewById(R.id.textViewNoNotif);
        checkForNotification();
        // GET ALL NOTIFICATIONS
        notifications = notifiHelper.getAll(currentUserProfile.getProfileID());
        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        notificationAdapter = new RVAnotification(view.getContext(), notifications);
        notificationAdapter.setClickListener(this);
        notificationRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),
                DividerItemDecoration.VERTICAL));
        notificationRecyclerView.setAdapter(notificationAdapter);

        return view;
    }

    private void checkForNotification(){
        checkNotification = notifiHelper.getAll(currentUserProfile.getProfileID());
        if (checkNotification == null) {
            textViewNoNotif.setVisibility(View.VISIBLE);
        } else  {
            textViewNoNotif.setVisibility(View.GONE);
        }
    }
    private void removeSingleItem(int position) {
        int removeIndex = position;
        notifications.remove(removeIndex);
        notificationAdapter.notifyItemRemoved(removeIndex);
    }

    @Override
    public void onItemClick(View view, int position) {
        Notification notification = notificationAdapter.getItem(position);
        notifiHelper.delete(notification.getNotifID());
        removeSingleItem(position);

    }
}