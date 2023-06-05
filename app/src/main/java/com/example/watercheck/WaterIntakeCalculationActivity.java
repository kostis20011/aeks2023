package com.example.watercheck;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.BreakIterator;

public class WaterIntakeCalculationActivity extends AppCompatActivity {

    private TextView waterIntakeTextView;
    private String nameView;
    private String genderView;
    private Integer ageView;
    private Double weightView;
    private Double heightView;

    private double weightFactorMale = 40.0;
    private double weightFactorFemale = 35.0;
    private double heightFactorMale = 0.6;
    private double heightFactorFemale = 0.5;
    private double ageFactorMale = 0.0; // Add the missing variable
    private double ageFactorFemale = -100.0; // Add the missing variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_calculation);

        // Retrieve the profileId from the previous activity or from user selection
        int profileId = getIntent().getIntExtra("profileId", 0);

        waterIntakeTextView = findViewById(R.id.waterIntakeTextView);

        // Retrieve the factors from the profile or calculate them as needed
        double weightFactor = calculateWeightFactor(profileId); // Calculate weight factor based on gender
        double heightFactor = calculateHeightFactor(profileId); // Calculate height factor based on gender
        double ageFactor = calculateAgeFactor(profileId); // Calculate age factor based on age range

        // Calculate the water intake
        double waterIntake = calculateWaterIntake(weightFactor, heightFactor, ageFactor);

        // Set the water intake value to the waterIntakeTextView
        waterIntakeTextView.setText("Water Intake: " + waterIntake + " ml");
    }

    private double calculateWeightFactor(int profileId) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Profile profile = dbHandler.findProfile(String.valueOf(profileId));
        if (profile != null) {
            String gender = profile.getGender();
            double weight = profile.getWeight();
            if (gender.equalsIgnoreCase("male")) {
                return weight * 40.0; // Adjust the weight factor for men
            } else if (gender.equalsIgnoreCase("female")) {
                return weight * 35.0; // Adjust the weight factor for women
            }
        }
        return 0.0; // Default value if profile not found
    }

    private double calculateHeightFactor(int profileId) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Profile profile = dbHandler.findProfile(String.valueOf(profileId));
        if (profile != null) {
            String gender = profile.getGender();
            double height = profile.getHeight();
            if (gender.equalsIgnoreCase("male")) {
                return height * 0.6; // Adjust the height factor for men
            } else if (gender.equalsIgnoreCase("female")) {
                return height * 0.5; // Adjust the height factor for women
            }
        }
        return 0.0; // Default value if profile not found
    }

    private double calculateAgeFactor(int profileId) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Profile profile = dbHandler.findProfile(String.valueOf(profileId));
        if (profile != null) {
            int age = profile.getAge();
            if (age >= 14 && age <= 30) {
                if (profile.getGender().equalsIgnoreCase("male")) {
                    return 0.0; // Age factor for ages 14-30 (male)
                } else if (profile.getGender().equalsIgnoreCase("female")) {
                    return -200.0; // Age factor for ages 14-30 (female)
                }
            }
            if (age >= 31 && age <= 55) {
                if (profile.getGender().equalsIgnoreCase("male")) {
                    return -100.0;
                } else if (profile.getGender().equalsIgnoreCase("female")) {
                    return -300.0;
                }
            }
            if (age>=56) {
                if (profile.getGender().equalsIgnoreCase("male")) {
                    return -300.0;
                } else if (profile.getGender().equalsIgnoreCase("female")) {
                    return -450.0;
                }
            }
        }
        return 0.0; // Default value if profile not found
    }

    private double calculateWaterIntake(double weightFactor, double heightFactor, double ageFactor) {
        double baseIntakeMale = 3000.0; // Base intake for men
        double baseIntakeFemale = 2700.0; // Base intake for women

        // Retrieve the profileId from the previous activity or from user selection
        int profileId = getIntent().getIntExtra("profileId", 0);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Profile profile = dbHandler.findProfile(String.valueOf(profileId));
        if (profile != null) {
            double weight = profile.getWeight();
            double height = profile.getHeight();
            String gender = profile.getGender();
            if (gender.equalsIgnoreCase("male")) {
                return 0.015*(baseIntakeMale + (weight * weightFactor) + (height * heightFactor) + ageFactor);
            } else if (gender.equalsIgnoreCase("female")) {
                return 0.015*(baseIntakeFemale + (weight * weightFactor) + (height * heightFactor) + ageFactor);
            }
        }
        return 0.0; // Default value if profile not found
    }
}
