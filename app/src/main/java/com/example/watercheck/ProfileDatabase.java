package com.example.watercheck;

import java.util.ArrayList;
import java.util.List;

// ProfileDatabase.java
public class ProfileDatabase {
    private static ProfileDatabase instance;
    private List<Profile> profiles;

    private ProfileDatabase() {
        profiles = new ArrayList<>();
        // Populate the profiles list with data from the database or other sources
        // For example:
        profiles.add(new Profile(1, "male", 25, 70.0, 170.0));
        profiles.add(new Profile(2, "female", 35, 60.0, 160.0));
        // Add more profiles as needed
    }

    public static ProfileDatabase getInstance() {
        if (instance == null) {
            instance = new ProfileDatabase();
        }
        return instance;
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
