package com.example.gamepals.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private static User user = null;
    private String name;
    private String uid;
    private HashMap<String,Group> groups;

    private ArrayList<Game> favoriteGames;

    public User() {
        this.groups = new HashMap<>();
    }

    public User(String name, String uid) {
        this.name = name;
        this.uid = uid;
        this.groups = new HashMap<>();
        this.favoriteGames = new ArrayList<>();
    }

    public static void init(String name,String uid){
        if(user == null)
            user = new User(name,uid);
    }

    public void setUser(User currentUser) {
        this.name = currentUser.getName();
        this.uid = currentUser.getUid();
        this.groups = currentUser.getGroups();
        this.favoriteGames = currentUser.getFavoriteGames();
    }
    public static User getInstance(){
        return user;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public HashMap<String,Group> getGroups() {
        return groups;
    }

    public static User getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setGroups(HashMap<String, Group> groups) {
        this.groups = groups;
    }

    public ArrayList<Game> getFavoriteGames() {
        return favoriteGames;
    }

    public void setFavoriteGames(ArrayList<Game> favoriteGames) {
        this.favoriteGames = favoriteGames;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", groups=" + groups +
                ", favoriteGames=" + favoriteGames +
                '}';
    }
}
