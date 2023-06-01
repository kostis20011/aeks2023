package com.example.watercheck;

import java.util.ArrayList;
import java.util.List;

// ProfileDatabase.java
public class ProfileDatabase {
    private static ProfileDatabase instance;
    private List<Profile> profiles;

    private ProfileDatabase() {
        profiles = new ArrayList<>();
    }

    public static ProfileDatabase getInstance() {
        if (instance == null) {
            instance = new ProfileDatabase();
        }
        return instance;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }

    public Profile getProfileById(int profileId) {
        for (Profile profile : profiles) {
            if (profile.getId() == profileId) {
                return profile;
            }
        }
        return null; // Profile not found
    }
}
