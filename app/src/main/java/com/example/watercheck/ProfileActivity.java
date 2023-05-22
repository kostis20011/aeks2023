package com.example.watercheck;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private EditText ageEditText;
    private RadioGroup genderRadioGroup;
    private Button createProfileButton;
    private Button selectProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);

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

    private void createProfile() {
        // Retrieve the values entered by the user
        String name = nameEditText.getText().toString();
        String height = heightEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = getSelectedGender();


        // Save the profile to SharedPreferences
        int profileId = saveProfileToSharedPreferences(name, height, weight, age, gender);

        // Redirect to the water intake calculation page with the profile ID
        redirectToWaterIntakeCalculation(profileId);
    }

    private int saveProfileToSharedPreferences(String name, String height, String weight, String age, String gender) {
        SharedPreferences sharedPreferences = getSharedPreferences("Profiles", MODE_PRIVATE);
        int nextProfileId = sharedPreferences.getInt("NextProfileId", 1);
        int profileId = nextProfileId;

        // Save the profile data with the created profile ID
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name_" + profileId, name);
        editor.putString("Height_" + profileId, height);
        editor.putString("Weight_" + profileId, weight);
        editor.putString("Age_" + profileId, age);
        editor.putString("Gender_" + profileId, gender);
        editor.putInt("NextProfileId", nextProfileId + 1);
        editor.apply();

        return profileId;
    }

    private void redirectToWaterIntakeCalculation(int profileId) {
        // Redirect to the water intake calculation page with the profile ID
        Intent intent = new Intent(ProfileActivity.this, WaterIntakeCalculationActivity.class);
        intent.putExtra("profileId", profileId);
        startActivity(intent);
        finish();
    }

    private void selectProfile() {
        // Redirect to the select profile page
        Intent intent = new Intent(ProfileActivity.this, SelectProfileActivity.class);
        startActivity(intent);
    }

    private String getSelectedGender() {
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText().toString();
        }
        return null;
    }
}
