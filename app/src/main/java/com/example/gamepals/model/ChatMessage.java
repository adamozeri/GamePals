package com.example.gamepals.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class ChatMessage implements Parcelable{
    private String senderID;
    private String senderName;
    private String groupID;
    private String message;
    private String dateTime;

    public ChatMessage() {
    }

    public ChatMessage(String senderID, String senderName,String groupID, String message, String dateTime) {
        this.senderID = senderID;
        this.senderName = senderName;
        this.groupID = groupID;
        this.message = message;
        this.dateTime = dateTime;
    }

    protected ChatMessage(Parcel in) {
        senderID = in.readString();
        groupID = in.readString();
        message = in.readString();
        dateTime = in.readString();
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(senderID);
        parcel.writeString(groupID);
        parcel.writeString(message);
        parcel.writeString(dateTime);
    }
}
