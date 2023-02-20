package com.example.gamepals.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamepals.GameCallback;
import com.example.gamepals.R;
import com.example.gamepals.model.Game;
import com.example.gamepals.model.User;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class FavoriteGamesAdapter extends RecyclerView.Adapter<FavoriteGamesAdapter.GameViewHolder> {

    private Context context;
    private ArrayList<Game> games;

    private ArrayList<Game> filteredGames;
    private ArrayList<Game> fullList;
    private GameCallback gameCallback;

    public FavoriteGamesAdapter(Context context) {
        this.context = context;
        this.games = new ArrayList<>();
    }

    public FavoriteGamesAdapter setGameCallback(GameCallback gameCallback) {
        this.gameCallback = gameCallback;
        return this;
    }

    @NonNull
    @Override
    public FavoriteGamesAdapter.GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = getItem(position);
        holder.game_title.setText(game.getName());
        Glide.with(context).load(game.getImage()).into(holder.game_IMG);
        if(User.getInstance().checkFavGame(getItem(position).getName()))
            holder.game_BTN_favorite.setImageResource(R.drawable.ic_star_full);
        else
            holder.game_BTN_favorite.setImageResource(R.drawable.ic_star_empty);
    }

    private Game getItem(int position) {
        return games.get(position);
    }

    @Override
    public int getItemCount() {
        return games == null ? 0 : games.size();
    }

    public void updateGames(ArrayList<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Game> filterList) {
        this.games = filterList;
        notifyDataSetChanged();
    }

    public ArrayList<Game> getGames() {
        return games;
    }


    public class GameViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView game_title;
        private AppCompatImageButton game_BTN_favorite;
        private ShapeableImageView game_IMG;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews();
            initListeners();
        }

        private void findViews() {
            game_title = itemView.findViewById(R.id.game_title);
            game_BTN_favorite = itemView.findViewById(R.id.game_BTN_favorite);
            game_IMG = itemView.findViewById(R.id.game_IMG);
        }

        private void initListeners(){
            game_BTN_favorite.setOnClickListener(view -> gameCallback.favoriteClicked(getItem(getAdapterPosition()), getAdapterPosition()));
        }

    }
}
