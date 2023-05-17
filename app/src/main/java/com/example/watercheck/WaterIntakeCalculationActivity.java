package com.example.watercheck;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WaterIntakeCalculationActivity extends AppCompatActivity {

    private TextView waterIntakeTextView;
    private TextView adviceTextView;

    private double weightFactorMale = 40.0;
    private double weightFactorFemale = 35.0;
    private double heightFactorMale = 0.6;
    private double heightFactorFemale = 0.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_calculation);

        // Retrieve the profileId from the previous activity or from user selection
        int profileId = getIntent().getIntExtra("profileId", 0);

        waterIntakeTextView = findViewById(R.id.waterIntakeTextView);
        adviceTextView = findViewById(R.id.adviceTextView);

        // Retrieve the factors from the profile or calculate them as needed
        double weightFactor = calculateWeightFactor(profileId); // Calculate weight factor based on gender
        double heightFactor = calculateHeightFactor(profileId); // Calculate height factor based on gender
        double ageFactor = calculateAgeFactor(profileId); // Calculate age factor based on age range

        // Calculate the water intake
        double waterIntake = calculateWaterIntake(weightFactor, heightFactor, ageFactor);

        // Set the water intake value to the waterIntakeTextView
        waterIntakeTextView.setText("Water Intake: " + waterIntake + " ml");

        // Set the advice based on the water intake
        setAdvice(waterIntake);
    }

    private double calculateWeightFactor(int profileId) {
        Profile profile = ProfileDatabase.getInstance().getProfileById(profileId);
        if (profile != null) {
            String gender = profile.getGender();
            double weight = profile.getWeight();
            if (gender.equalsIgnoreCase("male")) {
                return weight * weightFactorMale; // Weight factor for men
            } else if (gender.equalsIgnoreCase("female")) {
                return weight * weightFactorFemale; // Weight factor for women
            }
        }
        return 0.0; // Default value if profile not found
    }

    private double calculateHeightFactor(int profileId) {
        Profile profile = ProfileDatabase.getInstance().getProfileById(profileId);
        if (profile != null) {
            String gender = profile.getGender();
            double height = profile.getHeight();
            if (gender.equalsIgnoreCase("male")) {
                return height * heightFactorMale; // Height factor for men
            } else if (gender.equalsIgnoreCase("female")) {
                return height * heightFactorFemale; // Height factor for women
            }
        }
        return 0.0; // Default value if profile not found
    }

    private double calculateAgeFactor(int profileId) {
        Profile profile = ProfileDatabase.getInstance().getProfileById(profileId);
        if (profile != null) {
            int age = profile.getAge();
            if (age >= 14 && age <= 30) {
                return 0.0; // Age factor for ages 14-30
            } else if (age >= 31 && age <= 55) {
                return -100.0; // Age factor for ages 31-55
            } else if (age >= 56) {
                return -200.0; // Age factor for ages 56 and above
            }
        }
        return 0.0; // Default value if profile not found
    }

    private double calculateWaterIntake(double weightFactor, double heightFactor, double ageFactor) {
        double baseIntakeMale = 3000.0; // Base intake for men
        double baseIntakeFemale = 2700.0; // Base intake for women

        // Retrieve the profileId from the previous activity or from user selection
        int profileId = getIntent().getIntExtra("profileId", 0);
        Profile profile = ProfileDatabase.getInstance().getProfileById(profileId);
        if (profile != null) {
            double weight = profile.getWeight();
            double height = profile.getHeight();
            String gender = profile.getGender();
            if (gender.equalsIgnoreCase("male")) {
                return baseIntakeMale + (weight * weightFactor) + (height * heightFactor) + ageFactor;
            } else if (gender.equalsIgnoreCase("female")) {
                return baseIntakeFemale + (weight * weightFactor) + (height * heightFactor) + ageFactor;
            }
        }
        return 0.0; // Default value if profile not found
    }

    private void setAdvice(double waterIntake) {
        String advice;
        if (waterIntake < 2000) {
            advice = "Increase your water intake to stay hydrated.";
        } else if (waterIntake >= 2000 && waterIntake <= 3000) {
            advice = "Your water intake is adequate. Keep up the good work!";
        } else {
            advice = "Great job! You're drinking enough water.";
        }
        adviceTextView.setText(advice);
    }
}
