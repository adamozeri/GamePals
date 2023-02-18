package com.example.gamepals.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamepals.GroupRecyclerAdapter;
import com.example.gamepals.LoginActivity;
import com.example.gamepals.MainActivity;
import com.example.gamepals.SettingsActivity;
import com.example.gamepals.databinding.FragmentHomeBinding;
import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private GroupRecyclerAdapter groupAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViews();
        setCallbacks();

        binding.homeSettings.setOnClickListener(view -> loadSettingsScreen());

        return root;
    }



    private void initViews() {
        binding.homeTVHello.setText("Hello, "+User.getInstance().getName());
        HomeViewModel homeViewModel = new HomeViewModel();
        homeViewModel.getGroups().observe(getViewLifecycleOwner(),observer);

        groupAdapter = new GroupRecyclerAdapter(getContext());
        binding.homeLSTGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.homeLSTGroups.setAdapter(groupAdapter);


    }

    private void setCallbacks() {
        //        groupAdapter.setMovieCallback(new MovieCallback() {
//            @Override
//            public void favoriteClicked(Movie movie, int position) {
//                movie.setFavorite(!movie.isFavorite());
//                home_LST_groups.getAdapter().notifyItemChanged(position);
//            }
//
//            @Override
//            public void itemClicked(Movie movie, int position) {
//                Toast.makeText(MainActivity.this,""+ movie.getTitle() + " has been clicked!",Toast.LENGTH_SHORT).show();
//            }
//        });
    }



    private void loadSettingsScreen() {
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    Observer<HashMap<String,Group>> observer = new Observer<HashMap<String,Group>>(){

        @Override
        public void onChanged(HashMap<String,Group> groups) {
            groupAdapter.updateGroups(groups);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}