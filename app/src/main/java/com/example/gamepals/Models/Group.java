package com.example.gamepals.Models;

import java.util.ArrayList;
import java.util.UUID;

public class Group {

    private ArrayList<String> usersID;
    private ArrayList<ChatMessage> chatMessages;
    private String id;
    private String name;
    private Game game;
    private int capacity;
    private int numOfUsers;
    private String description;
    private String region;
    private String skill;
    private String gamingPlatform;


    public Group() {
    }

    public Group(String name, int capacity, Game game, String description, String region, String skill, String gamingPlatform) {
        this.name = name;
        this.capacity = capacity;
        this.game = game;
        this.description = description;
        this.region = region;
        this.skill = skill;
        this.gamingPlatform = gamingPlatform;
        this.usersID = new ArrayList<>();
        this.usersID.add(User.getInstance().getUid());
        this.numOfUsers = 1;
        this.id = UUID.randomUUID().toString();
        this.chatMessages = new ArrayList<>();
    }


    public void addUser(String uid) {
        usersID.add(uid);
        numOfUsers++;
    }

    public void removeUser(String uid){
        usersID.remove(uid);
        numOfUsers--;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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


    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void addChatMessage(ChatMessage chatMessage){
        if(chatMessages == null){
            chatMessages = new ArrayList<>();
        }
        chatMessages.add(chatMessage);
    }

    @Override
    public String toString() {
        return "Group{" +
                "usersID=" + usersID +
                ", chatMessages=" + chatMessages +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", game=" + game +
                ", capacity=" + capacity +
                ", numOfUsers=" + numOfUsers +
                ", description='" + description + '\'' +
                ", region='" + region + '\'' +
                ", skill='" + skill + '\'' +
                ", gamingPlatform='" + gamingPlatform + '\'' +
                '}';
    }
}
