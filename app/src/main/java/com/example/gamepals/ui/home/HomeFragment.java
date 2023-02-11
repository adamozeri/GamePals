package com.example.gamepals.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamepals.GroupRecyclerAdapter;
import com.example.gamepals.R;
import com.example.gamepals.databinding.FragmentHomeBinding;
import com.example.gamepals.model.Group;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView home_LST_groups;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        findViews();
        initViews();
        return root;
    }

    private void findViews() {
        home_LST_groups = binding.getRoot().findViewById(R.id.home_LST_groups);
    }

    private void initViews() {
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group("test",5,"test test", "Europe","Beginner", "PC"));

        GroupRecyclerAdapter groupAdapter = new GroupRecyclerAdapter(getContext(), groups);
        home_LST_groups.setLayoutManager(new LinearLayoutManager(getContext()));
        home_LST_groups.setAdapter(groupAdapter);
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}