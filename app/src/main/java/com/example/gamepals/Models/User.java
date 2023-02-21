package com.example.gamepals.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private static User user = null;
    private String name;
    private String uid;
    private HashMap<String,Group> groups;

    private ArrayList<String> favoriteGames;

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
        if(groups == null)
            groups = new HashMap<>();
        this.favoriteGames = currentUser.getFavoriteGames();
        if(favoriteGames == null)
            favoriteGames = new ArrayList<>();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setGroups(HashMap<String, Group> groups) {
        this.groups = groups;
    }

    public ArrayList<String> getFavoriteGames() {
        return favoriteGames;
    }

    public void setFavoriteGames(ArrayList<String> favoriteGames) {
        this.favoriteGames = favoriteGames;
    }

    public boolean checkFavGame(String game){
        for (int i = 0; i < favoriteGames.size(); i++) {
            if(favoriteGames.get(i).equals(game))
                return true;
        }
        return false;
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
