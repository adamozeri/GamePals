package com.example.gamepals.ui.create_a_group;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CreateAGroupViewModel extends ViewModel {


    public CreateAGroupViewModel() {
    }


    /**
     * updating firebase: groups, admins' group
     **/
    public CreateAGroupViewModel(Group newGroup) {
        this();

        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = db.getReference("Groups");
        databaseReference.child(newGroup.getId()).setValue(newGroup);

        databaseReference = db.getReference("UserInfo");
        databaseReference.child(User.getInstance().getUid()).setValue(User.getInstance());
    }
}