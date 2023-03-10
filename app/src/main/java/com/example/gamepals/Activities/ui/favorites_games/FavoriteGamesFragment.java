package com.example.gamepals.Activities.ui.favorites_games;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamepals.Adapters.GamesAdapter;
import com.example.gamepals.Callbacks.GameCallback;
import com.example.gamepals.databinding.FragmentFavoriteGamesBinding;
import com.example.gamepals.Models.Game;
import com.example.gamepals.Models.User;

import java.util.ArrayList;


public class FavoriteGamesFragment extends Fragment {

    private FragmentFavoriteGamesBinding binding;
    private GamesAdapter gamesAdapter;
    private FavoriteGamesViewModel favoriteGamesViewModel;

    private Observer<ArrayList<Game>> observer = new Observer<ArrayList<Game>>() {
        @Override
        public void onChanged(ArrayList<Game> games) {
            gamesAdapter.updateGames(games);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FavoriteGamesViewModel favoriteGamesViewModel =
                new ViewModelProvider(this).get(FavoriteGamesViewModel.class);
        binding = FragmentFavoriteGamesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViews();
        setCallbacks();
        initListeners();
        return root;
    }



    private void initViews() {
        favoriteGamesViewModel = new FavoriteGamesViewModel();
        favoriteGamesViewModel.getMGames().observe(getViewLifecycleOwner(), observer);

        gamesAdapter = new GamesAdapter(getContext(),this);
        binding.favLSTGames.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.favLSTGames.setAdapter(gamesAdapter);
    }
    private void initListeners() {

        binding.favETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                gamesAdapter.filter(editable.toString());
            }
        });
    }

    private void setCallbacks() {
        gamesAdapter.setGameCallback(new GameCallback() {
            @Override
            public void favoriteClicked(Game game, int position) {
                if (!User.getInstance().checkFavGame(game.getName()))
                    User.getInstance().getFavoriteGames().add(game.getName());
                else
                    User.getInstance().getFavoriteGames().remove(game.getName());
                favoriteGamesViewModel.updateFavGamesDB();
                gamesAdapter.notifyItemChanged(position);
            }

            @Override
            public void itemClicked(Game game, int position) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.favETSearch.setText(null);
    }
}