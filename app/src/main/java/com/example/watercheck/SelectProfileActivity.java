package com.example.watercheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectProfileActivity extends AppCompatActivity {

    private Spinner profileSpinner;
    private Button selectProfileButton;
    private Button deleteProfileButton;
    private Button profileButton;
    private int selectedProfileId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

        profileSpinner = findViewById(R.id.profileSpinner);
        selectProfileButton = findViewById(R.id.selectProfileButton);
        deleteProfileButton = findViewById(R.id.deleteProfileButton);
        profileButton= findViewById(R.id.profileButton);

        MyDBHandler dbHandler = new MyDBHandler(this);
        List<Profile> profiles = dbHandler.getProfiles();

        // Create a list of profile names displaying ID and Age in spinner
        List<String> profileNames = new ArrayList<>();
        for (Profile profile : profiles) {
            String profileName = "Name: " + profile.getName() + " - Age: " + profile.getAge();
            profileNames.add(profileName);
        }

        // Create an ArrayAdapter and set it as the adapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, profileNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileSpinner.setAdapter(adapter);

        // Set the item selection listener for the spinner
        profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected profile ID
                selectedProfileId = profiles.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when no profile is selected
                selectedProfileId = -1;
            }
        });

        //selects a profile
        selectProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProfileId != -1) {
                    // Nav to the water intake calculation page
                    navigateToWaterIntakeCalculation();
                } else {
                    // Show a message to the user to select a profile
                    Toast.makeText(SelectProfileActivity.this, "Please select a profile", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Nav to the profileActivity page
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    navigateToProfileActivity();
            }
        });

        //deletes the selected profile
        deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProfileId != -1) {
                    dbHandler.deleteProduct(selectedProfileId);
                    navigateToProfileActivity();
                } else {
                    // Show a message to the user to select a profile
                    Toast.makeText(SelectProfileActivity.this, "Please select a profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //sends you to WaterIntakeCalculation
    private void navigateToWaterIntakeCalculation() {
        Intent intent = new Intent(this, WaterIntakeCalculationActivity.class);
        intent.putExtra("profileId", selectedProfileId); // Change the key to lowercase
        startActivity(intent);
    }

    //sends you to ProfileActivity
    private void navigateToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

}
