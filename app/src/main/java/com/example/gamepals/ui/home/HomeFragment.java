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
import com.example.gamepals.Adapters.GroupAdapter;
import com.example.gamepals.SettingsActivity;
import com.example.gamepals.databinding.FragmentHomeBinding;
import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private GroupAdapter groupAdapter;
    private HomeViewModel homeViewModel;
    private Observer<HashMap<String, Group>> observer = new Observer<HashMap<String, Group>>() {

        @Override
        public void onChanged(HashMap<String, Group> groups) {
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

//    public void oneTime(){
//        Game game1 = new Game("Call of Duty: Modern Warfare II","https://m.media-amazon.com/images/M/MV5BMjZjODM2MDMtMGE2ZS00NWIyLTkzOWMtYjY0YTM3MzQ0ZjMzXkEyXkFqcGdeQXVyNTgyNTA4MjM@._V1_FMjpg_UX600_.jpg");
//        Game game2 = new Game("Counter-Strike: Global Offensive","https://m.media-amazon.com/images/M/MV5BMzE3Y2I4NjUtNWE4OS00MmRlLTk5MDctNzhlNGU2ZjllY2U3XkEyXkFqcGdeQXVyNTk1MjA5MjM@._V1_FMjpg_UX600_.jpg");
//        Game game3 = new Game("FIFA 23","https://images.igdb.com/igdb/image/upload/t_cover_big/co4zw5.png");
//        Game game4 = new Game("Fortnite","https://m.media-amazon.com/images/M/MV5BNzU2YTY2OTgtZGZjZi00MTAyLThlYjUtMWM5ZmYzOGEyOWJhXkEyXkFqcGdeQXVyNTgyNTA4MjM@._V1_FMjpg_UX960_.jpg");
//        Game game5 = new Game("Overwatch 2","https://m.media-amazon.com/images/M/MV5BMDNkZDVkODEtNjQyYy00NGYwLTljMGQtOTI2MDAwY2ZlOWFmXkEyXkFqcGdeQXVyNjM2MTY3MTY@._V1_FMjpg_UY720_.jpg");
//        Game game6 = new Game("Grand Theft Auto V","https://m.media-amazon.com/images/M/MV5BYWQyNTY1NzAtMGJiYi00ZTcwLWE0ZjktYjY4YTZkMzA1YzZmXkEyXkFqcGdeQXVyNTgyNTA4MjM@._V1_FMjpg_UX960_.jpg");
//
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = db.getReference("Games");
//        databaseReference.child(game1.getName()).setValue(game1);
//        databaseReference.child(game2.getName()).setValue(game2);
//        databaseReference.child(game3.getName()).setValue(game3);
//        databaseReference.child(game4.getName()).setValue(game4);
//        databaseReference.child(game5.getName()).setValue(game5);
//        databaseReference.child(game6.getName()).setValue(game6);
//
//    }


    private void initViews() {
        binding.homeTVHello.setText("Hello, " + User.getInstance().getName());
        homeViewModel = new HomeViewModel();
        homeViewModel.getGroups().observe(getViewLifecycleOwner(), observer);

        groupAdapter = new GroupAdapter(this);
        binding.homeLSTGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.homeLSTGroups.setAdapter(groupAdapter);
        binding.homeSettings.setOnClickListener(view -> loadSettingsScreen());
    }

    private void setCallbacks() {
        groupAdapter.setGroupCallback(new GroupCallback() {
            @Override
            public void joinClicked(Group group, int position) {
                User.getInstance().getGroups().put(group.getId(), group);
                group.addUser(User.getInstance());
                groupAdapter.removeGroup(group.getId());
                homeViewModel.updateJoinedGroupDB(group);
            }

            @Override
            public void itemClicked(Group group, int position) {
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