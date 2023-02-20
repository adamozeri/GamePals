package com.example.gamepals.ui.create_a_group;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamepals.Utils.Constants;
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
    public void updateGroupDB(Group newGroup){
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = db.getReference(Constants.DB_GROUPS);
        databaseReference.child(newGroup.getId()).setValue(newGroup);

        DatabaseReference userDatabaseReference = db.getReference(Constants.DB_USERS);
        userDatabaseReference.child(User.getInstance().getUid()).setValue(User.getInstance());
    }

}