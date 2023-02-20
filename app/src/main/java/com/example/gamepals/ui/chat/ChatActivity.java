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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    private String groupID;
    private ChatAdapter chatAdapter;

    private ChatViewModel chatViewModel;

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
        chatViewModel = new ChatViewModel(groupID);
        chatViewModel.getChatMessages().observe(this,observer);
        chatAdapter = new ChatAdapter();
        binding.chatLSTChatLst.setLayoutManager(new LinearLayoutManager(this));
        binding.chatLSTChatLst.setAdapter(chatAdapter);
        User.getInstance().getGroups().get(groupID).setChatMessages(chatViewModel.getMessagesFromDB(groupID));
    }


    private void setListeners() {
        binding.chatBTNBack.setOnClickListener(view -> finish());
        binding.chatFABSend.setOnClickListener(view -> sendMessage());
    }

    private void sendMessage() {
        String msg = binding.chatETMsgInput.getText().toString();
        if(!msg.isEmpty()){
            ChatMessage chatMessage = new ChatMessage(User.getInstance().getUid(),
                    User.getInstance().getName(),
                    groupID,
                    msg,
                    getReadableDateTime(new Date()));

            User.getInstance().getGroups().get(groupID).getChatMessages().add(chatMessage);
            chatViewModel.updateGroupChat(groupID,chatMessage,chatAdapter.getChatMessages().size()+"");
            chatViewModel.updateUser();
        }
        binding.chatETMsgInput.setText(null);
    }

    private String getReadableDateTime(Date date){
        return new SimpleDateFormat("dd MMMM, yyyy - hh:mm a", Locale.getDefault()).format(date);
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