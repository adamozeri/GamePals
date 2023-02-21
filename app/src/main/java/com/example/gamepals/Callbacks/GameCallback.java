package com.example.gamepals.Callbacks;

import com.example.gamepals.Models.Game;

public interface GameCallback {
    void favoriteClicked(Game game, int position);
    void itemClicked(Game game, int position);
}
