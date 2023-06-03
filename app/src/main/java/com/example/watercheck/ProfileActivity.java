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
    private String genderEditText;
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
        genderEditText = getSelectedGender();
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        ageEditText = findViewById(R.id.ageEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        createProfileButton = findViewById(R.id.createProfileButton);
        selectProfileButton = findViewById(R.id.selectProfileButton);

        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vew) {
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
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String Name = nameEditText.getText().toString();
        String gender = genderEditText;
        String age = ageEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        String height = heightEditText.getText().toString();
        if (!Name.equals("") &&  !height.equals("")){
            Profile found = dbHandler.findProfile(Name);
            if (found == null){
                Profile product = new Profile(Name, gender, Integer.parseInt(age), Integer.parseInt(weight), Integer.parseInt(height));
                dbHandler.addProduct(product);
                nameEditText.setText("");
                genderEditText=("");
                ageEditText.setText("");
                weightEditText.setText("");
                heightEditText.setText("");
            }
        }

        // Redirect to the water intake calculation page with the profile ID
        redirectToWaterIntakeCalculation(saveProfileToSharedPreferences(nameEditText.toString(), genderEditText.toString(), ageEditText.toString(), weightEditText.toString(), heightEditText.toString()));
    }


    private int saveProfileToSharedPreferences(String name, String gender, String age, String weight, String height) {
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

   // private void selectProfile() {
        // Redirect to the select profile page
   //     Intent intent = new Intent(ProfileActivity.this, SelectProfileActivity.class);
    //    startActivity(intent);
   // }

    private String getSelectedGender() {
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText().toString();
        }
        return null;
    }

}


