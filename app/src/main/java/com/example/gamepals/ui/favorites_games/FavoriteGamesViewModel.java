package com.example.gamepals.ui.favorites_games;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamepals.Utils.Constants;
import com.example.gamepals.model.Game;
import com.example.gamepals.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavoriteGamesViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Game>> mGames;

    public FavoriteGamesViewModel() {
        mGames = new MutableLiveData<>();
        ArrayList<Game> games = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference().child(Constants.DB_GAMES);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Game newGame = snapshot.getValue(Game.class);
                if (newGame != null) {
                    games.add(newGame);
                    mGames.setValue(games);
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<Game> getGames(){
        ArrayList<Game> games = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference().child(Constants.DB_GAMES);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Game game = snapshot.getValue(Game.class);
                games.add(game);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return games;
    }
    public void updateFavGamesDB() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userDatabaseReference = db.getReference(Constants.DB_USERS);
        userDatabaseReference.child(User.getInstance().getUid()).setValue(User.getInstance());
    }

    public MutableLiveData<ArrayList<Game>> getMGames() {
        return mGames;
    }
}
