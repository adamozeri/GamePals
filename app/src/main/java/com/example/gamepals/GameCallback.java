package com.example.gamepals;

import com.example.gamepals.model.Game;

public interface GameCallback {
    void favoriteClicked(Game game, int position);
}
