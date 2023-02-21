package com.example.gamepals.Activities.ui.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamepals.Utils.Constants;
import com.example.gamepals.Models.ChatMessage;
import com.example.gamepals.Models.User;
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

    public void updateGroupChat(String groupID,ChatMessage chatMessage,String index){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference(Constants.DB_GROUPS);
        databaseReference.child(groupID).child(Constants.DB_CHAT).child(index).setValue(chatMessage);
    }

    public void updateUser(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference(Constants.DB_USERS);
        databaseReference.child(User.getInstance().getUid()).setValue(User.getInstance());
    }

    public ArrayList<ChatMessage> getMessagesFromDB(String groupID){
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference().child(Constants.DB_GROUPS).child(groupID).child(Constants.DB_CHAT);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatMessage newMessage = snapshot.getValue(ChatMessage.class);
                chatMessages.add(newMessage);
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
        return chatMessages;
    }

    public MutableLiveData<ArrayList<ChatMessage>> getChatMessages(){
        return mChatMessages;
    }
}
