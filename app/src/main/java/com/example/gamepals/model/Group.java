package com.example.gamepals.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class Group {

    private HashMap<String, User> users;
    private String id;
    private String name;
    private int capacity;
    private String description;
    private String region;
    private String skill;
    private String gamingPlatform;
    private String groupAdmin;


    public Group() {}

    public Group(String name, int capacity, String description, String region, String skill, String gamingPlatform) {
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.region = region;
        this.skill = skill;
        this.gamingPlatform = gamingPlatform;
        this.users = new HashMap<>();
        this.users.put(User.getInstance().getUid(), User.getInstance());
        this.groupAdmin = User.getInstance().getUid(); // setting the admin to give him more permissions over the group
        this.id = UUID.randomUUID().toString();
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
}
