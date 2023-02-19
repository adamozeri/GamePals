package com.example.gamepals.ui.favorites_games;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamepals.databinding.FragmentFavoriteGamesBinding;


public class FavoriteGamesFragment extends Fragment {

    private FragmentFavoriteGamesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FavoriteGamesViewModel favoriteGamesViewModel =
                new ViewModelProvider(this).get(FavoriteGamesViewModel.class);
        binding = FragmentFavoriteGamesBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}