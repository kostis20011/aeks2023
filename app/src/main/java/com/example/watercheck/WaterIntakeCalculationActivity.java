package com.example.watercheck;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class WaterIntakeCalculationActivity extends AppCompatActivity {

    private TextView waterIntakeTextView;

    private double weightFactorMale = 40.0;
    private double weightFactorFemale = 35.0;
    private double heightFactorMale = 0.6;
    private double heightFactorFemale = 0.5;
    private double ageFactorMale = 0.0;
    private double ageFactorFemale = -100.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_calculation);

        // Retrieve the profileId from the previous activity or from user selection
        int profileId = getIntent().getIntExtra("profileId", 0);

        waterIntakeTextView = findViewById(R.id.waterIntakeTextView);

        // Retrieve the factors from the profile or calculate them as needed
        double weightFactor = calculateWeightFactor(profileId);
        double heightFactor = calculateHeightFactor(profileId);
        double ageFactor = calculateAgeFactor(profileId);

        // Calculate the water intake
        double waterIntake = calculateWaterIntake(weightFactor, heightFactor, ageFactor);

        // Set the water intake value to the waterIntakeTextView
        waterIntakeTextView.setText("Water Intake: " + String.format("%.1f", waterIntake) + " ml");

        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfileActivity();
            }
        });

    }

    private double calculateWeightFactor(int profileId) {
        MyDBHandler dbHandler = MyDBHandler.getInstance(getApplicationContext());
        Profile profile = dbHandler.getProfileById(profileId);
        if (profile != null) {
            String gender = profile.getGender();
            double weight = profile.getWeight();
            if (gender.equalsIgnoreCase("male")) {
                return weight * weightFactorMale;
            } else if (gender.equalsIgnoreCase("female")) {
                return weight * weightFactorFemale;
            }
        }
        return 0.0; // Default value if profile not found
    }

    private double calculateHeightFactor(int profileId) {
        MyDBHandler dbHandler = MyDBHandler.getInstance(getApplicationContext());
        Profile profile = dbHandler.getProfileById(profileId);
        if (profile != null) {
            String gender = profile.getGender();
            double height = profile.getHeight();
            if (gender.equalsIgnoreCase("male")) {
                return height * heightFactorMale;
            } else if (gender.equalsIgnoreCase("female")) {
                return height * heightFactorFemale;
            }
        }
        return 0.0; // Default value if profile not found
    }

    private double calculateAgeFactor(int profileId) {
        MyDBHandler dbHandler = MyDBHandler.getInstance(getApplicationContext());
        Profile profile = dbHandler.getProfileById(profileId);
        if (profile != null) {
            int age = profile.getAge();
            if (age >= 14 && age <= 30) {
                if (profile.getGender().equalsIgnoreCase("male")) {
                    return ageFactorMale;
                } else if (profile.getGender().equalsIgnoreCase("female")) {
                    return ageFactorFemale;
                }
            }
            if (age >= 31 && age <= 55) {
                if (profile.getGender().equalsIgnoreCase("male")) {
                    return -100.0;
                } else if (profile.getGender().equalsIgnoreCase("female")) {
                    return -300.0;
                }
            }
            if (age >= 56) {
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
        Profile profile = MyDBHandler.getInstance(getApplicationContext()).getProfileById(profileId);
        if (profile != null) {
            double weight = profile.getWeight();
            double height = profile.getHeight();
            String gender = profile.getGender();

            if (gender.equalsIgnoreCase("male")) {
                return 0.015 * (baseIntakeMale + (weight * weightFactor) + (height * heightFactor) + ageFactor);
            } else if (gender.equalsIgnoreCase("female")) {
                return 0.015 * (baseIntakeFemale + (weight * weightFactor) + (height * heightFactor) + ageFactor);
            }
        }

        return 0.0; // Default value if profile not found
    }
    private void navigateToProfileActivity() {
        Intent intent = new Intent(WaterIntakeCalculationActivity.this, ProfileActivity.class);
        startActivity(intent);
    }


}
