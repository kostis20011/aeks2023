package com.example.watercheck;

import java.util.ArrayList;
import java.util.List;

// ProfileDatabase.java
public class ProfileDatabase {
    private static ProfileDatabase instance;
    private List<Profile> profiles;

    private ProfileDatabase() {
        profiles = new ArrayList<>();
        // Add profiles as needed
        profiles.add(new Profile(1, "male", 25, 70.0, 170.0));
        profiles.add(new Profile(2, "female", 35, 60.0, 160.0));
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

    public Profile getProfileById(int profileId) {
        for (Profile profile : profiles) {
            if (profile.getId() == profileId) {
                return profile;
            }
        }
        return null; // Profile not found
    }
}
