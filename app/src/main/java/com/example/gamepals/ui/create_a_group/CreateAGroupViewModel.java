package com.example.gamepals.ui.create_a_group;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamepals.Utils.Constants;
import com.example.gamepals.model.Game;
import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class CreateAGroupViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Game>> mGames;

    public CreateAGroupViewModel() {
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


    /**
     * updating firebase: groups, admins' group
     **/
    public void updateGroupDB(Group newGroup){
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = db.getReference(Constants.DB_GROUPS);
        databaseReference.child(newGroup.getId()).setValue(newGroup);

        DatabaseReference userDatabaseReference = db.getReference(Constants.DB_USERS);
        userDatabaseReference.child(User.getInstance().getUid()).setValue(User.getInstance());
    }

    public MutableLiveData<ArrayList<Game>> getMGames() {
        return mGames;
    }
}