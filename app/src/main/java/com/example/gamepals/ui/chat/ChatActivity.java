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

    private Group group;
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
        ChatViewModel chatViewModel = new ChatViewModel(group.getId());
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
                group.getId(),
                binding.chatETMsgInput.getText().toString(),
                new Date().toString());
        group.getChatMessages().add(chatMessage);
        ChatViewModel chatViewModel = new ChatViewModel(group); // updating DB
        binding.chatETMsgInput.setText(null);
    }

    private void loadReceivedDetails() {
        Intent previousIntent = getIntent();
        Group group = previousIntent.getExtras().getParcelable(Constants.KEY_GROUP);
        Log.d("Group check:",group.toString());
        binding.chatTVGroupName.setText(group.getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}