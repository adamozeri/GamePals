package com.example.gamepals.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class Group implements Parcelable {

    private ArrayList<String> usersID;
    private ArrayList<ChatMessage> chatMessages;
    private String id;
    private String name;
    private int capacity;
    private int numOfUsers;
    private String description;
    private String region;
    private String skill;
    private String gamingPlatform;
    private String groupAdmin;


    public Group() {
    }

    public Group(String name, int capacity, String description, String region, String skill, String gamingPlatform) {
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.region = region;
        this.skill = skill;
        this.gamingPlatform = gamingPlatform;
        this.usersID = new ArrayList<>();
        this.usersID.add(User.getInstance().getUid());
        this.numOfUsers = 1;
        this.groupAdmin = User.getInstance().getUid(); // setting the admin to give him more permissions over the group
        this.id = UUID.randomUUID().toString();
        this.chatMessages = new ArrayList<>();
    }

    protected Group(Parcel in) {
        usersID = in.createStringArrayList();
        chatMessages = in.createTypedArrayList(ChatMessage.CREATOR);
        id = in.readString();
        name = in.readString();
        capacity = in.readInt();
        numOfUsers = in.readInt();
        description = in.readString();
        region = in.readString();
        skill = in.readString();
        gamingPlatform = in.readString();
        groupAdmin = in.readString();
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(capacity);
        parcel.writeInt(numOfUsers);
        parcel.writeString(description);
        parcel.writeString(region);
        parcel.writeStringList(usersID);
        parcel.writeTypedList(chatMessages);
    }

    public void addUser(User user) {
        usersID.add(user.getUid());
        numOfUsers++;
    }

    public String getRegion() {
        return region;
    }

    public String getSkill() {
        return skill;
    }

    public String getPlatform() {
        return gamingPlatform;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public int getNumOfUsers() {
        return numOfUsers;
    }

    public void setNumOfUsers(int numOfUsers) {
        this.numOfUsers = numOfUsers;
    }

    public ArrayList<String> getUsersID() {
        return usersID;
    }

    public void setUsersID(ArrayList<String> usersID) {
        this.usersID = usersID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getGamingPlatform() {
        return gamingPlatform;
    }

    public void setGamingPlatform(String gamingPlatform) {
        this.gamingPlatform = gamingPlatform;
    }

    public String getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(String groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }



    @Override
    public String toString() {
        return "Group{" +
                "usersID=" + usersID +
                ", chatMessages=" + chatMessages +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", numOfUsers=" + numOfUsers +
                ", description='" + description + '\'' +
                ", region='" + region + '\'' +
                ", skill='" + skill + '\'' +
                ", gamingPlatform='" + gamingPlatform + '\'' +
                ", groupAdmin='" + groupAdmin + '\'' +
                '}';
    }

}
