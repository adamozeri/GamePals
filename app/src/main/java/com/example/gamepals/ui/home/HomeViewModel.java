package com.example.gamepals.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamepals.Utils.Constants;
import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<HashMap<String, Group>> mGroups;

    public HomeViewModel() {
        mGroups = new MutableLiveData<>();
        HashMap<String,Group> groups = new HashMap<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference().child(Constants.DB_GROUPS);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Group newGroup = snapshot.getValue(Group.class);
                if(newGroup != null){
                    if(User.getInstance().getGroups().get(newGroup.getId()) == null){
                        groups.put(newGroup.getId(),newGroup);
                        mGroups.setValue(groups);
                    }
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


    public void updateJoinedGroupDB(Group group){
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = db.getReference(Constants.DB_GROUPS);
        databaseReference.child(group.getId()).setValue(group);

        databaseReference = db.getReference(Constants.DB_USERS);
        databaseReference.child(User.getInstance().getUid()).setValue(User.getInstance());
    }

    public HashMap<String, Group> getGroups(){
        HashMap<String,Group> groups = new HashMap<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference().child(Constants.DB_GROUPS);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Group newGroup = snapshot.getValue(Group.class);
                if(newGroup != null){
                    if(User.getInstance().getGroups().get(newGroup.getId()) == null){
                        groups.put(newGroup.getId(),newGroup);
                    }
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
        return groups;
    }
    public MutableLiveData<HashMap<String, Group>> getMGroups() {
        return mGroups;
    }
}