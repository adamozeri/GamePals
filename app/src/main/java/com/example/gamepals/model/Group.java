package com.example.gamepals.model;

import java.util.Locale;

public class Group {

    private enum Region {EUROPE, ASIA, NORTH_AMERICA, SOUTH_AMERICA, AFRICA, OCEANIA};
    private enum Skill {BEGINNER, EXPERIENCED, PROFESSIONAL};
    private enum Gaming_Platform {PC, XBOX, PLAYSTATION}

    private String name;
    private int capacity;
    private String description;
    private Region region;
    private Skill skill;
    private Gaming_Platform platform;

    public Group(String name, int capacity, String description, String region, String skill, String platform) {
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        setRegion(region);
        setSkill(skill);
        setPlatform(platform);
    }

    public String getRegion() {
        return region.toString();
    }

    public void setRegion(String region) {
        this.region = Region.valueOf(region.toUpperCase());
    }

    public String getSkill() {
        return skill.toString();
    }

    public void setSkill(String skill) {
        this.skill = Skill.valueOf(skill.toUpperCase());
    }

    public String getPlatform() {
        return platform.toString();
    }

    public void setPlatform(String platform) {
        this.platform = Gaming_Platform.valueOf(platform.toUpperCase());
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
}
