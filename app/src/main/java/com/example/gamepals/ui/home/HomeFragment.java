package com.example.gamepals.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamepals.GroupCallback;
import com.example.gamepals.Adapters.GroupRecyclerAdapter;
import com.example.gamepals.SettingsActivity;
import com.example.gamepals.databinding.FragmentHomeBinding;
import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private GroupRecyclerAdapter groupAdapter;
    private Observer<HashMap<String,Group>> observer = new Observer<HashMap<String,Group>>(){

        @Override
        public void onChanged(HashMap<String,Group> groups) {
            groupAdapter.updateGroups(groups);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViews();
        setCallbacks();

        return root;
    }



    private void initViews() {
        binding.homeTVHello.setText("Hello, "+User.getInstance().getName());
        HomeViewModel homeViewModel = new HomeViewModel();
        homeViewModel.getGroups().observe(getViewLifecycleOwner(),observer);

        groupAdapter = new GroupRecyclerAdapter(this);
        binding.homeLSTGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.homeLSTGroups.setAdapter(groupAdapter);
        binding.homeSettings.setOnClickListener(view -> loadSettingsScreen());
    }

    private void setCallbacks() {
        groupAdapter.setGroupCallback(new GroupCallback() {
            @Override
            public void joinClicked(Group group,int position) {
                User.getInstance().getGroups().put(group.getId(),group);
                group.addUser(User.getInstance());
                groupAdapter.removeGroup(group.getId());
                HomeViewModel homeViewModel = new HomeViewModel(group);
            }

            @Override
            public void itemClicked(Group group,int position) {

            }
        });

    }

    private void loadSettingsScreen() {
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivity(intent);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}