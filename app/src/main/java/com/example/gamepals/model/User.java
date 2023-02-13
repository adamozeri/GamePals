package com.example.gamepals.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private static User user = null;
    private String name;
    private String uid;
    private HashMap<String,Group> groups;

    public User() {
        this.groups = new HashMap<>();
    }

    public User(String name, String uid) {
        this.name = name;
        this.uid = uid;
        this.groups = new HashMap<>();
    }

    public static void init(String name,String uid){
        if(user == null)
            user = new User(name,uid);
    }

    public void setUser(User currentUser) {
        this.name = currentUser.getName();
        this.uid = currentUser.getUid();
        this.groups = currentUser.getGroups();
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

}
