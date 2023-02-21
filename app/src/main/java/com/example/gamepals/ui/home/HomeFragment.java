package com.example.gamepals.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        initListeners();
        return root;
    }

    private void initListeners() {
        binding.homeETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                groupAdapter.filter(editable.toString());
            }
        });
    }

//    public void oneTime(){
//        Game game1 = new Game("Valorant","https://images.igdb.com/igdb/image/upload/t_cover_big/co2mvt.png");
//        Game game2 = new Game("DOTA 2","https://images.igdb.com/igdb/image/upload/t_cover_big/co4bko.png");
//        Game game3 = new Game("League of Legends","https://images.igdb.com/igdb/image/upload/t_cover_big/co49wj.png");
//        Game game4 = new Game("Apex Legends","https://images.igdb.com/igdb/image/upload/t_cover_big/co1wzo.png");
//        Game game5 = new Game("Rainbow Six: Siege","https://images.igdb.com/igdb/image/upload/t_cover_big/co5cxf.png");
//        Game game6 = new Game("Overcooked!","https://images.igdb.com/igdb/image/upload/t_cover_big/co262g.png");
//        Game game7 = new Game("Overcooked!2","https://images.igdb.com/igdb/image/upload/t_cover_big/co1usu.png");
//        Game game8 = new Game("Overcooked! All You Can Eat","https://images.igdb.com/igdb/image/upload/t_cover_big/co2t83.png");
//        Game game9 = new Game("Rocket League","https://images.igdb.com/igdb/image/upload/t_cover_big/co5w0w.png");
//
//
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = db.getReference("Games");
//        databaseReference.child(game1.getName()).setValue(game1);
//        databaseReference.child(game2.getName()).setValue(game2);
//        databaseReference.child(game3.getName()).setValue(game3);
//        databaseReference.child(game4.getName()).setValue(game4);
//        databaseReference.child(game5.getName()).setValue(game5);
//        databaseReference.child(game9.getName()).setValue(game9);
//        databaseReference.child(game6.getName()).setValue(game6);
//        databaseReference.child(game7.getName()).setValue(game7);
//        databaseReference.child(game6.getName()).setValue(game8);
//
//    }


    private void initViews() {
        binding.homeTVHello.setText("Hello, " + User.getInstance().getName());
        homeViewModel = new HomeViewModel();
        homeViewModel.getMGroups().observe(getViewLifecycleOwner(), observer);

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
                groupAdapter.notifyItemRemoved(position);
                homeViewModel.updateJoinedGroupDB(group);
            }

            @Override
            public void itemClicked(Group group, int position) {
            }

            @Override
            public void leaveClicked(Group item, int position) {

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

    @Override
    public void onPause() {
        super.onPause();
        binding.homeETSearch.setText(null);
    }
}