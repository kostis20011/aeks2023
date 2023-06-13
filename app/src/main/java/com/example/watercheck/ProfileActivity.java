package com.example.watercheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private EditText ageEditText;
    private RadioGroup genderRadioGroup;
    private Button createProfileButton;
    private Button selectProfileButton;
    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);

        dbHandler = MyDBHandler.getInstance(this); // Retrieve the instance of MyDBHandler

        nameEditText = findViewById(R.id.nameEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        ageEditText = findViewById(R.id.ageEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        createProfileButton = findViewById(R.id.createProfileButton);
        selectProfileButton = findViewById(R.id.selectProfileButton);

        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
            }
        });

        selectProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfile();
            }
        });
    }
    //OnClick method for createProfileButton
    private void createProfile() {
        String name = nameEditText.getText().toString().trim();
        String heightStr = heightEditText.getText().toString().trim();
        String weightStr = weightEditText.getText().toString().trim();
        String ageStr = ageEditText.getText().toString().trim();
        String gender = getSelectedGender();

        if (name.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || ageStr.isEmpty() || gender == null) {
            // Check if any field is empty or gender is not selected
            Toast.makeText(this, "Please fill in all fields and select gender", Toast.LENGTH_SHORT).show();
            return;
        }

        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);
        int age = Integer.parseInt(ageStr);

        // Save the profile to the database using MyDBHandler
        Profile profile = new Profile(name, height, weight, age, gender);
        int profileId = dbHandler.addProfile(profile);

        if (profileId != -1) {
            // Redirect to the water intake calculation page with the profile ID
            redirectToWaterIntakeCalculation(profileId);
        } else {
            Toast.makeText(this, "Failed to save profile", Toast.LENGTH_SHORT).show();
        }
    }

    //OnClick method for selectProfileButton
    private void selectProfile() {
        // Redirect to the select profile page
        Intent intent = new Intent(ProfileActivity.this, SelectProfileActivity.class);
        startActivity(intent);
    }

    private void redirectToWaterIntakeCalculation(int profileId) {
        // Redirect to the water intake calculation page with the profile ID
        Intent intent = new Intent(ProfileActivity.this, WaterIntakeCalculationActivity.class);
        intent.putExtra("profileId", profileId);
        startActivity(intent);
        finish();
    }

    private String getSelectedGender() {
        //Returns the selected gender as a string
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText().toString();
        }
        return null;
    }
}
