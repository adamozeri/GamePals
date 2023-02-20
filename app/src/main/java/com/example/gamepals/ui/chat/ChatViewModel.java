package com.example.gamepals.ui.chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamepals.Utils.Constants;
import com.example.gamepals.model.ChatMessage;
import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<ChatMessage>> mChatMessages;

    public ChatViewModel(){
        mChatMessages = new MutableLiveData<>();
    }
    public ChatViewModel(String groupId) {
        this();
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference().child(Constants.DB_GROUPS).child(groupId).child(Constants.DB_CHAT);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatMessage newMessage = snapshot.getValue(ChatMessage.class);
                if(newMessage != null){
                    chatMessages.add(newMessage);
                    mChatMessages.setValue(chatMessages);
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

    public void updateGroupChat(String groupID,ArrayList<ChatMessage> chatMessages){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference(Constants.DB_GROUPS);
        databaseReference.child(groupID).child(Constants.DB_CHAT).setValue(chatMessages);
    }

    public void updateUser(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference(Constants.DB_USERS);
        databaseReference.child(User.getInstance().getUid()).setValue(User.getInstance());
    }



    public MutableLiveData<ArrayList<ChatMessage>> getChatMessages(){
        return mChatMessages;
    }
}