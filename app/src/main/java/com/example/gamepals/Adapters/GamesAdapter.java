package com.example.gamepals.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamepals.GameCallback;
import com.example.gamepals.R;
import com.example.gamepals.Utils.Constants;
import com.example.gamepals.model.Game;
import com.example.gamepals.model.User;
import com.example.gamepals.ui.favorites_games.FavoriteGamesFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class GamesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Fragment fragment;
    private ArrayList<Game> games;
    private ArrayList<Game> fullGamesList;
    private GameCallback gameCallback;


    public GamesAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.games = new ArrayList<>();
        this.fullGamesList = new ArrayList<>();
    }

    public GamesAdapter setGameCallback(GameCallback gameCallback) {
        this.gameCallback = gameCallback;
        return this;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        if (fragment instanceof FavoriteGamesFragment)
            return new FavGameViewHolder(view);
        else
            return new GameSelectViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (fragment instanceof FavoriteGamesFragment) {
            ((FavGameViewHolder) holder).setData(games.get(position));
        } else
            ((GameSelectViewHolder) holder).setData(games.get(position));
    }


    public Game getItem(int position) {
        return games.get(position);
    }

    @Override
    public int getItemCount() {
        return games == null ? 0 : games.size();
    }

    public void updateGames(ArrayList<Game> games) {
        this.games = games;
        this.fullGamesList = games;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Game> filterList) {
        this.games = filterList;
        notifyDataSetChanged();
    }

    public void filter(String text) {
        ArrayList<Game> filteredList = new ArrayList<>();
        for (Game game : fullGamesList) {
            if (game.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(game);
            }
        }
        filterList(filteredList);
    }


    public class FavGameViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView game_title;
        private AppCompatImageButton game_BTN_favorite;
        private ShapeableImageView game_IMG;

        public FavGameViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews();
            initListeners();
        }

        private void findViews() {
            game_title = itemView.findViewById(R.id.game_title);
            game_BTN_favorite = itemView.findViewById(R.id.game_BTN_favorite);
            game_IMG = itemView.findViewById(R.id.game_IMG);
            game_BTN_favorite.setVisibility(View.VISIBLE);
        }

        private void initListeners() {
            game_BTN_favorite.setOnClickListener(view -> gameCallback.favoriteClicked(getItem(getAdapterPosition()), getAdapterPosition()));
        }

        private void setData(Game game) {
            game_title.setText(game.getName());
            Glide.with(context).load(game.getImage()).into(game_IMG);
            if (User.getInstance().checkFavGame(game.getName()))
                game_BTN_favorite.setImageResource(R.drawable.ic_star_full);
            else
                game_BTN_favorite.setImageResource(R.drawable.ic_star_empty);
        }
    }

    public class GameSelectViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView game_title;
        private AppCompatImageButton game_BTN_favorite;
        private ShapeableImageView game_IMG;

        public GameSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews();
            initListeners();
        }

        private void findViews() {
            game_title = itemView.findViewById(R.id.game_title);
            game_BTN_favorite = itemView.findViewById(R.id.game_BTN_favorite);
            game_IMG = itemView.findViewById(R.id.game_IMG);
            game_BTN_favorite.setVisibility(View.INVISIBLE);
        }

        private void initListeners() {
            itemView.setOnClickListener(view -> gameCallback.itemClicked(getItem(getAdapterPosition()), getAdapterPosition()));
        }

        private void setData(Game game) {
            game_title.setText(game.getName());
            Glide.with(context).load(game.getImage()).into(game_IMG);
        }

    }
}
