package com.example.gamepals.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamepals.Utils.Constants;
import com.example.gamepals.databinding.ReceivedMessageItemBinding;
import com.example.gamepals.databinding.SentMessageItemBinding;
import com.example.gamepals.model.ChatMessage;
import com.example.gamepals.model.User;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatMessage> chatMessages;
//    private final Bitmap receiverProfileImage;

    public ChatAdapter() {
        this.chatMessages = new ArrayList<>();
//        this.receiverProfileImage = receiverProfileImage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.VIEW_TYPE_SENT) {
            return new SentMessageViewHolder(
                    SentMessageItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                            parent,
                            false)
            );
        } else {
            return new ReceivedMessageViewHolder(
                    ReceivedMessageItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                            parent,
                            false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == Constants.VIEW_TYPE_SENT)
            ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
        else
            ((ReceivedMessageViewHolder) holder).setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).getSenderID().equals(User.getInstance().getUid()))
            return Constants.VIEW_TYPE_SENT;
        else
            return Constants.VIEW_TYPE_RECEIVED;
    }

    public void updateMessages(ArrayList<ChatMessage> chatMessages){
        this.chatMessages = chatMessages;
        notifyDataSetChanged();
    }

    public class SentMessageViewHolder extends RecyclerView.ViewHolder {

        private final SentMessageItemBinding binding;

        public SentMessageViewHolder(SentMessageItemBinding sentMessageItemBinding) {
            super(sentMessageItemBinding.getRoot());
            this.binding = sentMessageItemBinding;
        }

        void setData(ChatMessage chatMessage) {
            binding.sentMessage.setText(chatMessage.getMessage());
            binding.sentDateTime.setText(chatMessage.getDateTime().toString());
        }
    }

    public class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        private final ReceivedMessageItemBinding binding;

        public ReceivedMessageViewHolder(ReceivedMessageItemBinding receivedMessageItemBinding) {
            super(receivedMessageItemBinding.getRoot());
            this.binding = receivedMessageItemBinding;
        }

        private void setData(ChatMessage chatMessage) {
            binding.receivedMessage.setText(chatMessage.getMessage());
            binding.receivedDateTime.setText(chatMessage.getDateTime());
        }
    }
}
