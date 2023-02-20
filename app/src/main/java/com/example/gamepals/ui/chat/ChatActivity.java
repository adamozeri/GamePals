package com.example.gamepals.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamepals.Adapters.ChatAdapter;
import com.example.gamepals.R;
import com.example.gamepals.Utils.Constants;
import com.example.gamepals.databinding.ActivityChatBinding;
import com.example.gamepals.model.ChatMessage;
import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    private String groupID;
    private ChatAdapter chatAdapter;

    private Observer<ArrayList<ChatMessage>> observer = new Observer<ArrayList<ChatMessage>>(){

        @Override
        public void onChanged(ArrayList<ChatMessage> chatMessages) {
            chatAdapter.updateMessages(chatMessages);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadReceivedDetails();
        initViews();
        setListeners();
    }

    private void initViews() {
        ChatViewModel chatViewModel = new ChatViewModel(groupID);
        chatViewModel.getChatMessages().observe(this,observer);
        chatAdapter = new ChatAdapter();
        binding.chatLSTChatLst.setLayoutManager(new LinearLayoutManager(this));
        binding.chatLSTChatLst.setAdapter(chatAdapter);
    }


    private void setListeners() {
        binding.chatBTNBack.setOnClickListener(view -> finish());
        binding.chatFABSend.setOnClickListener(view -> sendMessage());
    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(User.getInstance().getUid(),
                groupID,
                binding.chatETMsgInput.getText().toString(),
                new Date().toString());
        ArrayList<ChatMessage> chatMessages = User.getInstance().getGroups().get(groupID).getChatMessages();
        if(chatMessages == null)
            chatMessages = new ArrayList<>();
        chatMessages.add(chatMessage);
        User.getInstance().getGroups().get(groupID).setChatMessages(chatMessages);
        ChatViewModel chatViewModel = new ChatViewModel(groupID); // updating DB
        chatViewModel.updateGroupChat(groupID,chatMessages);
        chatViewModel.updateUser();
        binding.chatETMsgInput.setText(null);
    }


    /**
     * Loading group chat's name for title and id for updating DB
     **/
    private void loadReceivedDetails() {
        Intent previousIntent = getIntent();
        groupID = previousIntent.getStringExtra(Constants.KEY_GROUP_ID);
        String groupName = previousIntent.getStringExtra(Constants.KEY_GROUP_NAME);
        binding.chatTVGroupName.setText(groupName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}